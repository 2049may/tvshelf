package com.pirrera.tvshelf.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pirrera.tvshelf.R
import com.pirrera.tvshelf.auth.AuthState
import com.pirrera.tvshelf.auth.AuthViewModel
import com.pirrera.tvshelf.destinations.HomeScreenDestination
import com.pirrera.tvshelf.destinations.LoginScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun SignupScreen(navigator: DestinationsNavigator, authViewModel: AuthViewModel) {

    var pseudo by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> navigator.navigate(HomeScreenDestination)
            is AuthState.Error -> Toast.makeText(context, (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            else -> Unit
        }

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff212529)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.default_pfp),
            contentDescription = "Logo"
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = pseudo,
            onValueChange = {
                pseudo = it
            },
            label = {
                Text("Pseudo")
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = {
                Text("Email")
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text("Password")
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB8C5D6)),
            onClick = {
                authViewModel.signup(pseudo, email, password)
            }) {
            Text("Create Account")
        }
        Spacer(
            modifier = Modifier
                .height(8.dp)
        )

        TextButton(onClick = {
            navigator.navigate(LoginScreenDestination)
        }) {
            Text("Already have an account? Log in", color = Color(0xFFB8C5D6))
        }

    }

}


