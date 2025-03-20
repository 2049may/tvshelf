package com.pirrera.tvshelf

import android.annotation.SuppressLint
import android.os.Bundle
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.google.firebase.BuildConfig
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.pirrera.tvshelf.auth.AuthViewModel
import com.pirrera.tvshelf.components.HomeScreen
import com.pirrera.tvshelf.components.ProfileScreen
import com.pirrera.tvshelf.components.SearchScreen
import com.pirrera.tvshelf.ui.theme.TVshelfTheme
//import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
//import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.pirrera.tvshelf.auth.FirebaseEmulatorConfig
//import com.pirrera.tvshelf.components.NavGraphs
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.dependency

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseEmulatorConfig.configureFireBaseServices()

        enableEdgeToEdge()

        val authViewModel : AuthViewModel by viewModels()


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
fun MainScreen(navigator: DestinationsNavigator) {
    var selectedIcon by remember { mutableStateOf<String?>("home") }


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff212529)),
        bottomBar = {
            NavigationBar {
                BottomAppBar(
                    containerColor = Color(0xFF2D3339),
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
                .background(Color(0xff212529))
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
        else -> R.drawable.glass // Default icon
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