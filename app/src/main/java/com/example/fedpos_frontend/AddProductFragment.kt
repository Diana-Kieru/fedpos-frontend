package com.example.fedpos_frontend

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.fedpos_frontend.model.AddProductResponse
import com.example.fedpos_frontend.model.CategoryType
import com.example.fedpos_frontend.network.ApiService
import com.example.fedpos_frontend.network.NetworkClient
import com.google.android.material.textfield.TextInputEditText
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class AddProductFragment : Fragment() {
    private lateinit var name: TextInputEditText
    private lateinit var description: TextInputEditText
    private lateinit var targetAmount: TextInputEditText
    private lateinit var textInputLayoutUnit: TextInputEditText
    private lateinit var type: AutoCompleteTextView
    private lateinit var createButton: Button
    private lateinit var backArrow: ImageView
    private lateinit var imageView: ImageView
    private var partToUpload: MultipartBody.Part? = null

    val GALLERY_REQUEST_CODE = 100

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        name = view.findViewById(R.id.editTextFirstName)
        description = view.findViewById(R.id.textInputExperienceDescription)
        targetAmount = view.findViewById(R.id.editTextTargetAmount)
        type = view.findViewById(R.id.autoCompleteTextView)
        createButton = view.findViewById(R.id.buttonCreateEvent)
        textInputLayoutUnit = view.findViewById(R.id.textInputLayoutUnit)
        imageView = view.findViewById(R.id.imageView)
        backArrow = view.findViewById(R.id.backArrow)

        backArrow.setOnClickListener {
            NavHostFragment.findNavController(this)
                .popBackStack()
        }
        imageView.setOnClickListener {
            // Create an Intent to open the gallery
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)


            try {
                startActivityForResult(intent, GALLERY_REQUEST_CODE)
            } catch (e: Exception) {
                Toast.makeText(
                    requireContext(),
                    e.localizedMessage?.toString() ?: "Something went wrong!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


        type.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                CategoryType.values().map {
                    it.name.lowercase().replaceFirstChar { char ->
                        char.uppercase()
                    }
                }
            ).apply {
                setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
            }
        )

       createButton.setOnClickListener {
            if (partToUpload == null) {
                Toast.makeText(requireContext(), "Please select an image", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            if (name.text.toString().isBlank()) {
                name.error = " Name is required"
               name.requestFocus()
                return@setOnClickListener
            }
            if (description.text.toString().isBlank()) {
                description.error = " Description is required"
                description.requestFocus()
                return@setOnClickListener
            }
            if (targetAmount.text.toString().isBlank()) {
                targetAmount.error = "Target Amount is required"
                targetAmount.requestFocus()
                return@setOnClickListener
            }
            if (targetAmount.text.toString().toInt() < 1000) {
               targetAmount.error = " Target must be great than 1000"
                targetAmount.requestFocus()
                return@setOnClickListener
            }
            if (targetAmount.text.toString().toInt() > 1000000) {
                targetAmount.error = " Target must be less than 1000000"
               targetAmount.requestFocus()
                return@setOnClickListener
            }
            if (textInputLayoutUnit.text.toString().isBlank()) {
                textInputLayoutUnit.error = "Unit is required"
                textInputLayoutUnit.requestFocus()
                return@setOnClickListener
            }
            if (type.text.toString().isBlank()) {
                type.error = "Type is required"
                type.requestFocus()
                return@setOnClickListener
            }

//            make api call on products
            val apiService = NetworkClient.retrofitInstance?.create(ApiService::class.java)
            val call = apiService?.addProduct(
                name.text.toString(),
                description.text.toString(),
               targetAmount.text.toString().toInt(),
                textInputLayoutUnit.text.toString(),
                type.text.toString(),
                partToUpload!!
            )
            call?.enqueue(object : Callback<AddProductResponse> {
                override fun onResponse(
                    call: Call<AddProductResponse>,
                    response: Response<AddProductResponse>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            requireContext(),
                            " created successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        NavHostFragment.findNavController(this@AddProductFragment)
                            .popBackStack()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Failed to create ",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<AddProductResponse>, t: Throwable) {
                    Toast.makeText(
                        requireContext(),
                        "Failed to create",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            // Handle the selected image here
            val selectedImageUri = data.data

            if (selectedImageUri != null) {
                // get bitmap from the result URI
                val imageBitmap = MediaStore.Images.Media.getBitmap(
                    requireContext().contentResolver,
                    selectedImageUri
                )
                // display the image
                imageView.setImageURI(selectedImageUri)

                // create a temp file to upload
                val tempFile = createImageFile(requireContext())

                // compress the bitmap and save it to the temp file
                imageBitmap.compressAndSaveToFile(tempFile, 800, 600, 80)

                //create multipart from temp file
                partToUpload = prepareImageFilePart(tempFile)
            }
        }
    }

    fun Bitmap.compressAndSaveToFile(
        file: File,
        maxWidth: Int,
        maxHeight: Int,
        quality: Int = 80
    ): File {
        val scaledBitmap = scaleBitmap(this, maxWidth, maxHeight)
        val outputStream = FileOutputStream(file)
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        outputStream.close()
        return file
    }

    private fun scaleBitmap(bitmap: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
        val originalWidth = bitmap.width
        val originalHeight = bitmap.height
        var scale = 1.0f

        if (originalWidth > maxWidth || originalHeight > maxHeight) {
            val widthScale = maxWidth.toFloat() / originalWidth
            val heightScale = maxHeight.toFloat() / originalHeight
            scale = widthScale.coerceAtMost(heightScale)
        }

        val matrix = Matrix()
        matrix.postScale(scale, scale)

        return Bitmap.createBitmap(bitmap, 0, 0, originalWidth, originalHeight, matrix, true)
    }

    fun createImageFile(context: Context): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir,
        )
    }

    private fun prepareImageFilePart(file: File): MultipartBody.Part {
        val requestFile: RequestBody = file.asRequestBody("image/jpg".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("file", file.name, requestFile)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_product, container, false)
    }


}