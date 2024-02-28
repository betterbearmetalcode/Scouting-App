import java.net.ServerSocket
import java.net.Socket

class Server(port: Int): Thread() {
    val socket = ServerSocket(port)
    lateinit var clientSocket: Socket

    override fun run() {
        clientSocket = socket.accept()
    }
}