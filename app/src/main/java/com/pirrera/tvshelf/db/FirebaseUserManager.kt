package com.pirrera.tvshelf.db

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseUserManager {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    /**
     * Checks if the user exists in Firestore. If not, adds them.
     */
    fun createUserIfNotExists() {
        val user = auth.currentUser ?: return
        val userRef = db.collection("users").document(user.uid)

        userRef.get().addOnSuccessListener { document ->
            if (!document.exists()) {
                val userData = hashMapOf(
                    "uid" to user.uid,
                    "displayName" to (user.displayName ?: "Anonymous"),
                    "email" to (user.email ?: ""),
                    "profilePic" to (user.photoUrl?.toString() ?: ""),
                    "createdAt" to System.currentTimeMillis()
                )

                userRef.set(userData)
                    .addOnSuccessListener { println("User added to Firestore") }
                    .addOnFailureListener { e -> println("Error adding user: $e") }
            }
        }.addOnFailureListener { e ->
            println("Error checking user existence: $e")
        }
    }


    fun getUserData(uid : String, onSuccess : (Map<String, Any>?) -> Unit, onFailure: (Exception)->Unit) {
        val userRef = db.collection("users").document(uid)
        userRef.get()
            .addOnSuccessListener { document ->
                onSuccess(document.data)
            }
            .addOnFailureListener{e ->
                onFailure(e)
            }
    }


}