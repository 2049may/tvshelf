package com.pirrera.tvshelf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pirrera.tvshelf.ui.theme.TVshelfTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TVshelfTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize().background(Color(0xff212529)),
                    bottomBar = {
                        BottomAppBar(
                            containerColor = Color(0xFF2D3339),
                            tonalElevation = 8.dp,
                            actions = {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                                    IconButton(onClick = { /* do something */ }) {
                                        Icon(Icons.Filled.Check, contentDescription = "Localized description")
                                    }
                                    IconButton(onClick = { /* do something */ }) {
                                        Icon(
                                            Icons.Filled.Edit,
                                            contentDescription = "Localized description",
                                        )
                                    }
                                    IconButton(onClick = { /* do something */ }) {
                                        Icon(
                                            Icons.Filled.Edit,
                                            contentDescription = "Localized description",
                                        )
                                    }
                                }

                            },


                        )
                    },
                ) { innerPadding ->
                    Text(
                        modifier = Modifier.padding(innerPadding),
                        text = "Example of a scaffold with a bottom app bar."
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xff212529))
                    ) {
                }
            }}
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TVshelfTheme {
        Greeting("Android")
    }
}