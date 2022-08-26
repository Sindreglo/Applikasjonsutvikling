package inft2501.exercise2

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast

class MainActivity : Activity() {
    private val flagRequestCode: Int = 1
    private var flagValue = R.drawable.norway

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     *  Start ny aktivitet, og lytt etter resultatet
     */
    fun onClickStartChangeFlagActivity(v: View?) {
        val intent = Intent("inft2501.ChangeFlagActivity")
        intent.putExtra("flag", flagValue)
        startActivityForResult(intent, flagRequestCode)
    }

    /**
     *  Når aktivitene avslutter sjekk resultatet, endre flagget så det stemmer
     *  overens med resultatet.
     */
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (resultCode != RESULT_OK) {
            Log.e("onActivityResult()", "Noe gikk galt")
            return
        }
        if (requestCode == flagRequestCode) {
            flagValue = data.getIntExtra("flag", flagValue)
            val flag = findViewById<View>(R.id.imageView) as ImageView
            flag.setImageResource(flagValue)
        }
    }


    /**
     * Viser en AlertDialog med knapper, når knappene blir trykket på blir den valgte
     * knappen skrevet ut i en Toast
     */
    fun onClickVisAlertDialog(v: View?) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("AlertDialog")
        builder.setMessage("Forklarende tekst")
        builder.setPositiveButton("positiv") { dialog: DialogInterface, which: Int ->
            Toast.makeText(applicationContext, "positiv", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("negativ") { _, _ ->
            Toast.makeText(applicationContext, "negativ", Toast.LENGTH_SHORT).show()
        }
        builder.setNeutralButton("nøytral") { _, _ ->
            Toast.makeText(applicationContext, "nøytral", Toast.LENGTH_SHORT).show()
        }
        builder.show()
    }
}