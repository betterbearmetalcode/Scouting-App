package composables

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toAwtImage
import java.io.File
import java.io.FileWriter
import java.io.IOException
import javax.imageio.ImageIO

fun download(
    photoArray: ArrayList<ImageBitmap>,
    teamNumber: String,
    photoAmount: Int
) {
    val homeDir = System.getProperty("user.home")
    var filePath1 = "C:\\MechScoutingData\\Primary$teamNumber.png"
    try {
        val inputFile = File(filePath1)

        ImageIO.write(photoArray[0].toAwtImage(),"PNG", inputFile)

        println("Image saved successfully to $filePath1")
    } catch (e: IOException) {
        e.printStackTrace()
        println("Error saving image: ${e.message}")
    }
    if (photoAmount>=2){
        val filePath2 = "C:\\MechScoutingData\\Primary$teamNumber.png"
        try {
            val inputFile = File(filePath2)
            ImageIO.write(photoArray[1].toAwtImage(),"PNG", inputFile)

            println("Image saved successfully to $filePath2")
        } catch (e: IOException) {
            println("Error saving image: ${e.message}")
        }
    }
    if (photoAmount == 3){
        val filePath3 = "C:\\MechScoutingData\\Primary$teamNumber.png"
        try {
            val inputFile = File(filePath3)
            ImageIO.write(photoArray[1].toAwtImage(),"PNG", inputFile)

            println("Image saved successfully to $filePath3")
        } catch (e: IOException) {
            println("Error saving image: ${e.message}")
        }
    }
        filePath1 = "$homeDir/Pictures/Data$teamNumber.txt"
        try {
            val inputFile = File(filePath1)
            val fileWriter = FileWriter(inputFile)
            fileWriter.write("$teamNumber \n")

            println("Word saved successfully to $filePath1")
        } catch (e: IOException) {
            println("Error saving text: ${e.message}")
        }
}