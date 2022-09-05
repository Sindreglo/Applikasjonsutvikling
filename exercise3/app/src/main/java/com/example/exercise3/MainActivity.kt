package com.example.exercise3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    private var friends: Array<String> = arrayOf()

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                RESULT_OK -> {
                    if (result.data != null) {
                        val username = result.data!!.getIntExtra("name", 0)
                        Toast.makeText(this, username, Toast.LENGTH_LONG)
                            .show()
                    }
                }
                else -> {
                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        friends = resources.getStringArray(R.array.friends)

        initAddButton()
        initList()
    }


    private fun initAddButton() {
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            startForResult.launch(Intent(this, AddActivity::class.java))
        }
    }

    private fun initList() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_activated_1, friends)
        val listView = findViewById<ListView>(R.id.listView)
        listView.adapter = adapter
        listView.choiceMode = ListView.CHOICE_MODE_SINGLE
    }
}