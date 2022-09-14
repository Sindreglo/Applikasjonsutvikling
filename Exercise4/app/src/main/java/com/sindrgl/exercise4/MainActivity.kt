package com.sindrgl.exercise4

import android.os.Bundle
import android.util.Log
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.sindrgl.exercise4.ui.theme.Exercise4Theme
import coil.compose.ImagePainter
import coil.compose.rememberAsyncImagePainter
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
            "Harry Potter and the Prisoner of Azkaban",
            "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1630547330i/5._SY180_.jpg"
        ),


        ImageObj(
            "Harry Potter and the Goblet of Fire",
            "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1554006152i/6._SX120_.jpg"
        ),
        ImageObj(
            "Harry Potter and the Order of the Phoenix",
            "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1660911061i/2._SY180_.jpg"
        ),
        ImageObj(
            "Harry Potter and the Half-Blood Prince",
            "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1587697303i/1._SX120_.jpg"
        ),

        ImageObj(
            "Harry Potter and the Deathly Hallows",
            "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1661250233i/136251._SY180_.jpg"
        ),
        ImageObj(
            "Harry Potter and the Half-Blood Prince",
            "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1587697303i/1._SX120_.jpg"
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
                imageFromURL()
            }
        }
    }
}

@Composable
fun List(images: List<ImageObj>) {
    LazyColumn {
        items(images.size) { index ->
            Button(onClick = { Log.w("click", "Clicked") }, modifier = Modifier.fillMaxWidth()) {
                Text(text = images[index].label)
            }
        }
    }
}

@Composable
fun imageFromURL() {
    // on below line we are creating a column,
    Column(
        // in this column we are adding modifier
        // to fill max size, mz height and max width
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .fillMaxWidth()
            // on below line we are adding
            // padding from all sides.
            .padding(10.dp),
        // on below line we are adding vertical
        // and horizontal arrangement.
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // on below line we are adding image for our image view.
        Image(
            // on below line we are adding the image url
            // from which we will  be loading our image.
            painter = rememberAsyncImagePainter("https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1587697303i/1._SX120_.jpg"),

            // on below line we are adding content
            // description for our image.
            contentDescription = "gfg image",

            // on below line we are adding modifier for our
            // image as wrap content for height and width.
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxHeight()
        )
    }
}