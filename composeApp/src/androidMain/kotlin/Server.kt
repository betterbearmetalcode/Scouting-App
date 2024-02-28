import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketException

class Server(private val port: Int): Thread() {
    val serverSocket = ServerSocket(port)
    override fun run() {
        Log.i("Server", "Server started and listening on port $port")
        while (true) {
            try {
                val clientSocket = serverSocket.accept() // Accept incoming connection
                handleClient(clientSocket)
            } catch (_: SocketException) {

            }
        }
    }

    private fun handleClient(socket: Socket) {
        try {
            val outputStream = socket.getOutputStream()
            val inputStream = socket.getInputStream()
            val writer = PrintWriter(outputStream, true)

            // Send a message to the client
            writer.println("Hello from the server!")

            val reader = BufferedReader(InputStreamReader(inputStream))
            var line: String? = reader.readLine()
            while (line != null) {
                Log.i("Server", "Message received: $line")
                line = reader.readLine()
            }

            // Close the socket
            socket.close()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (_: SocketException) {

        }
    }
}