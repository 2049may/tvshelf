package com.pirrera.tvshelf.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import com.pirrera.tvshelf.R
import com.pirrera.tvshelf.data.Series
import com.pirrera.tvshelf.destinations.HomeScreenDestination
import com.pirrera.tvshelf.destinations.MainScreenDestination
import com.pirrera.tvshelf.destinations.SerieScreenDestination
import com.pirrera.tvshelf.ui.theme.Background
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultRecipient


@OptIn(ExperimentalMaterial3Api::class)
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
            .background(Background)
            //.windowInsetsPadding(WindowInsets.statusBars)
                ,
        topBar = {
            TopAppBar(
                modifier = Modifier.statusBarsPadding()
                    .fillMaxWidth()
                    //.height(40.dp)
                        ,
                title = {
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = serieName,
                            fontSize = 18.sp,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            modifier = Modifier.weight(1f)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navigator.popBackStack() }) {
                        Icon(painter = painterResource(id = R.drawable.back), contentDescription = "Back")
                    }
                }
            )

        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text(serieName)
            Text(serieOverview)
        }
    }


}