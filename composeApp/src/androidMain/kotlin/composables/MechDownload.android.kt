package composables

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.ImageDecoder.decodeBitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.decodeBitmap
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun download(
    context: Context,
    photoArray: SnapshotStateList<Uri>,
    teamNumber: String,
    photoAmount: Int
) {
    //var file = File(context.cacheDir, "photo_0.jpg")
    val directory = File(context.cacheDir,"MechScouting")

    if(!directory.exists()){
        directory.delete()
    }

    directory.mkdirs()
    directory.isDirectory
    directory.createNewFile()

    val file = File(directory,"primary$teamNumber")
    file.delete()
    file.createNewFile()

    var bos = ByteArrayOutputStream();
    var bitmapdata = photoArray[0]
    //var byte = ByteArray(photoArray[0].size)
    // Assuming you're inside a Composable function
    val context = LocalContext.current
    val contentResolver = context.contentResolver
    val bitmap = decodeBitmap(ImageDecoder.createSource(contentResolver, photoArray[0]))
    bitmap.compress(Bitmap.CompressFormat.PNG,0,bos)

}