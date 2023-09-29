package com.splendapps.androidstoragekotlin

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.splendapps.androidstoragekotlin.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private val cameraContract =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                // true
                // open camera and pick image from that
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
            } else {
                // false
                showPermissionDialog("Camera Permission", "This permission is required to update your profile picture.")
            }
        }

    private val contactContract =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                // true
                // open camera and pick image from that
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
            } else {
                // false
                showPermissionDialog("Contact Permission" , "This permission is required to update your profile picture.")
            }
        }

    private fun showPermissionDialog(title :String, message:String) {
        AlertDialog.Builder(this).apply {
            setTitle(title)
            setMessage(message)
                .setPositiveButton(
                    "Settings",
                    DialogInterface.OnClickListener { dialogInterface, i ->
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri: Uri = Uri.fromParts("package", packageName, null)
                        intent.data = uri
                        startActivity(intent)
                    })
                .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialogInterface, i ->

                }).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnCamera.setOnClickListener {
            cameraContract.launch(Manifest.permission.CAMERA)
        }

        binding.btnContact.setOnClickListener {
            contactContract.launch(Manifest.permission.READ_CONTACTS)
        }

    }
}