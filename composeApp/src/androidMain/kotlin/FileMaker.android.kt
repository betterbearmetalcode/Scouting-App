import android.content.Context
import android.content.Intent
import android.content.Intent.createChooser
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Picture
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat.startActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import nodes.matchScoutArray
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream
import java.io.FileWriter
import java.lang.reflect.Modifier
import java.util.jar.Manifest
import kotlin.coroutines.resume

fun createFile(context: Context) {
    val file = File(context.filesDir, "match_data.json")

    file.delete()
    file.createNewFile()
    val writer = FileWriter(file)

    matchData?.toString(1)?.let { writer.write(it) }
    writer.close()

    val teamFile = File(context.filesDir,"team_data.json")
    teamFile.delete()
    teamFile.createNewFile()
    val teamWriter = FileWriter(teamFile)

    teamData?.toString(1)?.let { teamWriter.write(it) }
    teamWriter.close()
}

fun openFile(context: Context) {
    matchData = JSONObject(String(FileInputStream(File(context.filesDir, "match_data.json")).readBytes()))

    teamData = JSONObject(String(FileInputStream(File(context.filesDir, "match_data.json")).readBytes()))
}

fun exportScoutData(context: Context) {
    val file = File(context.filesDir, "match_scouting_data.json")
    file.delete()
    file.createNewFile()
    val jsonArray = JSONArray()
    matchScoutArray.values.forEach {
        jsonArray.put(it)
    }
    val writer = FileWriter(file)
    writer.write(jsonArray.toString(1))
    writer.close()
}

fun generateBitmap(view: View): Bitmap {
    val bitmap = Bitmap.createBitmap(
        view.width,
        view.height,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    view.draw(canvas)
    return bitmap
}
