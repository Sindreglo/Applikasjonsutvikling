package com.sindrgl.exercise5

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import com.sindrgl.exercise5.ui.theme.Exercise5Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

const val URL = "https://bigdata.idi.ntnu.no/mobil/tallspill.jsp"

enum class AppView {
    START,
    GAME,
}

class MainActivity : ComponentActivity() {

    private val network: HttpWrapper = HttpWrapper(URL)
    private var gamestate = AppView.START

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Exercise5Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold {
                        Column {
                            if (gamestate == AppView.START) {
                                UserInfo()
                            } else {
                                GameOn()
                            }
                        }
                    }
                }
            }
        }
    }


    private fun performRequest(typeOfRequest: HTTP, parameterList: Map<String, String>) {
        CoroutineScope(Dispatchers.IO).launch {
            val response: String = try {
                when (typeOfRequest) {
                    HTTP.GET -> network.get(parameterList)
                    HTTP.POST -> network.post(parameterList)
                    HTTP.GET_WITH_HEADER -> network.getWithHeader(parameterList)
                }
            } catch (e: Exception) {
                Log.e("performRequest()", e.message!!)
                e.toString()
            }

            // Endre UI på hovedtråden
            MainScope().launch {
                setResult(response)
                //setResult(formatJsonString(response))
            }
        }
    }

    private fun setResult(response: String?) {
        if (response != null) {
            Log.w("Response", response)
            gamestate = AppView.GAME
        }
    }


    @Composable
    fun UserInfo() {
        val nameState = remember { mutableStateOf(TextFieldValue()) }
        val cardState = remember { mutableStateOf(TextFieldValue()) }
        fun add() {
            val requestParameters = mapOf<String, String>("navn" to nameState.value.text,
                "kortnummer" to cardState.value.text,)
            performRequest(HTTP.GET, requestParameters)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Free_Bitcoins.exe", fontSize = 25.sp)
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
    fun GameOn() {
        val nameState = remember { mutableStateOf(TextFieldValue()) }
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Free_Bitcoins.exe", fontSize = 25.sp)
            TextField(
                value = nameState.value,
                onValueChange = { nameState.value = it },
                label = { Text("Navn") })
            Button(onClick = { }) {
                Text("Start!")
            }
        }
    }
}