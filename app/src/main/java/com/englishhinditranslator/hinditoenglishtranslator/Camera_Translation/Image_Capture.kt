package com.englishhinditranslator.hinditoenglishtranslator.Camera_Translation

import android.app.ActionBar
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.englishhinditranslator.hinditoenglishtranslator.R.*
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.android.synthetic.main.activity_image_capture.*
import java.io.IOException


class Image_Capture : AppCompatActivity() {
    lateinit var imageUri: Uri
    val PICTURE_RESULT =1
    lateinit var values: ContentValues
    var snap_text :String ?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_image_capture)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#BCCEF8")))



        detect_txt.setOnClickListener {
            if (snap_text == null){
                Toast.makeText(this,"Capture Image",Toast.LENGTH_SHORT).show()
            }else if (snap_text == ""){
                Toast.makeText(this,"no text found",Toast.LENGTH_SHORT).show()
            }else{
                startActivity(Intent(this,Camera_Text::class.java)
                    .putExtra("text",snap_text))
            }
        }



        capture_btn.setOnClickListener {

            values = ContentValues()
            values.put(MediaStore.Images.Media.TITLE, "Translator")
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera")

            imageUri = contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)!!

            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            startActivityForResult(intent, PICTURE_RESULT)
        }


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == PICTURE_RESULT && resultCode == RESULT_OK)
            try {
                camera_iamge.setImageURI(imageUri)
                extractTextFromUri(this,imageUri)

            } catch (e: Exception) {
                e.printStackTrace()
            }
    }





    private fun extractTextFromUri(context: Context?, _uri: Uri?) {
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        try {
            val image = InputImage.fromFilePath(context!!, _uri!!)
            recognizer.process(image)
                .addOnSuccessListener { visionText ->
                    snap_text=  visionText.text
                }
                .addOnFailureListener {
                }
        } catch (e: IOException) { }
    }


}