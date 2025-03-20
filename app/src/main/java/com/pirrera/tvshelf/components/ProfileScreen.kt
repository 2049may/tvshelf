package com.pirrera.tvshelf.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.pirrera.tvshelf.R
import com.pirrera.tvshelf.auth.AuthViewModel
import com.pirrera.tvshelf.destinations.LoginScreenDestination
import com.pirrera.tvshelf.ui.theme.Primary
import com.pirrera.tvshelf.ui.theme.Red
import com.pirrera.tvshelf.ui.theme.Secondary
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(navigator: DestinationsNavigator,authViewModel: AuthViewModel = viewModel()) {

    val user = Firebase.auth.currentUser
    val pseudo = remember { mutableStateOf(user?.displayName?:"oula") }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {

        User(pseudo.value)
        HorizontalDivider(
            color = Secondary,
            thickness = 1.dp,
        )

        FavoriteShows()
        HorizontalDivider(
            color = Secondary,
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 10.dp)
        )

        CurrentlyWatching()
        HorizontalDivider(
            color = Secondary,
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 10.dp)
        )

        Statistics()
        HorizontalDivider(
            color = Secondary,
            thickness = 1.dp
        )

        TextButton(modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp), onClick = {
            authViewModel.signout()
            navigator.navigate(LoginScreenDestination)
        }) {
            Text("Log out",
                color = Red,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

    }
}

@Composable
fun User(pseudo : String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 15.dp),
        horizontalArrangement = Arrangement.Absolute.Left,
    ) {
        Image(
            painter = painterResource(R.drawable.default_pfp),
            contentDescription = "User profile picture",
            modifier = Modifier
                .clip(CircleShape)
                .size(70.dp)
        )

        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(horizontal = 10.dp)
        ) {
            Text(
                text = pseudo,
                color = Color.White,
                fontSize = 18.sp
            )

            Text(
                text = "@username",
                color = Color.White,
                fontSize = 16.sp
            )
        }

    }
}

@Composable
fun FavoriteShows() {
    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 15.dp)) {
        Text(
            text = "Favorite Shows",
            color = Primary,
            fontSize = 25.sp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(4) {
                BoxSeries(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun CurrentlyWatching() {
    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 15.dp)) {
        Text(
            text = "Currently Watching",
            color = Primary,
            fontSize = 25.sp
        )

        LazyRow(
            verticalAlignment = Alignment.Top,
            contentPadding = PaddingValues(vertical = 5.dp),
            modifier = Modifier.padding(top = 5.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(15) {
                BoxSeries()
            }
        }
    }
}

@Composable
fun Statistics() {
    Column(modifier = Modifier
        .padding(horizontal = 20.dp, vertical = 15.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.weight(1f),
                text = "Episodes this month",
                color = Primary,
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
            )

            Text(
                modifier = Modifier.weight(1f),
                text = "51",
                color = Primary,
                fontSize = 16.sp,
                textAlign = TextAlign.End,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
            )
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.weight(1f),
                text = "Now Watching",
                color = Primary,
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
            )

            Text(
                modifier = Modifier.weight(1f),
                text = "5 shows",
                color = Primary,
                fontSize = 16.sp,
                textAlign = TextAlign.End,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
            )
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.weight(1f),
                text = "Finished Shows",
                color = Primary,
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
            )

            Text(
                modifier = Modifier.weight(1f),
                text = "13",
                color = Primary,
                fontSize = 16.sp,
                textAlign = TextAlign.End,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
            )
        }


    }
}



@Composable
fun BoxSeries(modifier: Modifier = Modifier.width(76.dp)) {
    Box(
        modifier = modifier
            .height(114.dp)
            .background(color = Primary)
    )
}


//@Preview
//@Composable
//fun ProfileScreenPreview() {
//    ProfileScreen(navigator = DestinationsNavigator)
//}