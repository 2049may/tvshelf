package com.pirrera.tvshelf.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.pirrera.tvshelf.data.Series
import com.pirrera.tvshelf.destinations.HomeScreenDestination
import com.pirrera.tvshelf.destinations.SerieScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultRecipient


@Destination
@Composable
fun SerieScreen(navigator: DestinationsNavigator, serieName : String,serieOverview : String){
    Column {
        Text(serieName)
        Text(serieOverview)
    }

}