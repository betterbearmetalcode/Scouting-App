import org.json.JSONObject
import java.io.File
import java.io.FileInputStream
import java.io.FileWriter

actual fun createFile() {
    val homeDir = System.getProperty("user.home")
    val file = File("$homeDir/Documents/match_data.json")
    file.delete()
    file.createNewFile()
    val writer = FileWriter(file)

    matchData?.toString(1)?.let { writer.write(it) }
    writer.close()
}

actual fun openFile() {
    val homeDir = System.getProperty("user.home")
    matchData = JSONObject(String(FileInputStream(File("$homeDir/Documents/match_data.json")).readAllBytes()))
}