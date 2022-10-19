package com.sindrgl.exercise7

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sindrgl.exercise7.R
import com.sindrgl.exercise7.managers.FileManager
import com.sindrgl.exercise7.managers.MyPreferenceManager
import com.sindrgl.exercise7.service.Database
import com.sindrgl.exercise7.ui.theme.Exercise7Theme
import kotlinx.coroutines.launch

const val THEME_KEY = "theme"
const val THEME_DARK = "dark"
const val THEME_LIGHT = "light"
const val DEFAULT_THEME_VALUE = THEME_DARK

class MainActivity : ComponentActivity() {
    private lateinit var db: Database
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Database(this)
        val movies = FileManager(this).readFileFromResFolder(R.raw.movies).split("-\n")
        movies.forEach {
            val lines = it.lines()
            db.insert(lines[0], lines[1], lines[2].split(","))
            Log.i(lines[0], lines[1] + " " + lines[2])
        }
        setContent {
            Navigation(db)
        }
    }
}

@Composable
private fun Navigation(db: Database) {
    val navController = rememberNavController()
    val isSystemDarkTheme = isSystemInDarkTheme()
    val (selectedTheme, setSelectedTheme) = remember { mutableStateOf(isSystemDarkTheme) }
    val preferenceManager =
        MyPreferenceManager(LocalContext.current, THEME_KEY, DEFAULT_THEME_VALUE)

    fun setTheme(theme: String?) = setSelectedTheme(
        when (theme) {
            THEME_DARK -> true
            THEME_LIGHT -> false
            else -> isSystemDarkTheme
        }
    )

    preferenceManager.watchKey {
        setTheme(it)
    }

    Exercise7Theme(selectedTheme) {
        Surface(color = MaterialTheme.colors.background) {
            NavHost(navController, startDestination = "movies") {
                composable(route = "movies") {
                    MainScreen(navController, db)
                }
                composable(route = "settings") {
                    SettingsScreen(navController)
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun MainScreen(navController: NavController, db: Database) {
    val defaultText = {
        val res = StringBuffer("")
        for (s in db.allMovies) res.append("$s\n")
        res.toString()
    }
    val (text, setText) = remember { mutableStateOf(defaultText()) }
    val state = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    fun showResults(list: ArrayList<String>) {
        val res = StringBuffer("")
        for (s in list) res.append("$s\n")
        setText(res.toString())
    }
    Scaffold(
        topBar = {
            TopAppBar(
                elevation = 4.dp,
                title = { Text("Movies") },
                backgroundColor = MaterialTheme.colors.primarySurface,
                actions = {
                    IconButton(onClick = { navController.navigate("settings") }) {
                        Icon(Icons.Rounded.Settings, null)
                    }
                })
        },
        floatingActionButton = {
            if (!state.isVisible) {
                ExtendedFloatingActionButton(
                    icon = { Icon(Icons.Rounded.MoreVert, "") },
                    text = { Text("Filters") },
                    onClick = { scope.launch { state.show() } },
                    elevation = FloatingActionButtonDefaults.elevation(8.dp),
                    backgroundColor = Color(0xFF6200EE),
                    contentColor = Color(0xFFFFFFFF)
                )
            }
        }
    ) {
        ModalBottomSheetLayout(
            sheetState = state,
            sheetContent = {
                LazyColumn {
                    item {
                        ListItem(
                            Modifier.clickable(onClick = {
                                showResults(db.allMovies)
                                scope.launch { state.hide() }
                            }),
                            text = { Text("All movies") }
                        )
                    }
                    item {
                        ListItem(
                            Modifier.clickable(onClick = {
                                showResults(db.allActors)
                                scope.launch { state.hide() }
                            }),
                            text = { Text("All actors") }
                        )
                    }
                    item {
                        ListItem(
                            Modifier.clickable(onClick = {
                                showResults(db.allMoviesAndActors)
                                scope.launch { state.hide() }
                            }),
                            text = { Text("All movies and actors") }
                        )
                    }
                    item {
                        ListItem(
                            Modifier.clickable(onClick = {
                                showResults(db.getMoviesByDirector("Christopher Nolan"))
                                scope.launch { state.hide() }
                            }),
                            text = { Text("All movies by \"Christopher Nolan\"") }

                        )
                    }
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Top 5 Movies IMDb",
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                )
                Spacer(Modifier.height(20.dp))
                Text(
                    text = text,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                )
            }
        }
    }
}

@Composable
fun SettingsScreen(navController: NavController) {
    var selectedTheme: String? = null
    val preferenceManager =
        MyPreferenceManager(LocalContext.current, THEME_KEY, DEFAULT_THEME_VALUE)

    preferenceManager.watchKey { selectedTheme = it }

    val radioOptions = listOf("Dark", "Light", "System theme")
    val (selectedOption, onOptionSelected) = remember {
        mutableStateOf(radioOptions.find { it.lowercase() == selectedTheme } ?: radioOptions[2])
    }
    fun handleSelectOption(option: String) {
        onOptionSelected(option)
        when (option.lowercase()) {
            THEME_DARK -> preferenceManager.set(THEME_DARK)
            THEME_LIGHT -> preferenceManager.set(THEME_LIGHT)
            else -> preferenceManager.set(null)
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                elevation = 4.dp,
                title = { Text("Settings") },
                backgroundColor = MaterialTheme.colors.primarySurface,
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Rounded.ArrowBack, null)
                    }
                })
        }
    ) {
        Column {
            Text(
                text = "Themes",
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .wrapContentHeight()
            )
            radioOptions.forEach { text ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = { handleSelectOption(text) }
                        )
                        .padding(horizontal = 16.dp)
                ) {
                    RadioButton(
                        selected = (text == selectedOption),
                        onClick = { handleSelectOption(text) }
                    )
                    Text(
                        text = text,
                        style = MaterialTheme.typography.body1.merge(),
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
    }
}