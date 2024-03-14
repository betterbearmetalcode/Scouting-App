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
//    var file = photoArray[0].toFile() //File(context.filesDir, "Primary$teamNumber.png")
    ///println(file.name)

//    if(photoAmount>=2){
//        file = File(context.filesDir, "Secondary$teamNumber.png")
//
//        file.writeBytes(stream.toByteArray())
//        file.createNewFile()
//    }
//    if(photoAmount>=3){
//        file = File(context.filesDir, "Tertiary$teamNumber.png")
//        //photoArray[2].asAndroidBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream)
//        file.writeBytes(stream.toByteArray())
//        file.createNewFile()
//    }

}