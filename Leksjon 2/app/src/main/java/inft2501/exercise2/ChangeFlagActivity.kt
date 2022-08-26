package inft2501.exercise2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast

class ChangeFlagActivity : Activity() {
    private var flagValue = 100;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_flag)
    }

    /**
     *  Avslutt aktivitet og gå tilbake til MainActivity, legg ved intent med riktig flagg
     */
    fun onClickAvsluttAktivitet(v: View?) {
        setResult(RESULT_OK, Intent().putExtra("flag", flagValue));
        // Toast.makeText(applicationContext, value, Toast.LENGTH_SHORT).show();
        finish()
    }

    /**
     *  Endre flaggverdien og bilde til motsatt flagg
     */
    fun onClickEndreFlagg(v: View?) {
        flagValue = (0..flagValue).random();
    }
}