package pages

import RootNode
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.json.JSONObject
import qrcode.QRCode
import java.io.File
import java.io.FileOutputStream
import kotlin.math.round

actual class QualScoutMenu actual constructor(
    buildContext: BuildContext,
    private val backStack: BackStack<RootNode.NavTarget>
) : Node(buildContext = buildContext) {

    @OptIn(ExperimentalResourceApi::class)
    @SuppressLint("NewApi")
    @Composable
    actual override fun View(modifier: Modifier) {
        var text by remember { mutableStateOf("") }
        var defenceScore by remember { mutableFloatStateOf(5f) }
        var offenceScore by remember { mutableFloatStateOf(5f) }

        val context = LocalContext.current
        var qrCodeFile by remember { mutableStateOf(File("")) }

        Column (modifier = modifier) {
            TextField(
                value = text,
                onValueChange = {
                    text = it
                },
                placeholder = {
                    Text("Notes")
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(0.dp, 10.dp, 0.dp, 60.dp)
            )

            Text(
                text = "Defence Rating",
                fontSize = 30.sp,
                modifier = Modifier.padding(15.dp, 0.dp, 0.dp, 0.dp)
            )

            Slider(
                value = defenceScore,
                valueRange = 0f..10f,
                onValueChange = {
                    defenceScore = it
                    defenceScore = round(defenceScore)
                },
                steps = 9
            )

            Text(
                text = defenceScore.toString(),
                modifier = Modifier.padding(15.dp, 0.dp, 0.dp, 0.dp)
            )


            Text(
                text = "Offence Rating",
                fontSize = 30.sp,
                modifier = Modifier.padding(15.dp, 0.dp, 0.dp, 0.dp)
            )

            Slider(
                value = offenceScore,
                valueRange = 0f..10f,
                onValueChange = {
                    offenceScore = it
                    offenceScore = round(offenceScore)
                },
                steps = 9
            )

            Text(
                text = offenceScore.toString(),
                modifier = Modifier.padding(15.dp, 0.dp, 0.dp, 0.dp)
            )



            Button(
                onClick = {

                    val jsonObject = JSONObject()

                    jsonObject.put("Defence Score", defenceScore)
                    jsonObject.put("Offence Score", offenceScore)

                    val helloWorld = QRCode.ofSquares()
                        .withSize(10) // Default is 25
                        .build(jsonObject.toString(4))

                    // By default, QRCodes are rendered as PNGs.
                    val pngBytes = helloWorld.render()

                    val file = File(context.filesDir, "qrcode.png")

                    file.delete()
                    file.createNewFile()

                    FileOutputStream(file).use { it.write(pngBytes.getBytes()) }

                    qrCodeFile = file

                },
                content = {
                    Text(
                        text = "Export Data (Qr Code)"
                    )
                }
            )

            Image(
                painter = rememberAsyncImagePainter(model = qrCodeFile),
                contentDescription = "Qr Code"
            )
        }



    }
}