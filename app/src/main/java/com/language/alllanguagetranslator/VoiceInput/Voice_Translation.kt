package com.language.alllanguagetranslator.VoiceInput

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.language.alllanguagetranslator.R
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage.*
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage.*
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions.*
import kotlinx.android.synthetic.main.activity_voice_translation.*
import java.util.*

class Voice_Translation : AppCompatActivity() {
    lateinit var T_t_S: TextToSpeech
    private val REQUEST_CODE = 1
    lateinit var options: FirebaseTranslatorOptions
    lateinit var englishTranslator: FirebaseTranslator
    lateinit var text :String
    var s_lang : Int? = null
    var t_lang : Int? = null
    var s_position :Int ?= null
    var t_position :Int ?= null
    lateinit var s_voice_lang: String
    lateinit var t_voice_lang: String
    var state =true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice_translation)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#BCCEF8")))




        val s_Array = resources.getStringArray(R.array.S_languages)
        val t_Araay = resources.getStringArray(R.array.T_languages)

        s_voice_lang = "en-EN"
        t_voice_lang = "fr-FR"

        s_lang = EN
        t_lang = FR


        T_t_S = TextToSpeech(this) { status ->
            if (status != TextToSpeech.ERROR) {
                T_t_S.language = Locale.ENGLISH

            } }



        // set Source language list in  Drop down box
        if (v_source_spin != null) {
            val Adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, s_Array)
            v_source_spin.setAdapter(Adapter)
            v_source_spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                    when (position) {
                        0 -> { s_lang = EN
                            s_position =0 }
                        1 -> { s_lang = AF
                            s_position =1}
                        2 -> { s_lang = AR
                            s_position =2}
                        3 -> { s_lang = BG
                            s_position =3}
                        4 -> { s_lang = BN
                            s_position =4}
                        5 -> { s_lang = CA
                            s_position =5}
                        6 -> { s_lang = CS
                            s_position =6}
                        7 -> { s_lang = DA
                            s_position =7}
                        8 -> { s_lang = DE
                            s_position =8}
                        9 -> { s_lang = EL
                            s_position =9}
                        10 -> { s_lang = EN
                            s_position =10}
                        11 -> { s_lang = ES
                            s_position =11}
                        12 -> { s_lang = FA
                            s_position =12}
                        13 -> { s_lang = FI
                            s_position =13}
                        14 -> { s_lang = FR
                            s_position =14}
                        15 -> { s_lang = GL
                            s_position =15}
                        16 -> { s_lang = GU
                            s_position =16}
                        17 -> { s_lang = HE
                            s_position =17}
                        18 -> { s_lang = HI
                            s_position =18}
                        19 -> { s_lang = HR
                            s_position =19}
                        20 -> { s_lang = HU
                            s_position =20}
                        21 -> { s_lang = ID
                            s_position =21}
                        22 -> { s_lang = IS
                            s_position =22}
                        23 -> { s_lang = IT
                            s_position =23}
                        24 -> { s_lang = JA
                            s_position =24}
                        25 -> { s_lang = KA
                            s_position =25}
                        26 -> { s_lang = KN
                            s_position =26}
                        27 -> { s_lang = KO
                            s_position =27}
                        28 -> { s_lang = LT
                            s_position =28}
                        29 -> { s_lang = LV
                            s_position =29}
                        30 -> { s_lang = MR
                            s_position =30}
                        31 -> { s_lang = MS
                            s_position =31}
                        32 -> { s_lang = NL
                            s_position =32}
                        33 -> { s_lang = NO
                            s_position =33}
                        34 -> { s_lang = PL
                            s_position =34}
                        35 -> { s_lang = PT
                            s_position =35}
                        36 -> { s_lang = RO
                            s_position =36}
                        37 -> { s_lang = RU
                            s_position =37}
                        38 -> { s_lang = SK
                            s_position =38}
                        39 -> { s_lang = SL
                            s_position =39}
                        40 -> { s_lang = SV
                            s_position =40}
                        41 -> { s_lang = SW
                            s_position =41}
                        42 -> { s_lang = TA
                            s_position =42}
                        43 -> { s_lang = TE
                            s_position =43}
                        44 -> { s_lang = TH
                            s_position =44}
                        45 -> { s_lang = TR
                            s_position =45}
                        46 -> { s_lang = UK
                            s_position =46}
                        47 -> { s_lang = UR
                            s_position =47}
                        48 -> { s_lang = VI
                            s_position =48}
                        49 -> { s_lang = ZH
                            s_position =49} }
                    options = Builder()
                        .setSourceLanguage(s_lang!!)
                        .setTargetLanguage(t_lang!!)
                        .build()
                    englishTranslator = getInstance().getTranslator(options)
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                } } }




        // set Target language list in  Drop down box
        if (v_target_spin != null) {
            val Adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, t_Araay)
            v_target_spin.setAdapter(Adapter)
            v_target_spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                    when (position) {
                        0 -> { t_lang = FR
                            t_position =0}
                        1 -> { t_lang = AF
                            t_position =1}
                        2 -> { t_lang = AR
                            t_position =2}
                        3 -> { t_lang = BG
                            t_position =3}
                        4 -> { t_lang = BN
                            t_position =4}
                        5 -> { t_lang = CA
                            t_position =5}
                        6 -> { t_lang = CS
                            t_position =6}
                        7 -> { t_lang = DA
                            t_position =7}
                        8 -> { t_lang = DE
                            t_position =8}
                        9 -> { t_lang = EL
                            t_position =9}
                        10 -> { t_lang = EN
                            t_position =10}
                        11 -> { t_lang = ES
                            t_position =11}
                        12 -> { t_lang = FA
                            t_position =12}
                        13 -> { t_lang = FI
                            t_position =13}
                        14 -> { t_lang = FR
                            t_position =14}
                        15 -> { t_lang = GL
                            t_position =15}
                        16 -> { t_lang = GU
                            t_position =16}
                        17 -> { t_lang = HE
                            t_position =17}
                        18 -> { t_lang = HI
                            t_position =18}
                        19 -> { t_lang = HR
                            t_position =19}
                        20 -> { t_lang = HU
                            t_position =20}
                        21 -> { t_lang = ID
                            t_position =21}
                        22 -> { t_lang = IS
                            t_position =22}
                        23 -> { t_lang = IT
                            t_position =23}
                        24 -> { t_lang = JA
                            t_position =24}
                        25 -> { t_lang = KA
                            t_position =25}
                        26 -> { t_lang = KN
                            t_position =26}
                        27 -> { t_lang = KO
                            t_position =27}
                        28 -> { t_lang = LT
                            t_position =28}
                        29 -> { t_lang = LV
                            t_position =29}
                        30 -> { t_lang = MR
                            t_position =30}
                        31 -> { t_lang = MS
                            t_position =31}
                        32 -> { t_lang = NL
                            t_position =32}
                        33 -> { t_lang = NO
                            t_position =33}
                        34 -> { t_lang = PL
                            t_position =34}
                        35 -> { t_lang = PT
                            t_position =35}
                        36 -> { t_lang = RO
                            t_position =36}
                        37 -> { t_lang = RU
                            t_position =37}
                        38 -> { t_lang = SK
                            t_position =38}
                        39 -> { t_lang = SL
                            t_position =39}
                        40 -> { t_lang = SV
                            t_position =40}
                        41 -> { t_lang = SW
                            t_position =41}
                        42 -> { t_lang = TA
                            t_position =42}
                        43 -> { t_lang = TE
                            t_position =43}
                        44 -> { t_lang = TH
                            t_position =44}
                        45 -> { t_lang = TR
                            t_position =45}
                        46 -> { t_lang = UK
                            t_position =46}
                        47 -> { t_lang = UR
                            t_position =47}
                        48 -> { t_lang = VI
                            t_position =48}
                        49 -> { t_lang = ZH
                            t_position =49} }
                    options = Builder()
                        .setSourceLanguage(s_lang!!)
                        .setTargetLanguage(t_lang!!)
                        .build()
                    englishTranslator = getInstance().getTranslator(options)
                    downloadModal(input_box.text.toString())
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                } } }







        // On Source Mic click
        v_source_mic.setOnClickListener {

            when(s_position){
                0->{ s_voice_lang ="en-EN"}
                1->{ s_voice_lang ="af-AF"}
                2->{s_voice_lang  ="ar-AR"}
                3->{s_voice_lang  ="bg-BG"}
                4->{s_voice_lang  ="bn-BN"}
                5->{s_voice_lang  ="ca-CA"}
                6->{s_voice_lang  ="cs-CS"}
                7->{s_voice_lang  ="da-DA"}
                8->{s_voice_lang  ="de-DE"}
                9->{s_voice_lang  ="el-EL"}
                10->{s_voice_lang ="en-EN"}
                11->{s_voice_lang ="es-ES"}
                12->{s_voice_lang ="fa-FA"}
                13->{s_voice_lang ="fi-FI"}
                14->{s_voice_lang ="fr-FR"}
                15->{s_voice_lang ="gl-GL"}
                16->{s_voice_lang ="gu-GU"}
                17->{s_voice_lang ="he-HE"}
                18->{s_voice_lang ="hi-HI"}
                19->{s_voice_lang ="hr-HR"}
                20->{s_voice_lang ="hu-HU"}
                21->{s_voice_lang ="id-ID"}
                22->{s_voice_lang ="is-IS"}
                23->{s_voice_lang ="it-IT"}
                24->{s_voice_lang ="ja-JA"}
                25->{s_voice_lang ="ka-KA"}
                26->{s_voice_lang ="kn-KN"}
                27->{s_voice_lang ="ko-KO"}
                28->{s_voice_lang ="lt-LT"}
                29->{s_voice_lang ="lv-LV"}
                30->{s_voice_lang ="mr-MR"}
                31->{s_voice_lang ="ms-MS"}
                32->{s_voice_lang ="nl-NL"}
                33->{s_voice_lang ="no-NO"}
                34->{s_voice_lang ="pl-PL"}
                35->{s_voice_lang ="pt-PT"}
                36->{s_voice_lang ="ro-RO"}
                37->{s_voice_lang ="ru-RU"}
                38->{s_voice_lang ="sk-SK"}
                39->{s_voice_lang ="sl-SL"}
                40->{s_voice_lang ="sv-SV"}
                41->{s_voice_lang ="sw-SW"}
                42->{s_voice_lang ="ta-TA"}
                43->{s_voice_lang ="te-TE"}
                44->{s_voice_lang ="th-TH"}
                45->{s_voice_lang ="tr-TR"}
                46->{s_voice_lang ="uk-UK"}
                47->{s_voice_lang ="ur-UR"}
                48->{s_voice_lang ="vi-VI"}
                49->{s_voice_lang ="zh-ZH"}
            }

            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, s_voice_lang)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)

            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, s_voice_lang)
            intent.putExtra(RecognizerIntent.EXTRA_SUPPORTED_LANGUAGES, s_voice_lang)
            intent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, s_voice_lang)
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, s_voice_lang)
            intent.putExtra(RecognizerIntent.EXTRA_RESULTS, s_voice_lang)

            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak hare")
            try {
                @Suppress("DEPRECATION")
                startActivityForResult(intent, REQUEST_CODE)
            } catch (e: Exception) {
                Toast.makeText(this, " " + e.message, Toast.LENGTH_SHORT).show()
            } }




        //    On Target mic click
        v_target_mic.setOnClickListener {

            state =false

            when(t_position){
                0-> {t_voice_lang ="hi-HI"}
                1-> {t_voice_lang ="af-AF"}
                2-> {t_voice_lang ="ar-AR"}
                3-> {t_voice_lang ="bg-BG"}
                4-> {t_voice_lang ="bn-BN"}
                5-> {t_voice_lang ="ca-CA"}
                6-> {t_voice_lang ="cs-CS"}
                7-> {t_voice_lang ="da-DA"}
                8-> {t_voice_lang ="de-DE"}
                9-> {t_voice_lang ="el-EL"}
                10->{t_voice_lang ="en-EN"}
                11->{t_voice_lang ="es-ES"}
                12->{t_voice_lang ="fa-FA"}
                13->{t_voice_lang ="fi-FI"}
                14->{t_voice_lang ="fr-FR"}
                15->{t_voice_lang ="gl-GL"}
                16->{t_voice_lang ="gu-GU"}
                17->{t_voice_lang ="he-HE"}
                18->{t_voice_lang ="hi-HI"}
                19->{t_voice_lang ="hr-HR"}
                20->{t_voice_lang ="hu-HU"}
                21->{t_voice_lang ="id-ID"}
                22->{t_voice_lang ="is-IS"}
                23->{t_voice_lang ="it-IT"}
                24->{t_voice_lang ="ja-JA"}
                25->{t_voice_lang ="ka-KA"}
                26->{t_voice_lang ="kn-KN"}
                27->{t_voice_lang ="ko-KO"}
                28->{t_voice_lang ="lt-LT"}
                29->{t_voice_lang ="lv-LV"}
                30->{t_voice_lang ="mr-MR"}
                31->{t_voice_lang ="ms-MS"}
                32->{t_voice_lang ="nl-NL"}
                33->{t_voice_lang ="no-NO"}
                34->{t_voice_lang ="pl-PL"}
                35->{t_voice_lang ="pt-PT"}
                36->{t_voice_lang ="ro-RO"}
                37->{t_voice_lang ="ru-RU"}
                38->{t_voice_lang ="sk-SK"}
                39->{t_voice_lang ="sl-SL"}
                40->{t_voice_lang ="sv-SV"}
                41->{t_voice_lang ="sw-SW"}
                42->{t_voice_lang ="ta-TA"}
                43->{t_voice_lang ="te-TE"}
                44->{t_voice_lang ="th-TH"}
                45->{t_voice_lang ="tr-TR"}
                46->{t_voice_lang ="uk-UK"}
                47->{t_voice_lang ="ur-UR"}
                48->{t_voice_lang ="vi-VI"}
                49->{t_voice_lang ="zh-ZH"}
            }

            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, t_voice_lang)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)

            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, t_voice_lang)
            intent.putExtra(RecognizerIntent.EXTRA_SUPPORTED_LANGUAGES, t_voice_lang)
            intent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, t_voice_lang)
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, t_voice_lang)
            intent.putExtra(RecognizerIntent.EXTRA_RESULTS, t_voice_lang)

            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak hare")
            try {
                @Suppress("DEPRECATION")
                startActivityForResult(intent, REQUEST_CODE)
            } catch (e: Exception) {
                Toast.makeText(this, " " + e.message, Toast.LENGTH_SHORT).show()
            } }




        input_box.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (state ==true){
                    options = Builder()
                        .setSourceLanguage(s_lang!!)
                        .setTargetLanguage(t_lang!!)
                        .build()
                }else{
                    options = Builder()
                        .setSourceLanguage(t_lang!!)
                        .setTargetLanguage(s_lang!!)
                        .build()
                }

                state =true 
                englishTranslator = getInstance().getTranslator(options)

                val rec_t = "" + input_box.text.toString()
                downloadModal(rec_t)
            }
        })


        vs_speaker_btn.setOnClickListener {
            if (s_lang == EN){

                if (input_box.text.toString().isEmpty()){
                    Toast.makeText(this,"text no found",Toast.LENGTH_SHORT).show()

                }else{
                    speak(input_box.text.toString())

                }
            } else{
                Toast.makeText(this,"language not supported",Toast.LENGTH_SHORT).show()
            }
        }

        vt_speaker_btn.setOnClickListener {
            if (t_lang == EN){

                if (output_box.text.toString().isEmpty()){
                    Toast.makeText(this,"text no found",Toast.LENGTH_SHORT).show()

                }else{
                    speak(output_box.text.toString())

                }
            } else{
                Toast.makeText(this,"language not supported",Toast.LENGTH_SHORT).show()
            }
        }


        sv_copy_btn.setOnClickListener {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val data = ClipData.newPlainText("Text", input_box.text.toString())
            clipboard.setPrimaryClip(data)
            Toast.makeText(this, "Coped", Toast.LENGTH_SHORT).show()
        }

        tv_copy_btn.setOnClickListener {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val data = ClipData.newPlainText("Text", output_box.text.toString())
            clipboard.setPrimaryClip(data)
            Toast.makeText(this, "Coped", Toast.LENGTH_SHORT).show()
        }



    }


    // Set text in input textView
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        @Suppress("DEPRECATION")
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE) {
            @Suppress("DEPRECATION")
            if (resultCode == RESULT_OK && data != null) {

                val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>
                 text = Objects.requireNonNull(result)[0]
                input_box.setText(text)

            }
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
            output_box.setText(s)
        }.addOnFailureListener {


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
}