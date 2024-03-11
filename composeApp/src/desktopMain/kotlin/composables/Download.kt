package composables

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toAwtImage
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.imageio.ImageIO
    fun pitsDownload(
    photoArray: ArrayList<ImageBitmap>,
    teamNumber: String,
) {
        val homeDir = System.getProperty("user.home")
        val filePath = "$homeDir/Pictures/Primary$teamNumber.png"
        try {
            val inputFile = File(filePath)
            inputFile.createNewFile()
            ImageIO.write(photoArray[0].toAwtImage(),"PNG", inputFile)
            val outputStream = FileOutputStream(inputFile)
            ImageIO.createImageOutputStream(outputStream)
            outputStream.flush()
            outputStream.close()
            println("Image saved successfully to $filePath")
        } catch (e: IOException) {
            e.printStackTrace()
            println("Error saving image: ${e.message}")
        }
    }