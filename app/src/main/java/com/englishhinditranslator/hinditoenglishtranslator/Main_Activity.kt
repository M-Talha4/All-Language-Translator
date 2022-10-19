package com.englishhinditranslator.hinditoenglishtranslator

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager.*
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.*
import com.englishhinditranslator.hinditoenglishtranslator.Camera_Translation.Image_Capture
import com.englishhinditranslator.hinditoenglishtranslator.Gallery_Input.Gallery_Activity
import com.englishhinditranslator.hinditoenglishtranslator.Setting.Setting
import com.englishhinditranslator.hinditoenglishtranslator.VoiceInput.Voice_Translation
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage.*
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage.*
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions.*
import kotlinx.android.synthetic.main.activity_main.*

class Main_Activity : AppCompatActivity() {
    lateinit var i :Intent
    private var pressedTime: Long = 0
    lateinit var englishTranslator: FirebaseTranslator
    lateinit var string :String
    lateinit var options: FirebaseTranslatorOptions
    private  val CAMERA_PERMISSION_CODE = 100
    private  val STORAGE_PERMISSION_CODE = 101
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#BCCEF8")))

        string = "a"

        options = Builder()
            .setSourceLanguage(EN)
            .setTargetLanguage(HI)
            .build()
        englishTranslator = getInstance().getTranslator(options)
        downloadModal(string)


         options = Builder()
            .setSourceLanguage(EN)
            .setTargetLanguage(UR)
            .build()
        englishTranslator = getInstance().getTranslator(options)
        downloadModal(string)

        if (checkSelfPermission(this,
                Manifest.permission.CAMERA) !==
            PackageManager.PERMISSION_GRANTED)
        {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.CAMERA), 1)
            } else {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.CAMERA), 1)
            }
        }








        val sharedPreferences = getDefaultSharedPreferences(this)
        val agreed = sharedPreferences.getBoolean("agreed", false)
        if (!agreed) {
            AlertDialog.Builder(this)
                .setTitle("Wifi connection Required")
                .setIcon(R.drawable.ic_wifi)
                .setPositiveButton("Agree") { dialog, which ->
                    val editor: SharedPreferences.Editor = sharedPreferences.edit()
                    editor.putBoolean("agreed", true)
                    editor.apply()
                }
                .setNegativeButton("", null)
                .setMessage("All the tools and features are offline. \n \n" +
                        "First time wifi connection is required to download offline language pack. \n \n" +
                        "Provided by Microtech IT solution")
                .show()
        }




        text_btn.setOnClickListener {
            i = Intent(this,Text_Translation::class.java)
            StartActivity(i) }

        voice_btn.setOnClickListener {

            i = Intent(this, Voice_Translation::class.java)
            StartActivity(i) }

        gallery_btn.setOnClickListener {

            i = Intent(this, Gallery_Activity::class.java)
            StartActivity(i) }

        camera_btn.setOnClickListener {
            i = Intent(this, Image_Capture::class.java )
            StartActivity(i) }

        setting_btn.setOnClickListener {
            i = Intent(this,Setting::class.java)
            StartActivity(i) }

        more_btn.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/developer?id=Microtech+IT+Solutions"))) }





    }



    private fun StartActivity(Activty: Intent) {

        //  ad here
        startActivity(Activty)

    }



    override fun onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()
            finish()
        } else {
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show()
        }
        pressedTime = System.currentTimeMillis() }








    private fun downloadModal(input: String) {
        val conditions = FirebaseModelDownloadConditions.Builder().requireWifi().build()

        englishTranslator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {

            }.addOnFailureListener{
                Toast.makeText(this, "language is Downloading please wait", Toast.LENGTH_SHORT).show()
            }
        translateLanguage(input) }






    private fun translateLanguage(input: String) {
        englishTranslator.translate(input).addOnSuccessListener { }.addOnFailureListener {

            if (checkInternet(this)) {
                Toast.makeText(this, "language Downloading", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Please connect to wifi", Toast.LENGTH_SHORT).show()
            } }}






    private fun checkInternet(context: Context): Boolean {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                    if ((checkSelfPermission(this,
                            Manifest.permission.CAMERA) ===
                                PackageManager.PERMISSION_GRANTED)) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }
}