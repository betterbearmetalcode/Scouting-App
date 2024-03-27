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
import androidx.core.net.toUri
import com.bumble.appyx.interactions.core.asElement
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
    var file = File(context.cacheDir,"primary$teamNumber")
    val directory = context.filesDir
    directory.isDirectory
    directory.mkdirs()
    file.delete()
    file.createNewFile()
    val os = FileOutputStream(file);
    val contentResolver =  LocalContext.current.contentResolver
    val bitmap = decodeBitmap(ImageDecoder.createSource(contentResolver, photoArray[0]))
    bitmap.compress(Bitmap.CompressFormat.PNG,0,os)
    os.flush()
}