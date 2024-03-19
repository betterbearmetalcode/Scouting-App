package composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toAwtImage
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.imageio.ImageIO

fun download(
    photoArray: ArrayList<ImageBitmap>,
    teamNumber: String,
    photoAmount: Int
) {
    val homeDir = System.getProperty("user.home")
    var filePath = "C:\\MechScoutingData\\Primary$teamNumber"
    try {
        val inputFile = File(filePath)
        inputFile.createNewFile()
        var write = ImageIO.write(photoArray[0].toAwtImage(),"PNG", inputFile)
        val outputStream = FileOutputStream(inputFile)
        //ImageIO.createImageOutputStream(outputStream)
        outputStream.flush()
        outputStream.close()
        println("Image saved successfully to $filePath")
        println("Written to Image $write")
    } catch (e: IOException) {
        e.printStackTrace()
        println("Error saving image: ${e.message}")
    }
//    if (photoAmount>=2){
//        filePath = "$homeDir/Pictures/Primary$teamNumber.png"
//        try {
//            val inputFile = File(filePath)
//            inputFile.createNewFile()
//            ImageIO.write(photoArray[1].toAwtImage(),"PNG", inputFile)
//            val outputStream = FileOutputStream(inputFile)
//            ImageIO.createImageOutputStream(outputStream)
//            outputStream.flush()
//            outputStream.close()
//            println("Image saved successfully to $filePath")
//        } catch (e: IOException) {
//            e.printStackTrace()
//            println("Error saving image: ${e.message}")
//        }
//    }
}