package composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import com.lowagie.text.Document
import com.lowagie.text.Paragraph
import com.lowagie.text.pdf.PdfWriter
import org.jetbrains.skia.Image
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Profile(
    photoArray: ArrayList<ImageBitmap>,
    teamName: String,
    teamNumber: String,
    driveType: String,
    intakePref: String,
    robotWidth: String,
    robotLength: String,
    ampStrength: Boolean,
    speakerStrength: Boolean,
    climbStrength: Boolean,
    trapStrength: Boolean,
    concerns: String,
    scout: String
){
    val primaryPhoto = photoArray[0]
// 1) Create a FileOutputStream object with the path and name of the file
    val pdfOutputFile = FileOutputStream("./sample1.pdf")

//2) Instance an object from the class com.lowagie.text.Document
    val myPDFDoc = Document()

//3) Now we get a file writer instance from the class com.lowagie.text.pdf.PdfWriter
    val pdfWriter = PdfWriter.getInstance(myPDFDoc, pdfOutputFile)

//4) Once the File writer is set up we can open
//   the document and start adding content
    myPDFDoc.apply {
        open()

        add(Paragraph("I am $robotWidth by $robotLength, I like to intake using $intakePref. I enjoy long, luxurious walks on the beach with my intense $driveType drive. As you'll find out I am very efficient in multiple ways;\n" +
                " Amp: $ampStrength \n" +
                " Speaker: $speakerStrength \n" +
                " Climb: $climbStrength \n" +
                " Trap: $trapStrength \n" +
                " You should generally be concerned about my $concerns."))


    }
    pdfWriter.close() // close the File writer
}

