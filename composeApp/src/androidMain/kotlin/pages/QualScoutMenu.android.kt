package pages

import BackButton
import LabeledSlider
import RootNode
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.json.JSONObject
import qrcode.QRCode
import qrcode.color.Colors
import java.io.File
import kotlin.math.round

actual class QualScoutMenu actual constructor(
    buildContext: BuildContext,
    private val backStack: BackStack<RootNode.NavTarget>
) : Node(buildContext = buildContext) {

    @OptIn(ExperimentalResourceApi::class)
    @SuppressLint("NewApi")
    @Composable
    actual override fun View(modifier: Modifier) {
        var notes by remember { mutableStateOf("") }
        var defenceScore by remember { mutableFloatStateOf(5f) }
        var offenceScore by remember { mutableFloatStateOf(5f) }

        val context = LocalContext.current
        var qrCodeFile by remember { mutableStateOf(ImageRequest.Builder(context).data(File("")).build()) }

        BackButton(
            backStack = backStack,
            content = {
                Image(
                    painter = painterResource(res = "back-arrow.png"),
                    contentDescription = "Back Arrow",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .offset(y = (-13).dp)
                        .fillMaxSize(1f / 8f)

                )
            }
        )

        Column (modifier = modifier) {
            TextField(
                value = notes,
                maxLines = 2,
                onValueChange = {
                    notes = it
                },
                placeholder = {
                    Text("Notes")
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(0.dp, 10.dp, 0.dp, 60.dp)
                    .fillMaxWidth(2 / 3f)
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

                    val jsonObject = JSONObject()

                    jsonObject.put("Notes", notes)
                    jsonObject.put("Defence Score", defenceScore)
                    jsonObject.put("Offence Score", offenceScore)

                    val helloWorld = QRCode.ofRoundedSquares()
                        .withSize(12)
                        .withBackgroundColor(Colors.BLACK)
                        .withColor(Colors.GOLD)
                        .build(jsonObject.toString(4))

                    val pngBytes = helloWorld.render()

                    qrCodeFile = ImageRequest.Builder(context)
                        .data(pngBytes.getBytes())
                        .build()


                },
                content = {
                    Text(
                        text = "Export Data (Qr Code)"
                    )
                }
            )

            AsyncImage(
                model = qrCodeFile,
                contentDescription = "Qr Code",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize(3f / 4f)
                    .offset(x = (-25).dp)
            )


        }

    }
}