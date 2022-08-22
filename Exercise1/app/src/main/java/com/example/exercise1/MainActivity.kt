package com.example.exercise1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(meny: Menu): Boolean{
        super.onCreateOptionsMenu(meny)
        meny.add("Sindre")
        meny.add("Glomnes")
        Log.d("INFT2501","meny laget")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        if (item.title.equals("Sindre")) {
            Log.d("Leksjon", "Valg 'Sindre' er trykket av brukeren")
        }
        if (item.title.equals("Glomnes")) {
            Log.d("Leksjon", "Valg 'Glomnes' er trykket av brukeren")
        }
        return true
    }
}