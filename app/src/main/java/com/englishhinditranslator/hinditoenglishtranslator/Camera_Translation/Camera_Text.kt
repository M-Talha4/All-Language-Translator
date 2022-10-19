package com.englishhinditranslator.hinditoenglishtranslator.Camera_Translation

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.englishhinditranslator.hinditoenglishtranslator.R
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions
import kotlinx.android.synthetic.main.activity_camera_text.*
import kotlinx.android.synthetic.main.activity_detect_gallery_text.*
import java.util.*

class Camera_Text : AppCompatActivity() {
    lateinit var rec_text :String
    lateinit var T_t_S: TextToSpeech
    var s_lang : Int? = null
    var t_lang : Int? = null
    lateinit var options: FirebaseTranslatorOptions
    lateinit var englishTranslator: FirebaseTranslator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_text)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#BCCEF8")))


        val array = resources.getStringArray(R.array.T_languages)


        rec_text = intent.getStringExtra("text").toString()
        c_input.setText(rec_text)

        s_lang = FirebaseTranslateLanguage.EN
        t_lang = FirebaseTranslateLanguage.HI

        T_t_S = TextToSpeech(this) { status ->
            if (status != TextToSpeech.ERROR) {
                T_t_S.language = Locale.ENGLISH

            } }

        if (c_target_spin != null) {
            val Adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, array)
            c_target_spin.setAdapter(Adapter)
            c_target_spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {


                    when (position) {
                        0 -> { t_lang = FirebaseTranslateLanguage.HI
                        }
                        1 -> { t_lang = FirebaseTranslateLanguage.AF
                        }
                        2 -> { t_lang = FirebaseTranslateLanguage.AR
                        }
                        3 -> { t_lang = FirebaseTranslateLanguage.BG
                        }
                        4 -> { t_lang = FirebaseTranslateLanguage.BN
                        }
                        5 -> { t_lang = FirebaseTranslateLanguage.CA
                        }
                        6 -> { t_lang = FirebaseTranslateLanguage.CS
                        }
                        7 -> { t_lang = FirebaseTranslateLanguage.DA
                        }
                        8 -> { t_lang = FirebaseTranslateLanguage.DE
                        }
                        9 -> { t_lang = FirebaseTranslateLanguage.EL
                        }
                        10 -> { t_lang = FirebaseTranslateLanguage.EN
                        }
                        11 -> { t_lang = FirebaseTranslateLanguage.ES
                        }
                        12 -> { t_lang = FirebaseTranslateLanguage.FA
                        }
                        13 -> { t_lang = FirebaseTranslateLanguage.FI
                        }
                        14 -> { t_lang = FirebaseTranslateLanguage.FR
                        }
                        15 -> { t_lang = FirebaseTranslateLanguage.GL
                        }
                        16 -> { t_lang = FirebaseTranslateLanguage.GU
                        }
                        17 -> { t_lang = FirebaseTranslateLanguage.HE
                        }
                        18 -> { t_lang = FirebaseTranslateLanguage.HI
                        }
                        19 -> { t_lang = FirebaseTranslateLanguage.HR
                        }
                        20 -> { t_lang = FirebaseTranslateLanguage.HU
                        }
                        21 -> { t_lang = FirebaseTranslateLanguage.ID
                        }
                        22 -> { t_lang = FirebaseTranslateLanguage.IS
                        }
                        23 -> { t_lang = FirebaseTranslateLanguage.IT
                        }
                        24 -> { t_lang = FirebaseTranslateLanguage.JA
                        }
                        25 -> { t_lang = FirebaseTranslateLanguage.KA
                        }
                        26 -> { t_lang = FirebaseTranslateLanguage.KN
                        }
                        27 -> { t_lang = FirebaseTranslateLanguage.KO
                        }
                        28 -> { t_lang = FirebaseTranslateLanguage.LT
                        }
                        29 -> { t_lang = FirebaseTranslateLanguage.LV
                        }
                        30 -> { t_lang = FirebaseTranslateLanguage.MR
                        }
                        31 -> { t_lang = FirebaseTranslateLanguage.MS
                        }
                        32 -> { t_lang = FirebaseTranslateLanguage.NL
                        }
                        33 -> { t_lang = FirebaseTranslateLanguage.NO
                        }
                        34 -> { t_lang = FirebaseTranslateLanguage.PL
                        }
                        35 -> { t_lang = FirebaseTranslateLanguage.PT
                        }
                        36 -> { t_lang = FirebaseTranslateLanguage.RO
                        }
                        37 -> { t_lang = FirebaseTranslateLanguage.RU
                        }
                        38 -> { t_lang = FirebaseTranslateLanguage.SK
                        }
                        39 -> { t_lang = FirebaseTranslateLanguage.SL
                        }
                        40 -> { t_lang = FirebaseTranslateLanguage.SV
                        }
                        41 -> { t_lang = FirebaseTranslateLanguage.SW
                        }
                        42 -> { t_lang = FirebaseTranslateLanguage.TA
                        }
                        43 -> { t_lang = FirebaseTranslateLanguage.TE
                        }
                        44 -> { t_lang = FirebaseTranslateLanguage.TH
                        }
                        45 -> { t_lang = FirebaseTranslateLanguage.TR
                        }
                        46 -> { t_lang = FirebaseTranslateLanguage.UK
                        }
                        47 -> { t_lang = FirebaseTranslateLanguage.UR
                        }
                        48 -> { t_lang = FirebaseTranslateLanguage.VI
                        }
                        49 -> { t_lang = FirebaseTranslateLanguage.ZH
                        }
                    }
                    options = FirebaseTranslatorOptions.Builder()
                        .setSourceLanguage(s_lang!!)
                        .setTargetLanguage(t_lang!!)
                        .build()
                    englishTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options)
                    downloadModal(rec_text)
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented") } } }

        options = FirebaseTranslatorOptions.Builder()
            .setSourceLanguage(s_lang!!)
            .setTargetLanguage(t_lang!!)
            .build()
        englishTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options)


        c_input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                val rec_t = "" + c_input.text.toString()
                downloadModal(rec_t)
            }
        })



        cs_speaker_btn.setOnClickListener {
            if (s_lang == FirebaseTranslateLanguage.EN){

                if (c_input.text.toString().isEmpty()){
                    Toast.makeText(this,"text no found",Toast.LENGTH_SHORT).show()

                }else{
                    speak(c_input.text.toString())

                }
            } else{
                Toast.makeText(this,"language not supported",Toast.LENGTH_SHORT).show()
            }
        }

        ct_speaker_btn.setOnClickListener {
            if (t_lang == FirebaseTranslateLanguage.EN){

                if (c_output.text.toString().isEmpty()){
                    Toast.makeText(this,"text no found",Toast.LENGTH_SHORT).show()

                }else{
                    speak(c_output.text.toString())

                }
            } else{
                Toast.makeText(this,"language not supported",Toast.LENGTH_SHORT).show()
            }
        }


        Cst_copy_btn.setOnClickListener {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val data = ClipData.newPlainText("Text", c_input.text.toString())
            clipboard.setPrimaryClip(data)
            Toast.makeText(this, "Coped", Toast.LENGTH_SHORT).show()
        }

        Ctt_copy_btn.setOnClickListener {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val data = ClipData.newPlainText("Text", c_output.text.toString())
            clipboard.setPrimaryClip(data)
            Toast.makeText(this, "Coped", Toast.LENGTH_SHORT).show()
        }

    }

    private fun downloadModal(input: String) {
        val conditions = FirebaseModelDownloadConditions.Builder().requireWifi().build()

        englishTranslator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {

                translateLanguage(input)

            }.addOnFailureListener {
                Toast.makeText(applicationContext, "language is Downloading please wait", Toast.LENGTH_SHORT).show()
            }
    }
    private fun translateLanguage(input: String) {

        englishTranslator.translate(input).addOnSuccessListener { s ->
            c_output.setText(s)
        }.addOnFailureListener {

            if (checkInternet(this)) {
                Toast.makeText(this, "language Downloading", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Please connect to wifi", Toast.LENGTH_SHORT).show()
            }

        } }


    fun speak(toSpeak: String) {
        @Suppress("DEPRECATION")
        T_t_S.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null)
    }

    override fun onPause() {
        if (T_t_S.isSpeaking){
            T_t_S.stop()
        }
        super.onPause()
    }

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

}