package inft2501.exercise2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog

class MainActivity2  : Activity() {
    private val numberRequestCode: Int = 1

    private var number1: Int = 3
    private var number2: Int = 5
    private var upperLimit: Int = 10

    private var number1TextView: TextView? = null
    private var number2TextView: TextView? = null
    private var answerView: EditText? = null
    private var upperLimitView: EditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main2_activity)


        number1TextView = findViewById<View>(R.id.textNumber1) as TextView
        number2TextView = findViewById<View>(R.id.textNumber2) as TextView
        answerView = findViewById<View>(R.id.answer) as EditText
        upperLimitView = findViewById<View>(R.id.upperlimit) as EditText

        // Update view values
        number1TextView?.text = "Tall 1: $number1"
        number2TextView?.text = "Tall 2: $number2"
        upperLimitView?.setText("$upperLimit")
    }

    fun addOnClick(v: View?) {
        val correctAnswer = number1 + number2
        val answer = Integer.parseInt(answerView?.text.toString())
        showResult(
            if (correctAnswer == answer) "${getString(R.string.correct)} $number1 + $number2 = $correctAnswer" else "${
                getString(
                    R.string.wrong_the_correct_answer_is
                )
            }: $number1 * $number2 = $correctAnswer"
        )
    }

    /**
     * On multiply numbers button click
     */
    fun multiplyOnClick(v: View?) {
        val correctAnswer = number1 * number2
        val answer = Integer.parseInt(answerView?.text.toString())
        showResult(
            if (correctAnswer == answer) "${getString(R.string.correct)} $number1 * $number2 = $correctAnswer" else "${
                getString(
                    R.string.wrong_the_correct_answer_is
                )
            }: $number1 * $number2 = $correctAnswer"
        )
    }

    private fun showResult(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        upperLimit = Integer.parseInt(upperLimitView?.text.toString())
        val intent = Intent("inft2501.RandomNumberActivity")
        intent.putExtra("upperLimit", upperLimit)
        startActivityForResult(intent, numberRequestCode)

    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (resultCode != RESULT_OK) {
            Log.e("onActivityResult()", "Noe gikk galt")
            return
        }
        if (requestCode == numberRequestCode) {
            val generatedNumber1 = data.getIntExtra("randomNumber1", 10)
            val generatedNumber2 = data.getIntExtra("randomNumber2", 10)
            number1 = generatedNumber1
            number2 = generatedNumber2
            number1TextView?.text = "Tall 1: $number1"
            number2TextView?.text = "Tall 2: $number2"
        }
    }
}