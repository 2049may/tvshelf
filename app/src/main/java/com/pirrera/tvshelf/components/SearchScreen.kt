package com.pirrera.tvshelf.components

import android.annotation.SuppressLint
import android.view.KeyEvent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.pirrera.tvshelf.R
import com.pirrera.tvshelf.api.ApiViewModel
import com.pirrera.tvshelf.destinations.Destination
import com.pirrera.tvshelf.destinations.SerieScreenDestination
import com.pirrera.tvshelf.ui.theme.Primary
import com.pirrera.tvshelf.ui.theme.Secondary
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@com.ramcosta.composedestinations.annotation.Destination
@Composable
fun SearchScreen(navigator: DestinationsNavigator,viewModel: ApiViewModel = viewModel()) {
    var searchBar by remember { mutableStateOf("") }
    val searchList by viewModel.seriesSearch.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
    }

    val filteredList = searchList.filter { it.name.contains(searchBar, ignoreCase = true) }


    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Secondary)
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.glass),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 8.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))

                TextField(
                    value = searchBar,
                    onValueChange = { searchBar = it },
                    modifier = Modifier
                        .weight(1f)
                        .background(Secondary)
                        .height(50.dp),
                    textStyle = TextStyle(color = Primary, fontSize = 18.sp),
                    placeholder = { Text("Search...", color = Primary) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Secondary,
                        unfocusedContainerColor = Secondary,
                        disabledContainerColor = Secondary,
                        cursorColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            viewModel.fetchSeriesASerie(searchBar)
                        }
                    ),
                )
            }
        }
        LazyColumn(verticalArrangement = Arrangement.SpaceBetween) {
            items(filteredList) { series ->
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navigator.navigate(
                                SerieScreenDestination(
                                    serieId = series.id.toString(),
                                    serieName = series.name,
                                    serieOverview = series.overview,
                                    posterPath = series.posterPath,
                                    airDate = series.firstAirDate
                                )
                            )

                        }) {
                        AsyncImage(
                            model = "https://image.tmdb.org/t/p/w500/" + series.posterPath,
                            contentDescription = null,
                            modifier = Modifier
                                .height(135.dp)
                                .width(90.dp),
                        )
                        Column(
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(start = 10.dp)
                        ) {
                            Text(series.name, color = Primary)
                            Text(
                                series.originCountry.toString().replace("[", "").replace("]", ""),
                                color = Primary
                            )
                            Text(series.voteAverage.toString() + " / 10", color = Primary)
                        }
                    }
                    HorizontalDivider(
                        color = Color(0xFF000000),
                        thickness = 3.dp,
                    )
                }
            }
        }




}

