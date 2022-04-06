package com.chen.mygooglemap.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.chen.mygooglemap.R

/**
 * Main activity
 */
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_map)
        openMapFragment(GMapFragment.create(null))
    }

    private fun openMapFragment(frg: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.map, frg)
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()

        this.finish()
    }
}