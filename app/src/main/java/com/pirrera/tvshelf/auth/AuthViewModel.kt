package com.pirrera.tvshelf.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    init {
        checkAuthStatus()
    }

    fun checkAuthStatus() {
        if (auth.currentUser == null) {
            _authState.value = AuthState.Unauthenticated
        } else {
            _authState.value = AuthState.Authenticated
        }
    }

    fun login(email: String, password: String) {

        if (email.isBlank() || password.isBlank()) {
            _authState.value = AuthState.Error("Email and password cannot be empty")
            return
        }

        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "c'est la D")
                }
            }
    }

    fun signup(pseudo : String, email: String, password: String) {

        if (email.isBlank() || password.isBlank() || pseudo.isBlank()) {
            _authState.value = AuthState.Error("Pseudo, email and password cannot be empty")
            return
        }

        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.let{
                        it.updateProfile(userProfileChangeRequest {
                            displayName = pseudo
                        }).addOnCompleteListener{profileTask ->
                            if(profileTask.isSuccessful){
                                saveUserToFirestore(it.uid, pseudo, email)
                            } else {
                                _authState.value = AuthState.Error(profileTask.exception?.message ?: "l'update a foiré mgl")
                            }
                        }
                    }
                    _authState.value = AuthState.Authenticated
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "c'est la D")
                }
            }
    }

    private fun saveUserToFirestore(uid: String, pseudo: String, email: String) {
        val user = hashMapOf(
            "uid" to uid,
            "pseudo" to pseudo,
            "email" to email,
            "createdAt" to System.currentTimeMillis()
        )

        db.collection("users").document(uid)
            .set(user)
            .addOnSuccessListener {
                _authState.value = AuthState.Authenticated
            }
            .addOnFailureListener { e ->
                _authState.value = AuthState.Error("Firestore save failed: ${e.message}")
            }
    }

    fun signout() {
        try {
            auth.signOut()
            _authState.value = AuthState.Unauthenticated
        } catch (e: Exception) {
            _authState.value = AuthState.Error("Sign out failed: ${e.message}")
        }
    }
}

    sealed class AuthState {
        object Authenticated : AuthState()
        object Unauthenticated : AuthState()
        object Loading : AuthState()
        data class Error(val message: String) : AuthState()
    }