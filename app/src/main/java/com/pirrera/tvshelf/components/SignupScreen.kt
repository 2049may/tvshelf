package com.pirrera.tvshelf.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pirrera.tvshelf.R
import com.pirrera.tvshelf.auth.AuthState
import com.pirrera.tvshelf.auth.AuthViewModel
import com.pirrera.tvshelf.destinations.LoginScreenDestination
import com.pirrera.tvshelf.destinations.MainScreenDestination
import com.pirrera.tvshelf.ui.theme.Background
import com.pirrera.tvshelf.ui.theme.Primary
import com.pirrera.tvshelf.ui.theme.Tertiary
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun SignupScreen(navigator: DestinationsNavigator, authViewModel: AuthViewModel) {

    var pseudo by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> navigator.navigate(MainScreenDestination)
            is AuthState.Error -> Toast.makeText(
                context,
                (authState.value as AuthState.Error).message,
                Toast.LENGTH_SHORT
            ).show()

            else -> Unit
        }

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.logo_tvshelf_500px),
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
            },
            textStyle = TextStyle(color = Primary)

        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = {
                Text("Email")
            },
            textStyle = TextStyle(color = Primary)

        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text("Password")
            },
            textStyle = TextStyle(color = Primary),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
            },
            label = {
                Text("Confirm Password")
            },
            textStyle = TextStyle(color = Primary),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            colors = ButtonDefaults.buttonColors(containerColor = Primary),
            onClick = {
                if (password != confirmPassword) {
                    Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                    return@Button
                } else authViewModel.signup(pseudo, email, password)
            },
            enabled = authState.value != AuthState.Loading,
            shape = RoundedCornerShape(8.dp)


        ) {
            Text("Create Account",
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                color = Background)
        }
        Spacer(
            modifier = Modifier
                .height(8.dp)
        )

        TextButton(onClick = {
            navigator.navigate(LoginScreenDestination)
        }) {
            Text("Already have an account? Log in", color = Tertiary)
        }

    }

}


