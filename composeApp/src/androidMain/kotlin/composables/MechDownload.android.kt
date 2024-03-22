package composables

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


@Composable
fun download(
    context: Context,
    photoArray: SnapshotStateList<Uri>,
    teamNumber: String,
    photoAmount: Int
) {
    //var file = File(context.cacheDir, "photo_0.jpg")
    val file = File(context.cacheDir,"primary$teamNumber")
    val directory = context.filesDir
    directory.mkdirs()
    file.delete()
    file.createNewFile()
    var bos = ByteArrayOutputStream();
    var bitmapdata = photoArray[0]
    //var byte = ByteArray(photoArray[0].size)
    // Assuming you're inside a Composable function
    val context = LocalContext.current
    val contentResolver = context.contentResolver
    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, photoArray[0])
    // Your image data
    bitmap.compress(Bitmap.CompressFormat.PNG,0,bos)
//    val fos: FileOutputStream = FileOutputStream(file)
//    fos.write(byteArray)
}