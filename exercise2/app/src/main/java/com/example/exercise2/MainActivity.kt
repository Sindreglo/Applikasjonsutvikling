package com.example.exercise2

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickVisAlertDialog(v: View?) {
        val value = (0..100).random().toString();
        Toast.makeText(applicationContext, value, Toast.LENGTH_SHORT).show()
    }
}