package com.sindrgl.exercise4

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import coil.compose.rememberAsyncImagePainter

class ImagesViewModel : ViewModel() {

    var images = mutableStateListOf(
        ImageObj(
            "Harry Potter and the Philosopher's Stone",
            "Harry Potter bor i et kott hos teite onkel Wiktor og tante Petunia. Han har ikke feiret bursdag på 11 år og behandles dårlig. En dag tar livet hans en magisk vending. Han får et opptaksbrev fra Galtvort høyere skole for hekseri og trolldom.",
            "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1170803558l/72193._SX120_SY180_.jpg"
        ),
        ImageObj(
            "Harry Potter and the Chamber of Secrets",
            "Harry Potter gleder seg til å begynne på skolen igjen etter en lang og kjedelig sommer hos Dumlingene. Rett før skolestart blir han advart mot å dra tilbake, fordi livsfarlige krefter skal være i sving på Galtvort. Harry trosser advarselen, og snart står han overfor svart magi som krever alt han har av mot og trolldomskunnskap.",
            "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1474169725i/15881._SY180_.jpg"
        ),
        ImageObj(
            "Harry Potter and the Prisoner of Azkaban",
            "Harry Potters tredje år på Galtvort begynner med truende skyer: Voldemorts hjelper, morderen Sirius Svaart, har rømt fra fengselet Azkaban.",
            "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1630547330i/5._SY180_.jpg"
        ),
        ImageObj(
            "Harry Potter and the Goblet of Fire",
            "Harry Potter skal snart tilbake på skolen og starte sitt fjerde år med trollmannsstudier på Galtvort. Familien Wiltersen dukker uanmeldt opp og tar ham med til verdensmesterskapet i rumpeldunk.",
            "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1554006152i/6._SX120_.jpg"
        ),
        ImageObj(
            "Harry Potter and the Order of the Phoenix",
            "De færreste tror på at Voldemort har kommet tilbake, spesielt ikke magiministeren. Han sender derfor en ny lærer i forsvar mot svartekunster til Galtvort, som også har som oppgave å kontrollere Humlesnurr. Harry havner snart i klammeri med henne, men det er verre ting i vente.",
            "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1660911061i/2._SY180_.jpg"
        ),
        ImageObj(
            "Harry Potter and the Half-Blood Prince",
            "Fyrst Voldemort strammer grepet om både gomper og trollmenn, og Galtvort er ikke lenger noe fristed fra farer. Harry mistenker at farene kan lure til og med inne på slottet, men Humlesnurr er mer opptatt av å forberede ham på det endelige oppgjøret. Sammen jobber de for å finne svakheten i Voldemorts forsvarsverk, og med dette for øye rekrutterer Humlesnurr sin gamle venn og kollega Horatsion Snilehorn, som han tror sitter på viktig informasjon.",
            "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1587697303i/1._SX120_.jpg"
        ),
        ImageObj(
            "Harry Potter and the Deathly Hallows",
            "Voldemort vokser seg stadig sterkere, og han har nå makten over Magiministeriet og Galtvort. Harry, Ronny og Hermine bestemmer seg for å fullføre Humlesnurrs arbeid for å bekjempe den mørke fyrsten. Håpet tynnes ut, og for å klare oppgaven må alt gå som planlagt.",
            "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1661250233i/136251._SY180_.jpg"
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

data class ImageObj(val title: String, val description: String, val url: String)

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
    LazyColumn(modifier = Modifier.width(400.dp)) {
        items(images.size) { index ->
            Column(
                Modifier
                    .clickable { setImage(index) }
                    .padding(8.dp)) {
                Row(horizontalArrangement = Arrangement.Start,verticalAlignment = Alignment.CenterVertically) {
                    Text(text = images[index].title)
                }
                Divider()
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

        Text(image.title, fontWeight = FontWeight.Bold)
        Text(image.description, textAlign = TextAlign.Center)
        Image(
            painter = rememberAsyncImagePainter(image.url),
            contentDescription = image.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxHeight()
        )
    }
}