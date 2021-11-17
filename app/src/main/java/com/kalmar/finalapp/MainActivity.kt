package com.kalmar.finalapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

private const val LAST_SELECTED_ITEM = "item"

class MainActivity : AppCompatActivity() {
    private lateinit var bottomMenu: BottomNavigationView
    private lateinit var donationIntent: Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomMenu = findViewById(R.id.bottom_menu)
        donationIntent = Intent(this, DonationActivity::class.java)
        donationIntent.flags = Intent.FLAG_ACTIVITY_NEW_DOCUMENT

        bottomMenu.setOnItemSelectedListener {
            replaceFragment(it.itemId)
            true
        }
        bottomMenu.selectedItemId = savedInstanceState?.getInt(LAST_SELECTED_ITEM) ?: R.id.main
    }

    private fun replaceFragment(id: Int) {
        val nextFragment = when (id) {
            R.id.main -> MainFragment()
            R.id.license -> LicenseFragment()
            else -> null
        }
        if (nextFragment != null)
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, nextFragment)
                .commit()
        else
            startActivity(donationIntent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(LAST_SELECTED_ITEM, bottomMenu.selectedItemId)
        super.onSaveInstanceState(outState)
    }
}