package com.splendapps.androidstoragekotlin

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.splendapps.androidstoragekotlin.databinding.ActivityImagePickerBinding

class ImagePickerActivity : AppCompatActivity() {

    private val REQ_GALLERY = 100
    private val REQ_CAMERA = 200

    lateinit var binding: ActivityImagePickerBinding

    private val galleryPermissionContract = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        if(it){
            pickImageFromGallery()
        }
    }

    private val galleryResult = registerForActivityResult(ActivityResultContracts.GetContent()){
        if(it!=null){
            binding.imageView.setImageURI(it)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImagePickerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView.setOnClickListener {
           showOptionDialog()
        }

    }

    private fun showOptionDialog() {

        AlertDialog.Builder(this).apply {
            setTitle("Select Image")
            setItems(arrayOf("From Gallery", "From Camera"), DialogInterface.OnClickListener { dialogInterface, i ->

                if(i==0){
                    // gallery
                    // with runtime permission
                    // galleryPermissionContract.launch(Manifest.permission.READ_EXTERNAL_STORAGE)


                    // with out runtime permission (using contract)
                    galleryResult.launch("image/*")
                }else{
                    // camera
                    var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(intent, REQ_CAMERA)
                }

            })
            show()
        }
    }

    private fun pickImageFromGallery() {
        var intent = Intent()
        // intent.action = Intent.ACTION_PICK
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        startActivityForResult(intent, REQ_GALLERY)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQ_GALLERY) {

            if (resultCode == RESULT_OK && data != null){
                var imageUri = data.data
                binding.imageView.setImageURI(imageUri)
            }

        } else if (requestCode == REQ_CAMERA) {
            if (resultCode == RESULT_OK && data != null){
               // var imageUri = data.data
               // binding.imageView.setImageURI(imageUri)
                val imageBitmap = data?.extras?.get("data") as Bitmap
                binding.imageView.setImageBitmap(imageBitmap)
            }
        }

    }

}