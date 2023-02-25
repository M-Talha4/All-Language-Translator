package com.language.alllanguagetranslator.Gallery_Input

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.language.alllanguagetranslator.R
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.android.synthetic.main.activity_gallery.*
import java.io.IOException


class Gallery_Activity : AppCompatActivity() {
    private val pickImage = 100
    private var imageUri: Uri? = null
    var snap_text :String ?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#BCCEF8")))





        pick_btn.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

        detect_btn.setOnClickListener{
            if (snap_text == null){
                Toast.makeText(this,"Import Image",Toast.LENGTH_SHORT).show()
            }else if (snap_text == ""){
                Toast.makeText(this,"no text found",Toast.LENGTH_SHORT).show()
            }else{
                startActivity(Intent(this,Detect_galleryText::class.java)
                    .putExtra("text",snap_text))
            }
        }




    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            imageView.setImageURI(imageUri)
            extractTextFromUri(this,imageUri)
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