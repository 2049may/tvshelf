package com.pirrera.tvshelf

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.BuildConfig
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.messaging.FirebaseMessaging
import com.pirrera.tvshelf.auth.AuthViewModel
import com.pirrera.tvshelf.components.HomeScreen
import com.pirrera.tvshelf.components.ProfileScreen
import com.pirrera.tvshelf.components.SearchScreen
import com.pirrera.tvshelf.ui.theme.TVshelfTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.pirrera.tvshelf.auth.FirebaseEmulatorConfig
import com.pirrera.tvshelf.ui.theme.Background
import com.pirrera.tvshelf.ui.theme.Secondary
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.dependency
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseEmulatorConfig.configureFireBaseServices()

        enableEdgeToEdge()

        val authViewModel : AuthViewModel by viewModels()

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
           // val msg = getString(R.string.msg_token_fmt, token)
            Log.d(TAG, "BLABLABLA" + token)
           // Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })

        setContent {
            TVshelfTheme {
                DestinationsNavHost(navGraph = NavGraphs.root,
                    dependenciesContainerBuilder = {
                        dependency(authViewModel)
                    })
            }
        }
    }
}

@Destination
@Composable
fun MainScreen(
    navigator: DestinationsNavigator) {

    var selectedIcon by rememberSaveable { mutableStateOf("home") }
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val connectivityManager = remember {
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    val networkCallback = remember {
        object : ConnectivityManager.NetworkCallback() {
            override fun onLost(network: Network) {
                super.onLost(network)
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        message = "No internet connection",
                        duration = SnackbarDuration.Long
                    )
                }
            }
        }
    }

    DisposableEffect(Unit) {
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()

        connectivityManager.registerNetworkCallback(request, networkCallback)

        onDispose {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = {
            NavigationBar {
                BottomAppBar(
                    containerColor = Secondary,
                    tonalElevation = 8.dp,
                    actions = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            BottomAppBarButton(
                                "home",
                                isSelected = selectedIcon == "home",
                                onIconClick = { selectedIcon = "home" })

                            BottomAppBarButton(
                                "search",
                                isSelected = selectedIcon == "search",
                                onIconClick = { selectedIcon = "search" })

                            BottomAppBarButton(
                                "profile",
                                isSelected = selectedIcon == "profile",
                                onIconClick = { selectedIcon = "profile" })
                        }

                    },

                    )
            }
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(innerPadding)
        ) {
            when (selectedIcon) {
                "home" -> HomeScreen(navigator)
                "search" -> SearchScreen(navigator = navigator)
                "profile" -> ProfileScreen(navigator)
                else -> HomeScreen(navigator)
            }
        }
    }
}

@Composable
fun BottomAppBarButton(
    iconName: String,
    isSelected: Boolean,
    onIconClick: () -> Unit
) {
    val id = when (iconName) {
        "search" -> R.drawable.glass
        "profile" -> R.drawable.profile
        "home" -> R.drawable.home
        else -> R.drawable.glass
    }

    IconButton(onClick = onIconClick) {
        Image(
            painter = painterResource(id = id),
            contentDescription = iconName,
            modifier = Modifier
                .height(30.dp)
                .alpha(if (isSelected) 0.5f else 1f)
        )
    }
}