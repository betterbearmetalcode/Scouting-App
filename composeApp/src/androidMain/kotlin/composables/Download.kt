package composables

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import java.io.ByteArrayOutputStream
import java.io.File

fun pitsDownload(
    context: Context,
    photoArray: ArrayList<ImageBitmap>,
    teamNumber: String,
    photoAmount: Int
) {
    val file = File(context.filesDir, "Primary$teamNumber.png")
    val stream = ByteArrayOutputStream()
    photoArray[0].asAndroidBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream)
    file.writeBytes(stream.toByteArray())
    file.createNewFile()
}