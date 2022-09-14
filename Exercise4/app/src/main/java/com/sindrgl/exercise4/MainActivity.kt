package com.sindrgl.exercise4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.sindrgl.exercise4.ui.theme.Exercise4Theme
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter

class ImagesViewModel : ViewModel() {

    var images = mutableStateListOf(
        ImageObj(
            "Harry Potter and the Philosopher's Stone",
            "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1170803558l/72193._SX120_SY180_.jpg"
        ),
        ImageObj(
            "Harry Potter and the Chamber of Secrets",
            "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1474169725i/15881._SY180_.jpg"
        ),
        ImageObj(
            "Nr 3",
            "https://m.psecn.photoshelter.com/img-get2/I0000H0rIQzSXuNA/sec=/fit=1200x1200/I0000H0rIQzSXuNA.jpg"
        ),
    )
        private set


}

data class ImageObj(val label: String, val url: String)

class MainActivity : ComponentActivity() {
    private val imagesViewModel by viewModels<ImagesViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Exercise4Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Main(imagesViewModel.images)
                }
            }
        }
    }
}

@Composable
fun Main(
    images: List<ImageObj>,
) {
    Box() {
        Column() {
            Row(
                modifier = Modifier
                    .weight(1.0f)
                    .fillMaxWidth()
            ) {
                List(images)
            }

            Row(
                modifier = Modifier
                    .weight(1.0f)
                    .fillMaxWidth()
            ) {
                Greeting("sindre")
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun List(images: List<ImageObj>) {
    LazyColumn {
        items(images.size) { index ->
            ShowImage(images[index])
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}

@Composable
fun ShowImage(image: ImageObj) {
    val painter = rememberImagePainter(
        data = image.url,
        builder = {
            crossfade(true)
        }
    )

    Box {
        Column {
            Image(
                painter = painter,
                contentDescription = image.label,
                modifier = Modifier.fillMaxWidth()
            )
            Text(image.label)
        }

        when (painter.state) {
            is ImagePainter.State.Loading -> {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
            else -> {
            }
        }
    }
}