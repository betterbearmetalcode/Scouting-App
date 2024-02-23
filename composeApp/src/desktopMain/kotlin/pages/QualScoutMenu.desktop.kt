package pages

import BackButton
import LabeledSlider
import RootNode
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.json.JSONObject
import qrcode.QRCode
import qrcode.color.Colors
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.Writer
import kotlin.math.round

actual class QualScoutMenu actual constructor(
    buildContext: BuildContext,
    private val backStack: BackStack<RootNode.NavTarget>
) : Node(buildContext = buildContext) {

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    actual override fun View(modifier: Modifier) {
        var notes by remember { mutableStateOf("") }
        var collectionScore by remember { mutableFloatStateOf(5f) }
        var outtakeScore by remember { mutableFloatStateOf(5f) }
        var driverScore by remember { mutableFloatStateOf(5f) }
        var file by remember { mutableStateOf(File("src/commonMain/resources/Empty Qr Code.png")) }
        var imageBitmap by remember { mutableStateOf(loadImageBitmap(file.inputStream())) }



        Column(modifier = modifier.verticalScroll(ScrollState(0))) {
            BackButton(
                backStack = backStack,
                content = {
                    Image(
                        painter = painterResource(res = "back-arrow.png"),
                        contentDescription = "Back Arrow",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxSize(1f / 8f)

                    )
                }
            )


            Column {

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
                        text = "Collection Rating",
                        fontSize = 30.sp,
                        modifier = Modifier.padding(15.dp, 0.dp, 0.dp, 0.dp)
                    )
                },
                    sliderValue = collectionScore,
                    onValueChange = {
                        collectionScore = it
                        collectionScore = round(collectionScore * 10f) / 10f
                    }
                )

                Text(
                    text = collectionScore.toString(),
                    modifier = Modifier.padding(15.dp, 0.dp, 0.dp, 0.dp)
                )

                LabeledSlider(
                    label = {
                        Text(
                            text = "Outtake Rating",
                            fontSize = 30.sp,
                            modifier = Modifier.padding(15.dp, 0.dp, 0.dp, 0.dp)
                        )
                    },
                    sliderValue = outtakeScore,
                    onValueChange = {
                        outtakeScore = it
                        outtakeScore = round(outtakeScore * 10f) / 10f
                    }
                )

                Text(
                    text = outtakeScore.toString(),
                    modifier = Modifier.padding(15.dp, 0.dp, 0.dp, 0.dp)
                )

                LabeledSlider(
                    label = {
                        Text(
                            text = "Driver Precision",
                            fontSize = 30.sp,
                            modifier = Modifier.padding(15.dp, 0.dp, 0.dp, 0.dp)
                        )
                    },
                    sliderValue = driverScore,
                    onValueChange = {
                        driverScore = it
                        driverScore = round(driverScore * 10f) / 10f
                    }
                )

                Text(
                    text = driverScore.toString(),
                    modifier = Modifier.padding(15.dp, 0.dp, 0.dp, 0.dp)
                )



                Button(
                    onClick = {
                        val jsonObject = JSONObject()

                        jsonObject.put("Notes", notes)
                        jsonObject.put("Defence Score", collectionScore)
                        jsonObject.put("Offence Score", outtakeScore)

                        val helloWorld = QRCode.ofRoundedSquares()
                            .withSize(12)
                            .withBackgroundColor(Colors.BLACK)
                            .withColor(Colors.GOLD)
                            .build(jsonObject.toString(4))

                        val pngBytes = helloWorld.render()

                        val image = File("C:\\Users\\tahom\\IdeaProjects\\Scouting-App\\composeApp\\src\\desktopMain\\resources\\qrCodeDesktop.png")
                        image.delete()
                        image.createNewFile()

                        val fos = FileOutputStream(image)
                        fos.write(pngBytes.getBytes())
                        fos.close()

                        file = image
                        imageBitmap = loadImageBitmap(file.inputStream())
                    },
                    content = {
                        Text(
                            text = "Export Data (Qr Code)"
                        )
                    }
                )

                Image(
                    painter = BitmapPainter(imageBitmap),
                    contentDescription = "QR Code",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize(1.25f)
                )

                Spacer(modifier = Modifier.height(25.dp))

            }
        }

    }
}