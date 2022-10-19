package com.englishhinditranslator.hinditoenglishtranslator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class Splash_Screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        supportActionBar?.hide()


        Handler().postDelayed({

            startActivity(Intent(this,Main_Activity::class.java))
            finish()
        },500)
    }

    override fun onBackPressed() {
        return
    }
}