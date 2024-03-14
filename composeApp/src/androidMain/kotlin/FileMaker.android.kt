import android.content.Context
import android.content.Context.USB_SERVICE
import android.hardware.usb.UsbConstants.USB_DIR_OUT
import android.hardware.usb.UsbManager
import android.hardware.usb.UsbRequest
import android.os.Build
import android.os.Parcel
import android.util.Log
import androidx.annotation.RequiresApi
import nodes.matchScoutArray
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketException
import java.nio.ByteBuffer


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

    openScoutFile(context)
}

fun openScoutFile(context: Context) {

    var tempScoutData = JSONObject()
    try {
        tempScoutData =
            JSONObject(String(FileInputStream(File(context.filesDir, "match_scouting_data.json")).readBytes()))
    } catch (_: JSONException) {

    }

    repeat (6) {
        try {
            val array = tempScoutData[it.toString()] as JSONArray
            for (i in 0..<array.length()) {
                matchScoutArray.putIfAbsent(it, HashMap())
                matchScoutArray[it]?.set(i, array[i] as String)
            }
        } catch (_: JSONException) {}
    }

}


fun exportScoutData(context: Context) {

    val file = File(context.filesDir, "match_scouting_data.json")
    file.delete()
    file.createNewFile()
    val jsonObject = getJsonFromMatchHash()

    matchScoutArray.values
    val writer = FileWriter(file)
    writer.write(jsonObject.toString(1))
    writer.close()
}



fun sendData(context: Context, ipAddress: String) {

    exportScoutData(context)

    val jsonObject = getJsonFromMatchHash()
    val socket = Socket()
    try {
        socket.connect(InetSocketAddress(ipAddress, 45482), 5000)
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

@RequiresApi(Build.VERSION_CODES.O)
fun sendDataUSB(context: Context, deviceName: String) {
    exportScoutData(context)

    val jsonObject = getJsonFromMatchHash()
    val manager = context.getSystemService(USB_SERVICE) as UsbManager

    val deviceList = manager.deviceList

    val device = deviceList[deviceName]
    val connection = manager.openDevice(device)

    val endpoint = device?.getInterface(0)?.getEndpoint(5)
    if (endpoint?.direction == USB_DIR_OUT) {
        Log.i("USB", "Dir is out")
    } else {
        Log.i("USB", "Dir is in")
        return
    }


    val request = UsbRequest()
    request.initialize(connection, endpoint)

    val buffer = ByteBuffer.wrap(jsonObject.toString().encodeToByteArray())

    request.queue(buffer)
}