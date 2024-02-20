import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat.startActivityForResult

const val CREATE_FILE = 1
const val PICK_PDF_FILE = 2


@RequiresApi(Build.VERSION_CODES.O)
actual fun createFile() {
    val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
        addCategory(Intent.CATEGORY_OPENABLE)
        type = "application/json"
        putExtra(Intent.EXTRA_TITLE, "match_data.json")

        // Optionally, specify a URI for the directory that should be opened in
        // the system file picker before your app creates the document.
        putExtra(DocumentsContract.EXTRA_INITIAL_URI, Environment.DIRECTORY_DOCUMENTS)
    }
    startActivityForResult(Activity(), intent, CREATE_FILE, null)
}

@RequiresApi(Build.VERSION_CODES.O)
actual fun openFile() {
    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
        addCategory(Intent.CATEGORY_OPENABLE)
        type = "application/pdf"

        // Optionally, specify a URI for the file that should appear in the
        // system file picker when it loads.
        putExtra(DocumentsContract.EXTRA_INITIAL_URI, Environment.DIRECTORY_DOCUMENTS)
    }
    startActivityForResult(Activity(), intent, PICK_PDF_FILE, null)
}

actual fun exportScoutData() {
}