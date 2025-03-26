package com.pirrera.tvshelf.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pirrera.tvshelf.db.FirebaseUserManager

class UserViewModel : ViewModel() {

    private val userManager = FirebaseUserManager()
    private val _userData = MutableLiveData<Map<String, Any>?>()
    val userData: MutableLiveData<Map<String, Any>?> = _userData

    fun loadUserData(uid : String) {
        userManager.getUserData(
            uid,
            onSuccess = {data -> _userData.value = data},
            onFailure = { _userData.value = null}
        )
    }
}