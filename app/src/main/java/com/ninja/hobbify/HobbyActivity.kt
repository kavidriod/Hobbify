package com.ninja.hobbify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.ninja.hobbify.ui.introscreen.IntroActivity

class HobbyActivity : AppCompatActivity() {

    private var userFirstTime = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hobby)

        loadData()

        if(!userFirstTime) {

            userFirstTime = false
            saveData()

            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
            finish()
        }

        setupActionBarWithNavController(findNavController(R.id.navHostFragment))
    }

    private fun saveData() {
        val sp = getSharedPreferences("sharedPref", MODE_PRIVATE)
        sp.edit().apply{
            putBoolean("FirstTime", userFirstTime)
            apply()
        }
    }

    private fun loadData() {
        val sp = getSharedPreferences("sharedPref", MODE_PRIVATE)
        userFirstTime = sp.getBoolean("FirstTime", true)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.navHostFragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}