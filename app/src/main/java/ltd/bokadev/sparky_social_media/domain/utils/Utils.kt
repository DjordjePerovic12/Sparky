package ltd.bokadev.sparky_social_media.domain.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.provider.MediaStore
import ltd.bokadev.sparky_social_media.R
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


//Regarding earlier comments about my utils all being in core
//I made this one in domain being I consider this buissness logic
//Is this ok?


//Also, this is a function I use all the time for turning image uri to image
//but if it is not good, I'd appreciate feedback

fun getImage(image: Uri, context: Context, name: String = "profilePicture"): MultipartBody.Part? {
    var outputImage: MultipartBody.Part? = null
    val file = createFileFromUri(image, context)
    file?.asRequestBody("multipart/form-data".toMediaTypeOrNull())?.let { body ->
        outputImage = MultipartBody.Part.createFormData(
            name,
            file.name,
            body
        )
    }
    return outputImage
}

fun createFileFromUri(uri: Uri, context: Context): File? {
    val inputStream = context.contentResolver.openInputStream(uri) ?: return null
    val exif = ExifInterface(inputStream)

    // Get the orientation of the image
    val orientation =
        exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)
    val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri) ?: return null
    val bytes = ByteArrayOutputStream()

    // Rotate the image to portrait orientation
    val matrix = Matrix()
    when (orientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
        ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
        ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
    }
    val rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)

    rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)

    val filename = context.getString(
        R.string.file_name,
        java.util.UUID.randomUUID().toString()
    )

    val cacheDir = context.cacheDir
    val file = File(cacheDir, filename)
    file.createNewFile()
    val fileOutputStream = FileOutputStream(file)
    fileOutputStream.write(bytes.toByteArray())
    fileOutputStream.close()

    return file
}
