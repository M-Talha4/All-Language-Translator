package com.language.alllanguagetranslator.Gallery_Input

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
import com.language.alllanguagetranslator.R
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage.*
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions
import kotlinx.android.synthetic.main.activity_detect_gallery_text.*
import java.util.*

class Detect_galleryText : AppCompatActivity() {
    lateinit var T_t_S: TextToSpeech
    var s_lang : Int? = null
    var t_lang : Int? = null
    lateinit var options: FirebaseTranslatorOptions
    lateinit var englishTranslator: FirebaseTranslator

    lateinit var rec_text :String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detect_gallery_text)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#BCCEF8")))


        val array = resources.getStringArray(R.array.T_languages)

        rec_text = intent.getStringExtra("text").toString()
        g_input.setText(rec_text)

        s_lang = EN
        t_lang = HI

        T_t_S = TextToSpeech(this) { status ->
            if (status != TextToSpeech.ERROR) {
                T_t_S.language = Locale.ENGLISH

            } }



        if (g_target_spin != null) {
            val Adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, array)
            g_target_spin.setAdapter(Adapter)
            g_target_spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {


                    when (position) {
                        0 -> { t_lang =  HI }
                        1 -> { t_lang =  AF}
                        2 -> { t_lang =  AR}
                        3 -> { t_lang =  BG}
                        4 -> { t_lang =  BN}
                        5 -> { t_lang =  CA}
                        6 -> { t_lang =  CS}
                        7 -> { t_lang =  DA}
                        8 -> { t_lang =  DE}
                        9 -> { t_lang =  EL}
                        10 -> { t_lang = EN}
                        11 -> { t_lang = ES}
                        12 -> { t_lang = FA}
                        13 -> { t_lang = FI}
                        14 -> { t_lang = FR}
                        15 -> { t_lang = GL}
                        16 -> { t_lang = GU}
                        17 -> { t_lang = HE}
                        18 -> { t_lang = HI}
                        19 -> { t_lang = HR}
                        20 -> { t_lang = HU}
                        21 -> { t_lang = ID}
                        22 -> { t_lang = IS}
                        23 -> { t_lang = IT}
                        24 -> { t_lang = JA}
                        25 -> { t_lang = KA}
                        26 -> { t_lang = KN}
                        27 -> { t_lang = KO}
                        28 -> { t_lang = LT}
                        29 -> { t_lang = LV}
                        30 -> { t_lang = MR}
                        31 -> { t_lang = MS}
                        32 -> { t_lang = NL}
                        33 -> { t_lang = NO}
                        34 -> { t_lang = PL}
                        35 -> { t_lang = PT}
                        36 -> { t_lang = RO}
                        37 -> { t_lang = RU}
                        38 -> { t_lang = SK}
                        39 -> { t_lang = SL}
                        40 -> { t_lang = SV}
                        41 -> { t_lang = SW}
                        42 -> { t_lang = TA}
                        43 -> { t_lang = TE}
                        44 -> { t_lang = TH}
                        45 -> { t_lang = TR}
                        46 -> { t_lang = UK}
                        47 -> { t_lang = UR}
                        48 -> { t_lang = VI}
                        49 -> { t_lang = ZH}
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




        g_input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                val rec_t = "" + g_input.text.toString()
                downloadModal(rec_t)
            }
        })



        gs_speaker_btn.setOnClickListener {
            if (s_lang == EN){

                if (g_input.text.toString().isEmpty()){
                    Toast.makeText(this,"text no found",Toast.LENGTH_SHORT).show()

                }else{
                    speak(g_input.text.toString())

                }
            } else{
                Toast.makeText(this,"language not supported",Toast.LENGTH_SHORT).show()
            }
        }

        gt_speaker_btn.setOnClickListener {
            if (t_lang == EN){

                if (g_output.text.toString().isEmpty()){
                    Toast.makeText(this,"text no found",Toast.LENGTH_SHORT).show()

                }else{
                    speak(g_output.text.toString())

                }
            } else{
                Toast.makeText(this,"language not supported",Toast.LENGTH_SHORT).show()
            }
        }


        sg_copy_btn.setOnClickListener {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val data = ClipData.newPlainText("Text", g_input.text.toString())
            clipboard.setPrimaryClip(data)
            Toast.makeText(this, "Coped", Toast.LENGTH_SHORT).show()
        }

        tg_copy_btn.setOnClickListener {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val data = ClipData.newPlainText("Text", g_output.text.toString())
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
            g_output.setText(s)
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