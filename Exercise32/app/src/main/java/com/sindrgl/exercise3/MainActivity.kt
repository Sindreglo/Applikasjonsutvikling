package com.sindrgl.exercise3

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.sindrgl.exercise3.ui.theme.Exercise3Theme

class MainActivity : ComponentActivity() {
    private val friendsViewModel by viewModels<FriendsViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Exercise3Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    mainBox(friends = friendsViewModel.friends, addFriend = friendsViewModel::addFriend , editFriend = friendsViewModel::updateFriend)
                }
            }
        }
    }
}

class FriendsViewModel : ViewModel() {

    var friends = mutableStateListOf<Friend>()
        private set

    fun addFriend(name: String, birthDate: String) {
        val id = (friends.map { it.id }.maxOrNull() ?: 0) + 1
        Log.w("Id", id.toString())
        friends.add(Friend(id, name, birthDate))
    }

    fun updateFriend(id: Int, name: String, birthdate: String) {
        friends.filter { it.id == id }.forEach {
            it.name = name
            it.birthDate = birthdate
        }
    }
}

@Composable
fun mainBox(
    friends: List<Friend>,
    addFriend: (String, String) -> Unit,
    editFriend: (Int, String, String) -> Unit) {
    FriendsListScreen(friends, addFriend)
}

@Composable
fun FriendsListScreen(
    friends: List<Friend>,
    addFriend: (String, String) -> Unit
) {
    Column {
        LazyColumn(contentPadding = PaddingValues(8.dp)) {
            item {
                Text(text = "Legg til venn")
                addFriendBox(addFriend)
                Divider()
            }
        }
    }
}

@Composable
fun addFriendBox(
    addFriend: (String, String) -> Unit
) {
    val nameState = remember { mutableStateOf(TextFieldValue()) }
    val birthdateState = remember { mutableStateOf(TextFieldValue()) }
    fun add() {
        addFriend(nameState.value.text, birthdateState.value.text)
    }
    Column {
        TextField(
            value = nameState.value,
            onValueChange = { nameState.value = it },
            label = { Text("Navn") })
        TextField(
            value = birthdateState.value,
            onValueChange = { birthdateState.value = it },
            label = { Text("Fødselsdag") }
        )
        Button(onClick = { add() }) {
            Text("Legg til venn")
        }
    }
}