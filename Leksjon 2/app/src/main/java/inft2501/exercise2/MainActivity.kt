package inft2501.exercise2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {
    private val numberRequestCode: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickStartRandomNumberActivity(v: View?) {
        val intent = Intent("inft2501.RandomNumberActivity")
        startActivityForResult(intent, numberRequestCode)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (resultCode != RESULT_OK) {
            Log.e("onActivityResult()", "Noe gikk galt")
            return
        }
        if (requestCode == numberRequestCode) {
            val generatedNumber = data.getIntExtra("number", 5)
            Toast.makeText(this, "Generert tall: $generatedNumber", Toast.LENGTH_LONG)
                .show()
            val textView = findViewById<View>(R.id.textView) as TextView
            textView.text = "Generert tall: $generatedNumber"
        }
    }
}