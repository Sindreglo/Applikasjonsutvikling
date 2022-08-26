package inft2501.exercise2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View

class RandomNumberActivity : Activity() {
    private var randomNumber = 100;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_flag)
    }

    fun onClickAvsluttAktivitet(v: View?) {
        setResult(RESULT_OK, Intent().putExtra("number", randomNumber));
        // Toast.makeText(applicationContext, value, Toast.LENGTH_SHORT).show();
        finish()
    }

    fun onClickDrawNewNumber(v: View?) {
        randomNumber = (0..randomNumber).random();
    }
}