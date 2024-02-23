import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import nodes.matchScoutArray
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream
import java.io.FileWriter

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
    val gson = Gson()
    val writer = FileWriter(file)
    writer.write(gson.toJson(matchScoutArray))
    writer.close()
}