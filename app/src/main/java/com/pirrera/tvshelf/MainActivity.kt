package com.pirrera.tvshelf

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pirrera.tvshelf.ui.theme.TVshelfTheme
import com.ramcosta.composedestinations.DestinationsNavHost

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TVshelfTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xff212529)),
                    bottomBar = {
                        NavigationBar{
                            BottomAppBar(
                                containerColor = Color(0xFF2D3339),
                                tonalElevation = 8.dp,
                                actions = {
                                    var selectedIcon by remember { mutableStateOf<String?>(null) }
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceEvenly
                                    ) {
                                        BottomAppBarButton("home", isSelected = selectedIcon == "home", onIconClick = { selectedIcon = "home" })
                                        BottomAppBarButton("search", isSelected = selectedIcon == "search", onIconClick = { selectedIcon = "search" })
                                        BottomAppBarButton("profile", isSelected = selectedIcon == "profile", onIconClick = { selectedIcon = "profile" })

                                    }

                                },


                                )
                        }
                        }
                        ,
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xff212529))
                    ) {
                    }
                }
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
        else -> R.drawable.glass // TODO(): default à modifier
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