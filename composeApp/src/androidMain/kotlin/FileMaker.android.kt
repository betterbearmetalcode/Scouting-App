import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import nodes.matchScoutArray
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketException


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
            JSONObject(String(FileInputStream(File(context.filesDir, "match_scouting_data.json")).readBytes()))

        repeat (6) {
            val array = tempScoutData[it.toString()] as JSONArray
            for (i in 0..<array.length()) {
                matchScoutArray[it]?.set(i, array[i] as String)
            }
        }
    } catch (_: JSONException) {

    }
}


fun exportScoutData(context: Context) {
    val file = File(context.filesDir, "match_scouting_data.json")
    file.delete()
    file.createNewFile()
    val jsonObject = getJsonFromMatchHash()

    val jsonArray = JSONArray()
    matchScoutArray.values
    val writer = FileWriter(file)
    writer.write(jsonArray.toString(1))
    writer.close()
}



fun sendData(context: Context, ipAddress: String) {
    val file = File(context.filesDir, "match_scouting_data.json")
    file.delete()
    file.createNewFile()
    val jsonObject = getJsonFromMatchHash()
    val socket = Socket()
    try {
        socket.connect(InetSocketAddress(ipAddress, 45482), 500)
        socket.getOutputStream().writer().use { writer ->
            writer.write(jsonObject.toString(1) + "\n")
            writer.flush() // Ensure data is sent immediately
        }

        Log.i("Client", "Message Sent: ${jsonObject.toString(1)}")
    } catch (e: IOException) {
        e.printStackTrace()
    } catch (_: SocketException) {

    } finally {
        socket.close()
    }


}