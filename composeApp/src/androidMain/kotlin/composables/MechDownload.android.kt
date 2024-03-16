package composables

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.core.net.toFile
import java.io.ByteArrayOutputStream
import java.io.File

fun download(
    context: Context,
    photoArray: SnapshotStateList<Uri>,
    teamNumber: String,
    photoAmount: Int
) {
    val stream = ByteArrayOutputStream()
    var file = File(context.cacheDir, "photo_0.jpg")
    val directory = File(context.filesDir, "")
    directory.mkdirs()
    file.writeBytes(stream.toByteArray())
    file.createNewFile()
    if(photoAmount>=2){
        file = File(context.cacheDir, "photo_1.jpg")
        file.writeBytes(stream.toByteArray())
        file.createNewFile()
    }
    if(photoAmount>=3){
        file = File(context.cacheDir, "photo_2.jpg")
        //photoArray[2].asAndroidBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream)
        file.writeBytes(stream.toByteArray())
        file.createNewFile()
    }

}