package com.sindrgl.exercise4

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.sindrgl.exercise4.ui.theme.Exercise4Theme
import coil.compose.rememberAsyncImagePainter

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

    var currentImage by mutableStateOf(0)
        private set

    fun setImage(index: Int) {
        currentImage = index
    }

    fun showNextImage() {
        if (currentImage < images.size - 1) {
            currentImage++
        } else {
            currentImage = 0
        }
    }

    fun showPreviousImage() {
        if (currentImage == 0) {
            currentImage = images.size - 1
        } else {
            currentImage--
        }
    }

}

data class ImageObj(val label: String, val url: String)

class MainActivity : ComponentActivity() {
    private val imagesViewModel by viewModels<ImagesViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            Scaffold(

                // Creating a Top Bar
                topBar = { TopAppBar(
                    title = {
                        Row(horizontalArrangement = Arrangement.SpaceEvenly,verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                            Text("Books App!")
                            Row(horizontalArrangement = Arrangement.End,modifier = Modifier.fillMaxWidth()) {
                                Button(onClick = { imagesViewModel.showPreviousImage() }) {
                                    Text("Previous")
                                }
                                Button(onClick = { imagesViewModel.showNextImage() }) {
                                    Text("Next")
                                }
                            }
                        }
                    })},
                content = {

                    // Creating a Column to display Text
                    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {

                        // Fetching current app configuration
                        val configuration = LocalConfiguration.current

                        // When orientation is Landscape
                        when (configuration.orientation) {
                            Configuration.ORIENTATION_LANDSCAPE -> {
                                Main(imagesViewModel.images,
                                    imagesViewModel::setImage,
                                    imagesViewModel.currentImage,
                                    2,
                                )
                            }

                            // Other wise
                            else -> {
                                Main(imagesViewModel.images,
                                    imagesViewModel::setImage,
                                    imagesViewModel.currentImage,
                                1,
                                )
                            }
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun Main(
    images: List<ImageObj>,
    setImage: (Int) -> Unit,
    currentImage: Int,
    orientation: Int,
) {
        if (orientation == 1) {
            Column() {
                Row(
                    modifier = Modifier
                        .weight(1.0f)
                        .fillMaxWidth()
                ) {
                    ListFragment(images, setImage)
                }

                Row(
                    modifier = Modifier
                        .weight(1.0f)
                        .fillMaxWidth()
                ) {
                    ImageFragment(images, currentImage)
                }
            }
        } else {
            Column() {
                Row(horizontalArrangement = Arrangement.SpaceEvenly,verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    ListFragment(images, setImage)
                    ImageFragment(images, currentImage)
                }
            }
        }
}

@Composable
fun ListFragment(images: List<ImageObj>, setImage: (Int) -> Unit) {
    LazyColumn {
        items(images.size) { index ->
            Button(onClick = { setImage(index) }, modifier = Modifier.width(400.dp)) {
                Text(text = images[index].label)
            }
        }
    }
}

@Composable
fun ImageFragment(
    images: List<ImageObj>,
    currentImage: Int,
) {
    Column(modifier = Modifier.fillMaxHeight(), Arrangement.SpaceBetween) {
        ImageFromURL(images[currentImage])
    }
}

@Composable
fun ImageFromURL(
    image: ImageObj,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(10.dp),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(image.label)
        Image(
            painter = rememberAsyncImagePainter(image.url),
            contentDescription = image.label,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxHeight()
        )
    }
}