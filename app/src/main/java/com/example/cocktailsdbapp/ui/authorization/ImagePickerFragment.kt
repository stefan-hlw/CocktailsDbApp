//package com.example.cocktailsdbapp.ui.authorization
//
//import android.app.Activity
//import android.app.AlertDialog
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.net.Uri
//import android.provider.MediaStore
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.core.content.ContextCompat
//import androidx.core.content.FileProvider
//import androidx.fragment.app.Fragment
//import com.example.cocktailsdbapp.MainActivity
//import java.io.File
//import java.io.IOException
//import java.text.SimpleDateFormat
//import java.util.Date
//import java.util.Locale
//
//open class ImagePickerFragment : Fragment() {
//
//    private lateinit var currentPhotoPath: String
//    private lateinit var imageUri: Uri
//
//    // Activity Result Launcher for image capture and gallery selection
//    private val getContent =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            if (result.resultCode == Activity.RESULT_OK) {
//                if (result.data != null) {
//                    handleImage(result.data?.data)
//                } else {
//                    handleImage(Uri.parse(currentPhotoPath))
//                }
//            }
//        }
//
//    private fun openGallery() {
//        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        getContent.launch(intent)
//    }
//
//    private fun openCamera() {
//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        try {
//            val photoFile: File? = createImageFile()
//            photoFile?.also {
//                val photoURI: Uri = FileProvider.getUriForFile(
//                    requireContext(),
//                    "com.example.cocktailsdbapp.fileprovider",
//                    it
//                )
//                imageUri = photoURI
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
//                getContent.launch(intent)
//            }
//        } catch (ex: IOException) {
//            // Handle the error.
//        }
//    }
//
//    private fun createImageFile(): File? {
//        // Create an image file name
//        val timeStamp: String =
//            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
//        val storageDir: File? = (activity as MainActivity).getExternalFilesDir("images")
//        return File.createTempFile(
//            "JPEG_${timeStamp}_",
//            ".jpg",
//            storageDir
//        ).apply {
//            currentPhotoPath = absolutePath
//        }
//    }
//
//    // TODO
//    private fun handleImage(imageUri: Uri?) {
//
//        // Handle the selected image URI
//        // You can use the imageUri to load the image into an ImageView or perform other operations
//    }
//
//    // Callback for the result of requesting permissions
//    private val requestPermissionLauncher =
//        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
//            if (isGranted) {
//                openCamera()
//            } else {
//                // Permission denied, handle accordingly (e.g., show a message)
//            }
//        }
//
//    // Check if the camera permission is granted
//    fun checkCameraPermission(): Boolean {
//        return ContextCompat.checkSelfPermission(
//            this.requireContext(),
//            "android.permission.CAMERA"
//        ) == PackageManager.PERMISSION_GRANTED
//    }
//
//    fun requestCameraPermission() {
//        requestPermissionLauncher.launch("android.permission.CAMERA")
//    }
//
//    // TODO: If we want icons in dialog, it might better to go with DialogFragment approach
//    fun showOptionDialog() {
//        val options = arrayOf("Take a Photo", "Choose from Gallery", "Cancel")
//
//        val builder = AlertDialog.Builder(context)
//        builder.setTitle("Choose an Option")
//            .setItems(options) { dialog, which ->
//                when (which) {
//                    0 -> openCamera()
//                    1 -> openGallery()
//                    // Handle Cancel option if needed
//                }
//                dialog.dismiss()
//            }
//
//        builder.create().show()
//    }
//
//}
