package pages

import BackButton
import LabeledSlider
import RootNode
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
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
        var qrCodeFile by remember { mutableStateOf(ImageRequest.Builder(context).build()) }

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

            LabeledSlider(label = {
                Text(
                    text = "Defence Rating",
                    fontSize = 30.sp,
                    modifier = Modifier.padding(15.dp, 0.dp, 0.dp, 0.dp)
                )
              },
                sliderValue = defenceScore,
                onValueChange = {
                    defenceScore = it
                    defenceScore = round(defenceScore)
                }
            )

            Text(
                text = defenceScore.toString(),
                modifier = Modifier.padding(15.dp, 0.dp, 0.dp, 0.dp)
            )

            LabeledSlider(
                label = {
                    Text(
                        text = "Offence Rating",
                        fontSize = 30.sp,
                        modifier = Modifier.padding(15.dp, 0.dp, 0.dp, 0.dp)
                    )
                },
                sliderValue = offenceScore,
                onValueChange = {
                    offenceScore = it
                    offenceScore = round(offenceScore)
                }
            )

            Text(
                text = offenceScore.toString(),
                modifier = Modifier.padding(15.dp, 0.dp, 0.dp, 0.dp)
            )



            Button(
                onClick = {

                    val obj = qrCodeFile

                    val jsonObject = JSONObject()

                    jsonObject.put("Defence Score", defenceScore)
                    jsonObject.put("Offence Score", offenceScore)

                    val helloWorld = QRCode.ofSquares()
                        .withSize(10) // Default is 25
                        .build(jsonObject.toString(4))

                    // By default, QRCodes are rendered as PNGs.
                    val pngBytes = helloWorld.render()

                    val file = File(context.filesDir, "qrcode.png")

                    while (!file.delete())

                    file.createNewFile()

                    FileOutputStream(file).use { it.write(pngBytes.getBytes()) }



                    qrCodeFile = ImageRequest.Builder(context)
                            .data(file)
                            .build()

                    if (obj.data == qrCodeFile.data)
                        Log.d("Button", "File has not been changed")
                },
                content = {
                    Text(
                        text = "Export Data (Qr Code)"
                    )
                }
            )

            BackButton(
                backStack = backStack,
                content = {
                    Image(
                        painter = painterResource(res = "back-arrow.png"),
                        contentDescription = "Back Arrow",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxSize(
                            fraction = 1f/10f
                        )
                    )
                }
            )

            AsyncImage(
                model = qrCodeFile,
                contentDescription = "Qr Code",
                onState = {
                    Log.d("Qr Code", "Qr Code has changed")
                }
            )

        }
    }
}