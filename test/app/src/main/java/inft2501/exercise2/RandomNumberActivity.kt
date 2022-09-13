package inft2501.exercise2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View

class RandomNumberActivity : Activity() {
    var upperLimit = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_flag)
        upperLimit = intent.getIntExtra("upperLimit", upperLimit)
    }

    fun onClickDrawNewNumber(v: View?) {
        val intent = Intent()
        intent.putExtra("randomNumber1", (0..upperLimit).random())
        intent.putExtra("randomNumber2", (0..upperLimit).random())
        setResult(RESULT_OK, intent)
        finish()
    }
}