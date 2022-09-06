package com.example.exercise3

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class AddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        initAddButton()
    }


    private fun initAddButton() {
        val button = findViewById<Button>(R.id.addbutton)
        button.setOnClickListener {
            finish()
        }
    }
}