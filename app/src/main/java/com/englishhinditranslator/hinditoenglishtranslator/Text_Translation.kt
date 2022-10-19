package com.englishhinditranslator.hinditoenglishtranslator

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage.*
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage.*
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions.*
import kotlinx.android.synthetic.main.activity_text_translation.*
import java.util.*
import kotlin.properties.Delegates


class Text_Translation : AppCompatActivity() {
    lateinit var options: FirebaseTranslatorOptions
    lateinit var englishTranslator: FirebaseTranslator
    lateinit var T_t_S: TextToSpeech
    lateinit var text :String
    var s_lang : Int? = null
    var t_lang : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_translation)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#BCCEF8")))


        val s_Array = resources.getStringArray(R.array.S_languages)
        val t_Araay = resources.getStringArray(R.array.T_languages)
        s_lang = EN
        t_lang = HI

        T_t_S = TextToSpeech(this) { status ->
            if (status != TextToSpeech.ERROR) {
                T_t_S.language = Locale.ENGLISH

            } }



        if (t_source_spin != null) {
            val Adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, s_Array)
            t_source_spin.setAdapter(Adapter)
            t_source_spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                    when (position) {
                        0 -> { s_lang =  EN }
                        1 -> { s_lang =  AF}
                        2 -> { s_lang =  AR}
                        3 -> { s_lang =  BG}
                        4 -> { s_lang =  BN}
                        5 -> { s_lang =  CA}
                        6 -> { s_lang =  CS}
                        7 -> { s_lang =  DA}
                        8 -> { s_lang =  DE}
                        9 -> { s_lang =  EL}
                        10 -> { s_lang = EN}
                        11 -> { s_lang = ES}
                        12 -> { s_lang = FA}
                        13 -> { s_lang = FI}
                        14 -> { s_lang = FR}
                        15 -> { s_lang = GL}
                        16 -> { s_lang = GU}
                        17 -> { s_lang = HE}
                        18 -> { s_lang = HI}
                        19 -> { s_lang = HR}
                        20 -> { s_lang = HU}
                        21 -> { s_lang = ID}
                        22 -> { s_lang = IS}
                        23 -> { s_lang = IT}
                        24 -> { s_lang = JA}
                        25 -> { s_lang = KA}
                        26 -> { s_lang = KN}
                        27 -> { s_lang = KO}
                        28 -> { s_lang = LT}
                        29 -> { s_lang = LV}
                        30 -> { s_lang = MR}
                        31 -> { s_lang = MS}
                        32 -> { s_lang = NL}
                        33 -> { s_lang = NO}
                        34 -> { s_lang = PL}
                        35 -> { s_lang = PT}
                        36 -> { s_lang = RO}
                        37 -> { s_lang = RU}
                        38 -> { s_lang = SK}
                        39 -> { s_lang = SL}
                        40 -> { s_lang = SV}
                        41 -> { s_lang = SW}
                        42 -> { s_lang = TA}
                        43 -> { s_lang = TE}
                        44 -> { s_lang = TH}
                        45 -> { s_lang = TR}
                        46 -> { s_lang = UK}
                        47 -> { s_lang = UR}
                        48 -> { s_lang = VI}
                        49 -> { s_lang = ZH}
                    }
                    options = Builder()
                        .setSourceLanguage(s_lang!!)
                        .setTargetLanguage(t_lang!!)
                        .build()
                    englishTranslator = getInstance().getTranslator(options)
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                } } }




        if (t_target_spin != null) {
            val Adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, t_Araay)

            t_target_spin.setAdapter(Adapter)
            t_target_spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    when (position) {
                        0 -> {  t_lang = HI }
                        1 -> {  t_lang = AF}
                        2 -> {  t_lang = AR}
                        3 -> {  t_lang = BG}
                        4 -> {  t_lang = BN}
                        5 -> {  t_lang = CA}
                        6 -> {  t_lang = CS}
                        7 -> {  t_lang = DA}
                        8 -> {  t_lang = DE}
                        9 -> {  t_lang = EL}
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
                    options = Builder()
                        .setSourceLanguage(s_lang!!)
                        .setTargetLanguage(t_lang!!)
                        .build()
                    englishTranslator = getInstance().getTranslator(options)
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                } } }



        options = Builder()
            .setSourceLanguage(s_lang!!)
            .setTargetLanguage(t_lang!!)
            .build()
        englishTranslator = getInstance().getTranslator(options)


        // Text change Listener
        input_et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                val rec_t = "" + input_et.text.toString()
                downloadModal(rec_t)
            }
        })


        // English Text speaker
        st_speaker_btn.setOnClickListener {
            if (s_lang == EN){
                if (input_et.text.toString().isEmpty()){
                    Toast.makeText(this,"text no found",Toast.LENGTH_SHORT).show()
                }else{
                    speak(input_et.text.toString())
                }
        } else{
            Toast.makeText(this,"language not supported",Toast.LENGTH_SHORT).show() }
        }


        tt_speaker_btn.setOnClickListener {
            if (t_lang == EN){
                if (output_et.text.toString().isEmpty()){
                    Toast.makeText(this,"text no found",Toast.LENGTH_SHORT).show()
                }else{
                    speak(output_et.text.toString())
                }
            } else{
                Toast.makeText(this,"language not supported",Toast.LENGTH_SHORT).show() }
        }


        // Copy text to Clipboard
        st_copy_btn.setOnClickListener {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val data = ClipData.newPlainText("Text", input_et.text.toString())
            clipboard.setPrimaryClip(data)
            Toast.makeText(this, "Coped", Toast.LENGTH_SHORT).show()
        }

        tt_copy_btn.setOnClickListener {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val data = ClipData.newPlainText("Text", output_et.text.toString())
            clipboard.setPrimaryClip(data)
            Toast.makeText(this, "Coped", Toast.LENGTH_SHORT).show()
        }



    }
    // Download language Pack
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
            output_et.setText(s)
        }.addOnFailureListener {

            if (checkInternet(this)) {
                Toast.makeText(this, "language Downloading", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Please connect to wifi", Toast.LENGTH_SHORT).show()
            }


        } }
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


    // Text speaker method
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


}