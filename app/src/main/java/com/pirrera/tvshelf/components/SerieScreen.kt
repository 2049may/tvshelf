package com.pirrera.tvshelf.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.pirrera.tvshelf.data.Series
import com.pirrera.tvshelf.destinations.HomeScreenDestination
import com.pirrera.tvshelf.destinations.MainScreenDestination
import com.pirrera.tvshelf.destinations.SerieScreenDestination
import com.pirrera.tvshelf.ui.theme.Background
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultRecipient


@Destination
@Composable
fun SerieScreen(
    navigator: DestinationsNavigator,
    serieName: String,
    serieOverview: String
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        topBar = {
            Button(onClick = { navigator.popBackStack() }) {
                Text("Back")
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text(serieName)
            Text(serieOverview)
        }
    }

//    Button(onClick = { navigator.navigate(MainScreenDestination) }) {
//        Text("Back")
//    }
//    Column {
//        Text(serieName)
//        Text(serieOverview)
//    }

}