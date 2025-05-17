package com.pirrera.tvshelf.components

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.rememberAsyncImagePainter
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.storage
import com.pirrera.tvshelf.R
import com.pirrera.tvshelf.auth.AuthViewModel
import com.pirrera.tvshelf.destinations.LoginScreenDestination
import com.pirrera.tvshelf.ui.theme.Primary
import com.pirrera.tvshelf.ui.theme.Red
import com.pirrera.tvshelf.ui.theme.Secondary
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.io.File
import kotlinx.coroutines.tasks.await

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(navigator: DestinationsNavigator, authViewModel: AuthViewModel = viewModel()) {

    val user = Firebase.auth.currentUser
    val userId = user?.uid
    val pseudo = remember { mutableStateOf(user?.displayName ?: "oula") }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {

        User(pseudo.value)
        HorizontalDivider(
            color = Secondary,
            thickness = 1.dp,
        )

        FavoriteShows(userId, navigator)
        HorizontalDivider(
            color = Secondary,
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 10.dp)
        )

        CurrentlyWatching(
            userId = userId,
            navigator = navigator
        )
        HorizontalDivider(
            color = Secondary,
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 10.dp)
        )

        Statistics(userId)
        HorizontalDivider(
            color = Secondary,
            thickness = 1.dp
        )

        TextButton(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp), onClick = {
            authViewModel.signout()
            navigator.navigate(LoginScreenDestination)
        }) {
            Text(
                "Log out",
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
fun User(pseudo: String) {

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    var uploadedImageUrl by remember { mutableStateOf<String?>(null) }
    val userId = Firebase.auth.currentUser?.uid
    val db = FirebaseFirestore.getInstance()

    // recup la pp
    LaunchedEffect(userId) {
        if (userId != null) {
            db.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    val profilePicUrl = document.getString("profilePic")
                    uploadedImageUrl = profilePicUrl
                }
        }
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        uploadImageToFirebase(uri, context) { downloadUrl ->
            uploadedImageUrl = downloadUrl
            Log.d("ImageUpload", "Image uploadée avec succès : $downloadUrl")
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 15.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Image(
            painter = when {
                imageUri != null -> rememberAsyncImagePainter(imageUri)
                uploadedImageUrl != null -> rememberAsyncImagePainter(uploadedImageUrl)
                else -> painterResource(R.drawable.default_pfp)
            },
            contentDescription = "User profile picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .size(70.dp)
                .clickable { imagePickerLauncher.launch("image/*") }
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


fun getFileFromUri(context: Context, uri: Uri): File? {
    val contentResolver = context.contentResolver
    val tempFile = File.createTempFile("upload", ".jpg", context.cacheDir)
    tempFile.outputStream().use { output ->
        contentResolver.openInputStream(uri)?.use { input ->
            input.copyTo(output)
        }
    }
    return tempFile
}

fun uploadImageToFirebase(uri: Uri?, context: Context, onSuccess: (String) -> Unit) {

    val db = FirebaseFirestore.getInstance()
    val userId = Firebase.auth.currentUser?.uid


    if (uri == null) {
        Toast.makeText(context, "Aucune image sélectionnée !", Toast.LENGTH_SHORT).show()
        return
    }

    if (userId != null) {
        db.collection("users").document(userId).update("profilePic", uri.toString())
            .addOnSuccessListener {
                Toast.makeText(context, "Image de profil mise à jour !", Toast.LENGTH_SHORT).show()
            }

    }

    val storageRef = Firebase.storage("gs://tvshelf2049.firebasestorage.app")
        .getReference("profile_pictures")
    val fileName = "profile_pictures/${userId}.jpg"
    val imageRef = storageRef.child(fileName)

    val file = getFileFromUri(context, uri)
    if (file != null) {
        val fileUri = Uri.fromFile(file)

        imageRef.putFile(fileUri)
            .addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                    onSuccess(downloadUri.toString())
                }
            }
            .addOnFailureListener { exception ->
                Log.e("FirebaseUpload", "Erreur lors de l'upload", exception)
                Toast.makeText(context, "Échec de l'upload : ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    } else {
        Toast.makeText(context, "Impossible de traiter l'image", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun FavoriteShows(userId: String?, navigator: DestinationsNavigator) {
    var favorites by remember { mutableStateOf<List<Map<String, String>>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var numberOfFavorites = favorites.size

    if (userId != null) {
        val db = FirebaseFirestore.getInstance()
        val userDoc = db.collection("users").document(userId)

        LaunchedEffect(userId) {
            userDoc.get().addOnSuccessListener { document ->
                if (document.exists()) {
                    val retrievedFavorites =
                        document.get("favorites") as? List<Map<String, String>> ?: emptyList()
                    favorites = retrievedFavorites
                    numberOfFavorites = favorites.size
                } else {
                    favorites = emptyList()
                }
                loading = false
            }.addOnFailureListener {
                Log.e("Firestore", "Error fetching favorites: ${it.message}")
                loading = false
            }
        }
    } else {
        loading = false
    }

    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 15.dp)) {
        Text(
            text = "Favorite Shows",
            color = Primary,
            fontSize = 25.sp
        )

        if (loading) {
            ProfilePreloader(numberOfFavorites)
        } else {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (favorites.isEmpty()) {
                    item {
                        Text(
                            text = "No favorites yet, keep watching!",
                            color = Primary,
                            fontSize = 16.sp
                        )
                    }
                } else {
                    items(favorites) { favorite ->
                        BoxSeries(
                            showId = favorite["showId"] ?: "",
                            posterPath = favorite["posterPath"] ?: "",
                            navigator = navigator
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PlaceholderBox() {
    Box(
        modifier = Modifier
            .height(114.dp)
            .fillMaxWidth()
            .background(Secondary)
    )
}

@Composable
fun CurrentlyWatching(userId: String?, navigator: DestinationsNavigator) {
    var currentlyWatching by remember { mutableStateOf<List<Map<String, String>>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var numberOfCurrentlyWatching = currentlyWatching.size

    if (userId != null) {
        val db = FirebaseFirestore.getInstance()
        val userShowsStatusCollection =
            db.collection("users").document(userId).collection("showsStatus")

        LaunchedEffect(userId) {
            userShowsStatusCollection
                .whereEqualTo("watchState", "Watching")
                .get()
                .addOnSuccessListener { querySnapshot ->
                    val shows = querySnapshot.documents.mapNotNull { document ->
                        val posterPath = document.getString("posterPath")
                        val showId = document.id
                        if (posterPath != null) {
                            mapOf("showId" to showId, "posterPath" to posterPath)
                        } else {
                            null
                        }
                    }
                    currentlyWatching = shows
                    numberOfCurrentlyWatching = currentlyWatching.size
                    loading = false
                }
                .addOnFailureListener {
                    loading = false
                }
        }
    } else {
        loading = false
    }

    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 15.dp)) {
        Text(
            text = "Currently Watching",
            color = Primary,
            fontSize = 25.sp
        )

        if (loading) {
            ProfilePreloader(numberOfCurrentlyWatching)
        } else {

            LazyRow(
                verticalAlignment = Alignment.Top,
                contentPadding = PaddingValues(vertical = 5.dp),
                modifier = Modifier.padding(top = 5.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                if (currentlyWatching.isEmpty()) {
                    item {
                        Text(
                            text = "Nothing here... Start a new show !",
                            color = Primary,
                            fontSize = 16.sp
                        )
                    }
                } else {
                    items(currentlyWatching) { show ->
                        BoxSeries(
                            showId = show["showId"] ?: "",
                            posterPath = show["posterPath"] ?: "",
                            navigator = navigator
                        )
                    }
                }
            }
        }

    }
}


@SuppressLint("DefaultLocale")
@Composable
fun Statistics(userId: String?) {

    var finishedShows by remember { mutableStateOf(0) }
    var currentlyWatching by remember { mutableStateOf(0) }
    var averageRating by remember { mutableStateOf(0.0) }

    if (userId != null) {
        val db = FirebaseFirestore.getInstance()
        val userShowsStatusCollection =
            db.collection("users").document(userId).collection("showsStatus")

        LaunchedEffect(userId) {
            userShowsStatusCollection.get()
                .addOnSuccessListener { querySnapshot ->
                    val documents = querySnapshot.documents

                    finishedShows = documents.count { it.getString("watchState") == "Watched" }
                    currentlyWatching = documents.count { it.getString("watchState") == "Watching" }
                }
                .addOnFailureListener {
                    finishedShows = 0
                    currentlyWatching = 0
                    Log.e("Firestore", "Error fetching shows: ${it.message}")
                }
        }

        LaunchedEffect(userId) {
            val userSnapshot = db.collection("users").document(userId).get().await()
            averageRating = userSnapshot.getDouble("averageRating") ?: 0.0
        }


    }



    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 15.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.weight(1f),
                text = "Average Rating",
                color = Primary,
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
            )

            Text(
                modifier = Modifier.weight(1f),
                text = String.format("%.2f", averageRating),
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
                text = currentlyWatching.toString(),
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
                text = finishedShows.toString(),
                color = Primary,
                fontSize = 16.sp,
                textAlign = TextAlign.End,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
            )
        }


    }
}



@Composable
fun BoxSeries(showId: String, posterPath: String, navigator: DestinationsNavigator) {
    val imageUrl = "https://image.tmdb.org/t/p/w500$posterPath"

    val painter = rememberAsyncImagePainter(model = imageUrl)

    Box(
        modifier = Modifier
            .height(150.dp)
            .width(100.dp)
            .fillMaxWidth()
            .background(Secondary)
    ) {

        Image(
            painter = painter,
            contentDescription = "Poster for $showId",
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
fun ProfilePreloader(items: Int) {
    LazyRow(
        verticalAlignment = Alignment.Top,
        contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items) {
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(150.dp)
                    .background(color = Secondary)
            )
        }
    }
}

