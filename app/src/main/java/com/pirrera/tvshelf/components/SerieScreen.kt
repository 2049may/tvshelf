package com.pirrera.tvshelf.components

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.pirrera.tvshelf.R
import com.pirrera.tvshelf.data.Series
import com.pirrera.tvshelf.destinations.HomeScreenDestination
import com.pirrera.tvshelf.destinations.MainScreenDestination
import com.pirrera.tvshelf.destinations.SerieScreenDestination
import com.pirrera.tvshelf.model.WatchState
import com.pirrera.tvshelf.ui.theme.Background
import com.pirrera.tvshelf.ui.theme.Green
import com.pirrera.tvshelf.ui.theme.Primary
import com.pirrera.tvshelf.ui.theme.Red
import com.pirrera.tvshelf.ui.theme.Secondary
import com.pirrera.tvshelf.ui.theme.Tertiary
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
    serieId : String,
    serieName: String,
    serieOverview: String,
    posterPath: String?,
    airDate: String?
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
        //.windowInsetsPadding(WindowInsets.statusBars)
        ,
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .statusBarsPadding()
                    .fillMaxWidth()
                //.height(40.dp)
                ,
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "",
                            fontSize = 18.sp,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            modifier = Modifier.weight(1f)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navigator.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.back),
                            contentDescription = "Back",
                            tint = Primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(Background)
            )

        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500/$posterPath",
                contentDescription = null,
                modifier = Modifier
                    .height(315.dp)
                    .width(210.dp),
            )
            Spacer(modifier = Modifier.height(10.dp))

            var watchState by rememberSaveable { mutableStateOf(WatchState.WatchNow) }
            Rating(5) { stars ->
                if (watchState == WatchState.WatchNow) {
                    watchState = WatchState.Watching
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                WatchButton(watchState) { newState ->
                    watchState = newState
                }

                FavoriteButton(showId = serieId, userId = FirebaseAuth.getInstance().currentUser?.uid ?: "", posterPath = posterPath)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = serieName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Tertiary,
                modifier = Modifier.padding(horizontal = 30.dp, vertical = 10.dp),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(5.dp))

            HorizontalDivider(
                color = Secondary,
                thickness = 2.dp,
                modifier = Modifier.padding(horizontal = 30.dp)
            )

            Informations(airDate)

            HorizontalDivider(
                color = Secondary,
                thickness = 2.dp,
                modifier = Modifier.padding(horizontal = 30.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Synopsis",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Primary,
                textAlign = TextAlign.Left,
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .fillMaxWidth()
            )

            Text(
                text = serieOverview,
                fontSize = 16.sp,
                color = Tertiary,
                modifier = Modifier.padding(horizontal = 30.dp),
                textAlign = TextAlign.Justify
            )

            Spacer(modifier = Modifier.height(10.dp))

        }
    }
}

@Composable
fun Rating(
    maxStars: Int = 5,
    onRated: (Int) -> Unit // Callback when rating is confirmed
) {
    var selectedStars by remember { mutableStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }
    var tempStars by remember { mutableStateOf(0) }

    Row(horizontalArrangement = Arrangement.spacedBy((-3).dp)) {
        for (i in 1..maxStars) {
            IconButton(onClick = {
                tempStars = i
                showDialog = true // Show confirmation dialog
            }) {
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

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirm Rating") },
            text = { Text("Are you sure you want to rate this series $tempStars stars?") },
            confirmButton = {
                Button(onClick = {
                    selectedStars = tempStars
                    showDialog = false
                    onRated(selectedStars) // Notify parent component to update watch state
                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}


@Composable
fun WatchButton(watchState: WatchState, onWatchStateChange: (WatchState) -> Unit) {
    OutlinedButton(
        modifier = Modifier
            .width(150.dp)
            .padding(5.dp),
        onClick = {
            onWatchStateChange(
                when (watchState) {
                    WatchState.WatchNow -> WatchState.Watching
                    WatchState.Watching -> WatchState.Watched
                    WatchState.Watched -> WatchState.WatchNow
                }
            )
        },
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = when (watchState) {
                WatchState.WatchNow, WatchState.Watched -> Color.Transparent
                WatchState.Watching -> Primary
            },
            contentColor = when (watchState) {
                WatchState.WatchNow -> Primary
                WatchState.Watching -> Background
                WatchState.Watched -> Green
            }
        ),
        elevation = ButtonDefaults.buttonElevation(5.dp),
        border = when (watchState) {
            WatchState.WatchNow -> BorderStroke(2.dp, Primary)
            WatchState.Watched -> BorderStroke(2.dp, Green)
            WatchState.Watching -> BorderStroke(2.dp, Primary)
        }
    ) {
        Text(
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            text = when (watchState) {
                WatchState.Watching -> "Watching"
                WatchState.Watched -> "Watched"
                else -> "Watch Now"
            }
        )
    }
}



@Composable
fun FavoriteButton(showId: String, posterPath: String?, userId: String) {
    val db = FirebaseFirestore.getInstance()
    val userDoc = db.collection("users").document(userId)

    var favorite by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        userDoc.get().addOnSuccessListener { document ->
            if (document.exists()) {
                val favorites = document.get("favorites") as? List<HashMap<String, String>> ?: emptyList()
                favorite = favorites.any { it["showId"] == showId }
            }
        }
    }

    IconButton(onClick = {
        favorite = !favorite
        val favoriteData = hashMapOf("showId" to showId, "posterPath" to posterPath)

        if (favorite) {
            userDoc.update("favorites", FieldValue.arrayUnion(favoriteData))
        } else {
            userDoc.update("favorites", FieldValue.arrayRemove(favoriteData))
        }
    }) {
        Icon(
            painter = painterResource(
                id = if (favorite) R.drawable.filledheart else R.drawable.emptyheart
            ),
            contentDescription = "Favorite",
            tint = Red
        )
    }
}



@Composable
fun Informations(airDate: String?) {
    Column(modifier = Modifier.padding(vertical = 5.dp)) {


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 5.dp)
        ) {

            Text(
                text = "X seasons", //TODO : recuperer le nombre de saisons
                fontSize = 16.sp,
                color = Primary,
                textAlign = TextAlign.Left,
                modifier = Modifier.weight(1f)
            )

            if (airDate != null) {
                Text(
                    text = airDate.trim().substring(0, 4),
                    fontSize = 16.sp,
                    color = Primary,
                    textAlign = TextAlign.Right,
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 5.dp)
        ) {

            Text(
                text = "DIRECTED BY",
                fontSize = 14.sp,
                color = Primary,
                textAlign = TextAlign.Left,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "dddd", // TODO : recuperer le réal
                textAlign = TextAlign.Right,
                modifier = Modifier.weight(1f)

            )

        }
    }
}

//@Preview
//@Composable
//fun RatingPreview() {
//
//    Rating()
//
//}


@Preview(showBackground = true)
@Composable
fun WatchButtonPreview() {

}