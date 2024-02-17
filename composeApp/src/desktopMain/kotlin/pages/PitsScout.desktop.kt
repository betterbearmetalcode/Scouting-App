package pages

import RootNode
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import com.github.sarxos.webcam.Webcam
import composables.CheckBox
import defaultOnPrimary
import java.io.File
import javax.imageio.ImageIO


actual class PitsScoutMenu actual constructor(
    buildContext: BuildContext,
    private val backStack: BackStack<RootNode.NavTarget>,
    private var pitsPerson: MutableState<String>
) : Node(buildContext = buildContext) {
    @Composable
    actual override fun View(modifier: Modifier) {
        var notes by remember { mutableStateOf("") }
        var teamName by remember { mutableStateOf("") }
        var teamNumber by remember { mutableStateOf("") }
        var teamDropdown by remember { mutableStateOf(false) }
        var pitsScoutedTeam by remember { mutableStateOf("") }
        var robotLength by remember { mutableStateOf("") }
        var robotWidth by remember { mutableStateOf("") }
        var robotType by remember { mutableStateOf(" ") }
        var ampStrength = mutableStateOf(false)
        var speakerStrength = mutableStateOf(false)
        var trapStrength = mutableStateOf(false)
        var collectionPreference by remember { mutableStateOf("") }
        val webcam = Webcam.getDefault()
        var webcamWorky by remember { mutableStateOf("false") }
        val helloWorldPng = File("hello-world.png")
        val photoArray by remember { mutableStateOf(ArrayList<ImageBitmap>())}
        var photoAmount by remember { mutableStateOf(0) }
        var robotImageBytes by remember{ mutableStateOf(helloWorldPng.readBytes())}
        var bitmap by remember{ mutableStateOf(org.jetbrains.skia.Image.makeFromEncoded(robotImageBytes).toComposeImageBitmap()) }
        Column(modifier = modifier.verticalScroll(ScrollState(0))){
            Row(modifier = Modifier.offset(20.dp,15.dp)){
                Text(
                   text ="Pits",
                   fontSize = 50.sp,
                   color = defaultOnPrimary,
                )
                Text(
                    text = pitsPerson.value,
                    fontSize = 40.sp,
                    color = defaultOnPrimary
                )
          }
            Row{
                Text(
                    text="Team: ",
                    fontSize = 30.sp,
                )
                OutlinedTextField(
                    value = pitsScoutedTeam,
                    onValueChange ={ pitsScoutedTeam = it},
                    textStyle = TextStyle.Default.copy(fontSize = 20.sp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = Color.Black, focusedBorderColor = Color.Yellow, textColor = defaultOnPrimary),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier.size(85.dp,60.dp)
                )
            }
            Divider(color = Color.Yellow, thickness = 2.dp)
            Row{
                Text(
                    text="Dimensions"
                )
                OutlinedTextField(
                    value = robotLength,
                    onValueChange ={ robotLength = it},
                    colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = Color.Black, focusedBorderColor = Color.Yellow, textColor = defaultOnPrimary),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier.width(45.dp)
                )
                Text(
                    text=" by "
                )
                OutlinedTextField(
                    value = robotWidth,
                    onValueChange ={ robotWidth = it},
                    colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = Color.Black, focusedBorderColor = Color.Yellow, textColor = defaultOnPrimary),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier.width(45.dp)
                )
            }
            Row{
                Text(
                    text="type"
                )
                OutlinedTextField(
                    value = robotType,
                    onValueChange ={ robotType = it},
                    colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = Color.Black, focusedBorderColor = Color.Yellow, textColor = defaultOnPrimary),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier.width(80.dp)
                )
            }
            OutlinedButton(
                border = BorderStroke(2.dp, color = Color.Yellow),
                shape = RoundedCornerShape(10.dp),
                onClick = {
                    if (webcam != null) {
                        webcamWorky = "true"
                        webcam.open()
                        helloWorldPng.delete()
                        ImageIO.write(webcam.image, "PNG", helloWorldPng)
                        bitmap = webcam.image.toComposeImageBitmap()
                        webcam.close()
                        photoAmount++
                        photoArray.add(bitmap)

                    }
                }
            ) {
                Row{
                    Image(
                        painter = painterResource("KillCam.png"),
                        contentDescription = "Camera"
                    )
                    Text(
                        text ="Take Picture",
                        color= defaultOnPrimary
                    )
                    }
                }
            Row(modifier = Modifier.horizontalScroll(ScrollState(0))) {
                photoArray.forEach() {
                    Image(
                        painter = BitmapPainter(it),
                        contentDescription = "Robor image",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxSize(1.25f)
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                }
            }
            Box(modifier = Modifier.fillMaxWidth()){
            Text(text = "WebCam Works: $webcamWorky", color = Color.Gray, modifier = Modifier.align(Alignment.CenterStart))
            Text(text = "*Have to take pic to check", color = Color.Gray, modifier = Modifier.scale(.65f).align(Alignment.CenterStart).offset(215.dp,0.dp))
            Text(text = "Amount of Photos: $photoAmount",color = Color.Gray ,modifier = Modifier.align(Alignment.CenterEnd))
            }
            Text(
                text = "Strengths",
                fontSize = 30.sp
                )
            CheckBox(label ="Amp:", ifChecked = ampStrength, modifier = Modifier.align(Alignment.CenterHorizontally))
            CheckBox("Speaker:",speakerStrength, modifier = Modifier.align(Alignment.CenterHorizontally))
            CheckBox("Trap:",trapStrength, modifier = Modifier.align(Alignment.CenterHorizontally))

            Text(
                text ="Collection Preference",
                fontSize = 30.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            OutlinedTextField(
                    value = collectionPreference,
                    onValueChange ={ collectionPreference = it},
                    colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = Color.Black, focusedBorderColor = Color.Yellow, textColor = defaultOnPrimary),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally).height(90.dp)
                )
        }
    }
}