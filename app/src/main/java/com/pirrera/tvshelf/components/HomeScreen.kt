package com.pirrera.tvshelf.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.compose.SubcomposeAsyncImage
import com.pirrera.tvshelf.api.ApiViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(viewModel: ApiViewModel = viewModel()){
    val seriesList by viewModel.series.collectAsState()
    val actionAdventureList by viewModel.seriesAction.collectAsState()
    val fictionFantasyList by viewModel.seriesFictionFantasy.collectAsState()
    val crimeList by viewModel.seriesCrime.collectAsState()
    val comedyList by viewModel.seriesComedy.collectAsState()
    val dramaList by viewModel.seriesDrama.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchSeries()
        viewModel.fetchSeriesByAction()
        viewModel.fetchSeriesByFictionFantasy()
        viewModel.fetchSeriesByCrime()
        viewModel.fetchSeriesByComedy()
        viewModel.fetchSeriesByDrama()
    }


    Column (modifier = Modifier
        .fillMaxSize().
        verticalScroll(rememberScrollState())){
        Box(modifier = Modifier.padding(top = 40.dp)) {
            Text("Action", color = Color(0xFFB8C5D6))
            LazyRow(verticalAlignment = Alignment.Top,
                contentPadding = PaddingValues(vertical = 15.dp),
                modifier = Modifier.padding(top = 25.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(actionAdventureList) { series ->
                        AsyncImage(
                            model = "https://image.tmdb.org/t/p/w500/" + series.posterPath,
                            contentDescription = null,
                            modifier = Modifier
                                .height(150.dp)
                                .width(100.dp),
                        )
                    }
                }
            }



        Box(modifier = Modifier.padding(top = 10.dp)) {
            Text("Science Fiction", color = Color(0xFFB8C5D6))
            LazyRow(verticalAlignment = Alignment.Top,
                contentPadding = PaddingValues(vertical = 15.dp),
                modifier = Modifier.padding(top = 25.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(fictionFantasyList) { series ->
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500/" + series.posterPath,
                        contentDescription = null,
                        modifier = Modifier.height(150.dp)
                            .width(100.dp)
                    )
                }
            }
        }

        Box(modifier = Modifier.padding(top = 10.dp)) {
            Text("Crime", color = Color(0xFFB8C5D6))
            LazyRow(verticalAlignment = Alignment.Top,
                contentPadding = PaddingValues(vertical = 15.dp),
                modifier = Modifier.padding(top = 25.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(crimeList) { series ->
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500/" + series.posterPath,
                        contentDescription = null,
                        modifier = Modifier.height(150.dp)
                            .width(100.dp)
                    )
                }
            }
        }

        Box(modifier = Modifier.padding(top = 10.dp)) {
            Text("Comedy", color = Color(0xFFB8C5D6))
            LazyRow(verticalAlignment = Alignment.Top,
                contentPadding = PaddingValues(vertical = 15.dp),
                modifier = Modifier.padding(top = 25.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(comedyList) { series ->
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500/" + series.posterPath,
                        contentDescription = null,
                        modifier = Modifier.height(150.dp)
                            .width(100.dp)
                    )
                }
            }
        }

        Box(modifier = Modifier.padding(top = 10.dp)) {
            Text("Drama", color = Color(0xFFB8C5D6))
            LazyRow(verticalAlignment = Alignment.Top,
                contentPadding = PaddingValues(vertical = 15.dp),
                modifier = Modifier.padding(top = 25.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(dramaList) { series ->
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500/" + series.posterPath,
                        contentDescription = null,
                        modifier = Modifier.height(150.dp)
                            .width(100.dp)
                    )
                }
            }
        }
    }
    @Composable
    fun BoxSeries(){
        Box(modifier = Modifier.width(100.dp).height(150.dp).background(Color(0xFFB8C5D6))){
        }
    }
}










