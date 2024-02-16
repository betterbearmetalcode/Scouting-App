package pages

import RootNode
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import com.github.sarxos.webcam.Webcam
import java.io.File
import javax.imageio.ImageIO

actual class PitsScoutMenu actual constructor(
    buildContext: BuildContext,
    backStack: BackStack<RootNode.NavTarget>
) : Node(buildContext = buildContext) {
    @Composable
    actual override fun View(modifier: Modifier) {

        var notes by remember { mutableStateOf("") }
        var teamName by remember { mutableStateOf("") }
        var teamNumber by remember { mutableStateOf("") }
        val webcam = Webcam.getDefault()
        var webcamActive by remember { mutableStateOf("None") }
        var helloWorldPng = File("C:\\Users\\tahom\\IdeaProjects\\Scouting-App\\composeApp\\hello-world.png")
        Column {
            TextButton(
                onClick = {
                    if (webcam != null) {
                        webcamActive = "true"
                        webcam.open()
                        helloWorldPng.delete()
                        ImageIO.write(webcam.image, "PNG", helloWorldPng)
                        webcam.close()
                    }
                }
            ) {
                Text(text = "WebCam Active: $webcamActive", color = Color.White)
            }

            Image(
                painter = painterResource("C:\\Users\\tahom\\IdeaProjects\\Scouting-App\\composeApp\\hello-world.png"),
                    contentDescription = "Robor image",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize(1.25f)
            )
        }
    }
}