package com.sindrgl.exercise4

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction

class MainActivity : FragmentActivity(), Fragment1.OnFragmentInteractionListener  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setOrientation(resources.configuration)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setOrientation(newConfig)
    }

    private fun setOrientation(config: Configuration) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        val isPortrait: Boolean = config.orientation == Configuration.ORIENTATION_PORTRAIT
        transaction.replace(R.id.content, if (isPortrait) Fragment1() else Fragment2())
        transaction.commit()
    }

    override fun onFragmentInteraction(text: String?) {
        val fragment2 = supportFragmentManager.findFragmentById(R.id.fragment2) as Fragment2
        fragment2.setText(text)
    }
}