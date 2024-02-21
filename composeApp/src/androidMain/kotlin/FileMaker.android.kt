import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import java.io.File
import java.io.FileWriter

const val CREATE_FILE = 1
const val PICK_PDF_FILE = 2


@RequiresApi(Build.VERSION_CODES.O)
fun createFile(context: Context) {
//    val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
//        addCategory(Intent.CATEGORY_OPENABLE)
//        type = "application/json"
//        putExtra(Intent.EXTRA_TITLE, "match_data.json")
//
//        // Optionally, specify a URI for the directory that should be opened in
//        // the system file picker before your app creates the document.
//        putExtra(DocumentsContract.EXTRA_INITIAL_URI, Environment.DIRECTORY_DOCUMENTS)
//    }
    val documentsDir = context.getExternalFilesDir(null)
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
    teamWriter.close()}

@RequiresApi(Build.VERSION_CODES.O)
actual fun openFile() {
//    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
//        addCategory(Intent.CATEGORY_OPENABLE)
//        type = "application/pdf"
//
//        // Optionally, specify a URI for the file that should appear in the
//        // system file picker when it loads.
//        putExtra(DocumentsContract.EXTRA_INITIAL_URI, Environment.DIRECTORY_DOCUMENTS)
//    }
//    startActivityForResult(Activity(), intent, PICK_PDF_FILE, null)
}

actual fun exportScoutData() {
}