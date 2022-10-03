package com.sindrgl.exercise5

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.sindrgl.exercise5.ui.theme.Exercise5Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

enum class AppView {
    START,
    GAME,
}

enum class States {
    GUESS_1,
    GUESS_2,
    GUESS_3,
    CORRECT,
    WRONG,
}

class GameViewModel : ViewModel() {

    private val BASE_URL = "https://bigdata.idi.ntnu.no/mobil/tallspill.jsp"
    private val network: HttpWrapper = HttpWrapper(BASE_URL)

    var start_game by mutableStateOf(AppView.START)
    var states by mutableStateOf(States.GUESS_1)
        private set

    var httpResponseMessage: String? = null
        private set

    private fun httpResponse(message: String) {
        Log.i("Snackbar", "Opening snackbar with message: $message")
        httpResponseMessage = message.substringBefore("null")
    }

    fun openView(view: AppView) {
        if (view == AppView.GAME) {
            states = States.GUESS_1
        }
        start_game = view
        httpResponseMessage = null
    }

    fun userInfo(name: String, cardNumber: String) {
        Log.i("StartGame", "Starting game by sending request")
        performRequest(network, mapOf("navn" to name, "kortnummer" to cardNumber)) {
            Log.i("StartGame", "Received response to startGame request: $it")
            val startSuccessMsg = "Oppgi et"
            if (it.contains(startSuccessMsg, ignoreCase = true)) {
                Log.i("StartGame", "StartGame request succeeded")
                openView(AppView.GAME)
            }
            httpResponse(it)
        }
    }

    fun gameOn(number: String) {
        Log.i("GuessNumber", "Guessing number by sending request")
        performRequest(network, mapOf("tall" to number)) {
            Log.i("GuessNumber", "Received response to GuessNumber request: $it")
            val wonSuccessMsg = "du har vunnet 100 kr som kommer inn på ditt kort"
            httpResponse(it)
            if (it.contains(wonSuccessMsg, ignoreCase = true)) {
                states = States.CORRECT
                Log.i("GuessNumber", "Correct guess")
            } else {
                when (states) {
                    States.GUESS_1 -> states = States.GUESS_2
                    States.GUESS_2 -> states = States.GUESS_3
                    States.GUESS_3 -> states = States.WRONG
                    else -> Log.e("Gamestate", "Gamestate is in an unknown state")
                }
            }
        }
    }
}

private fun performRequest(
    network: HttpWrapper,
    parameterList: Map<String, String>,
    onResult: (String) -> Unit
) {
    CoroutineScope(IO).launch {
        val response: String = try {
            network.get(parameterList)
        } catch (e: Exception) {
            Log.e("performRequest()", e.message!!)
            e.printStackTrace()
            e.toString()
        }
        MainScope().launch {
            Log.e("Response", response)
            onResult(response)
        }
    }
}

class MainActivity : ComponentActivity() {
    private val gameViewModel by viewModels<GameViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Exercise5Theme {
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold {
                        Column {
                            Text(
                                text = "FREE money NO risk!!!",
                                fontSize = 25.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .wrapContentHeight()
                            )
                            if (gameViewModel.httpResponseMessage != null) {
                                Text(
                                    text = gameViewModel.httpResponseMessage!!,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(100.dp)
                                        .wrapContentHeight()
                                )
                                Log.i("response", "${gameViewModel.httpResponseMessage}")
                            }
                            if (gameViewModel.start_game == AppView.START) {
                                UserView(gameViewModel::userInfo)
                            } else {
                                GuessView(
                                    gameViewModel::gameOn,
                                    gameViewModel.states,
                                    gameViewModel::openView
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UserView(startGame: (String, String) -> Unit,) {
    val nameState = remember { mutableStateOf(TextFieldValue()) }
    val cardState = remember { mutableStateOf(TextFieldValue()) }
    fun add() {
        startGame(nameState.value.text, cardState.value.text)
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = nameState.value,
            onValueChange = { nameState.value = it },
            label = { Text("Navn") })
        TextField(
            value = cardState.value,
            onValueChange = { cardState.value = it },
            label = { Text("Kortnummer") }
        )
        Button(onClick = { add() }) {
            Text("Start!")
        }
    }
}

@Composable
fun GuessView(
    guessNumber: (String) -> Unit,
    states: States,
    setViewOpen: (AppView) -> Unit,
) {
    val guessState = remember { mutableStateOf(TextFieldValue()) }
    fun guess() {
        guessNumber(guessState.value.text)
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        when (states) {
            States.GUESS_1, States.GUESS_2, States.GUESS_3 -> {
                TextField(
                    value = guessState.value,
                    onValueChange = { guessState.value = it },
                    label = { Text("Gjett tall") })
                Button(onClick = { guess() }) {
                    Text("Gjett tall")
                }
            }
            States.WRONG -> {
                Button(onClick = { setViewOpen(AppView.START) }) {
                    Text("Start nytt spill")
                }
            }
            States.CORRECT -> {
                Button(onClick = { setViewOpen(AppView.START) }) {
                    Text("Start nytt spill")
                }
            }
        }
    }
}