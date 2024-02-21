import com.google.gson.Gson
import nodes.matchScoutArray
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream
import java.io.FileWriter

fun createFile() {
    val homeDir = System.getProperty("user.home")
    val file = File("$homeDir/Documents/match_data.json")
    file.delete()
    file.createNewFile()
    val writer = FileWriter(file)

    matchData?.toString(1)?.let { writer.write(it) }
    writer.close()

    val teamFile = File("$homeDir/Documents/team_data.json")
    teamFile.delete()
    teamFile.createNewFile()
    val teamWriter = FileWriter(teamFile)

    teamData?.toString(1)?.let { teamWriter.write(it) }
    teamWriter.close()
}

actual fun openFile() {
    val homeDir = System.getProperty("user.home")
    matchData = JSONObject(String(FileInputStream(File("$homeDir/Documents/match_data.json")).readAllBytes()))

    teamData = JSONObject(String(FileInputStream(File("$homeDir/Documents/team_data.json")).readAllBytes()))

}

actual fun exportScoutData() {
    val homeDir = System.getProperty("user.home")
    val file = File("$homeDir/Documents/match_scouting_data.json")
    file.delete()
    file.createNewFile()
    val gson = Gson()
    val writer = FileWriter(file)
    writer.write(gson.toJson(matchScoutArray))
    writer.close()
}