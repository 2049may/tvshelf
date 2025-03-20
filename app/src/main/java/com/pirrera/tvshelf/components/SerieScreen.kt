package com.pirrera.tvshelf.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.pirrera.tvshelf.R
import com.pirrera.tvshelf.data.Series
import com.pirrera.tvshelf.destinations.HomeScreenDestination
import com.pirrera.tvshelf.destinations.MainScreenDestination
import com.pirrera.tvshelf.destinations.SerieScreenDestination
import com.pirrera.tvshelf.ui.theme.Background
import com.pirrera.tvshelf.ui.theme.Yellow
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultRecipient
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun SerieScreen(
    navigator: DestinationsNavigator,
    serieName: String,
    serieOverview: String,
    posterPath: String?
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
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500/$posterPath",
                contentDescription = null,
                modifier = Modifier
                    .height(330.dp)
                    .width(220.dp),
            )
            Spacer(modifier = Modifier.height(10.dp))

            Rating(5)

        }
    }
}

@Composable
fun Rating(maxStars : Int = 5, posterWidth: Int = 220) {
    var selectedStars by remember { mutableStateOf(0) }

    Row(
        modifier = Modifier.width(posterWidth.dp),
        horizontalArrangement = Arrangement.spacedBy((-2).dp)
    ) {
        for (i in 1..maxStars) {
            IconButton(onClick = { selectedStars = i }) {
                Icon(
                    painter = painterResource(
                        id = if (i <= selectedStars) R.drawable.filledstar else R.drawable.emptystar
                    ),
                    contentDescription = "Star $i",
                    tint = Yellow
                )
            }
        }

    }
}

@Composable
fun WatchButton() {

    var watching by remember { mutableStateOf(false) }

}