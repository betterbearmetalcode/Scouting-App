import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import nodes.matchScoutArray
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream
import java.io.FileWriter
import java.io.PrintWriter
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.net.Socket


val serverSocket = Server(8880)

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

    try {
        val tempScoutData =
            JSONArray(String(FileInputStream(File(context.filesDir, "match_scouting_data.json")).readBytes()))

        for (i in 0..<tempScoutData.length()) {
            matchScoutArray[i] = tempScoutData[i] as String
        }
    } catch (_: JSONException) {

    }
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

fun sendData(context: Context) {
    serverSocket.start()

    val file = File(context.filesDir, "match_scouting_data.json")
    file.delete()
    file.createNewFile()
    val jsonArray = JSONArray()
    matchScoutArray.values.forEach {
        jsonArray.put(it)
    }
    val socket = Socket()
    socket.connect(InetSocketAddress(InetAddress.getLocalHost(), 8880), 500)
    val writer = PrintWriter(socket.getOutputStream())
    writer.write(jsonArray.toString())
}