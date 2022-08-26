package inft2501.exercise2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView

class ChangeFlagActivity : Activity() {
    private var flagValue = R.drawable.norway

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_flag)
        flagValue = intent.getIntExtra("flag", flagValue)
        val image = findViewById<View>(R.id.imageView) as ImageView
        image.setImageResource(flagValue)
    }

    /**
     *  Avslutt aktivitet og gå tilbake til MainActivity, legg ved intent med riktig flagg
     */
    fun onClickAvsluttAktivitet(v: View?) {
        setResult(RESULT_OK, Intent().putExtra("flag", flagValue))
        finish()
    }

    /**
     *  Endre flaggverdien og bilde til motsatt flagg
     */
    fun onClickEndreFlagg(v: View?) {
        val currentImageIsUK: Boolean = flagValue == R.drawable.uk
        flagValue = if (currentImageIsUK) R.drawable.norway else R.drawable.uk
        (findViewById<View>(R.id.imageView) as ImageView).setImageResource(flagValue)
    }
}