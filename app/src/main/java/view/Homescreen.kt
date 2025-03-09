package view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(start = true)
@Composable
fun Homescreen(navigator: DestinationsNavigator) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff212529)),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Home Screen")
    }
}