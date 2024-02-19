package pages

import RootNode
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
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
        var pitsPersonDD by remember { mutableStateOf(false) }
        var numOfPitsPeople by remember { mutableStateOf(6) }
        var notes by remember { mutableStateOf("") }
        var teamName by remember { mutableStateOf("") }
        var teamNumber by remember { mutableStateOf("") }
        var teamDropdown by remember { mutableStateOf(false) }
        var pitsScoutedTeam by remember { mutableStateOf("") }
        var robotLength by remember { mutableStateOf("") }
        var robotWidth by remember { mutableStateOf("") }
        var robotTypeDropDown by remember { mutableStateOf(false) }
        var robotType by remember { mutableStateOf("NoneSelected") }
        var ampStrength = mutableStateOf(false)
        var speakerStrength = mutableStateOf(false)
        var climbStrength = mutableStateOf(false)
        var trapStrength = mutableStateOf(false)
        var collectionPreference by remember { mutableStateOf("") }
        var concerns by remember { mutableStateOf("") }
        val webcam = Webcam.getDefault()
        var webcamWorky by remember { mutableStateOf("false") }
        val helloWorldPng = File("hello-world.png")
        val photoArray by remember { mutableStateOf(ArrayList<ImageBitmap>())}
        var photoAmount by remember { mutableStateOf(0) }
        var robotImageBytes by remember{ mutableStateOf(helloWorldPng.readBytes())}
        var bitmap by remember{ mutableStateOf(org.jetbrains.skia.Image.makeFromEncoded(robotImageBytes).toComposeImageBitmap()) }
        val scrollState = rememberScrollState(0)
        var isScrollEnabled by remember{ mutableStateOf(true)}
        Column(modifier = Modifier.verticalScroll(state = scrollState, enabled = isScrollEnabled)) {
            Box( modifier = Modifier.offset(20.dp, 15.dp).fillMaxWidth()) {
                Text(
                    text = "Pits",
                    fontSize = 50.sp,
                    color = defaultOnPrimary,
                )
                TextButton(
                    onClick = { pitsPersonDD = true },
                    modifier = Modifier.align(Alignment.CenterEnd).padding(15.dp)
                ) {
                    Text(
                        text = pitsPerson.value,
                        fontSize = 40.sp,
                        color = defaultOnPrimary,
                    )
                }
                Box(modifier = Modifier.align(Alignment.CenterEnd).padding(15.dp).offset(0.dp,15.dp)) {
                    DropdownMenu(
                        expanded = pitsPersonDD,
                        onDismissRequest = { pitsPersonDD = false },
                        modifier = Modifier.background(color = Color(15, 15, 15)).clip(RoundedCornerShape(7.5.dp))

                    ) {
                        var i by mutableStateOf(0)
                        while (i < numOfPitsPeople) {
                            i++
                            DropdownMenuItem(
                                onClick = {
                                    pitsPersonDD = false
                                    pitsPerson.value = "P$i"
                                }
                            ) {
                                Text("P$i")
                            }

                        }
                    }
                }
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
            Spacer(modifier = Modifier.height(7.5.dp))
            Divider(color = Color.Yellow, thickness = 2.dp)
            Row {
                Text(
                    text="Dimensions"
                )
                OutlinedTextField(
                    value = robotLength,
                    onValueChange ={ robotLength = it +"mm"},
                    colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = Color.Black, focusedBorderColor = Color.Yellow, textColor = defaultOnPrimary),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier.size(80.dp,45.dp).border(BorderStroke(width = 1.dp, color = Color.Yellow),RoundedCornerShape(15.dp))
                )
                Text(
                    text=" by "
                )
                OutlinedTextField(
                    value = robotWidth,
                    onValueChange ={ robotWidth = it +"mm"},
                    colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = Color.Black, focusedBorderColor = Color.Yellow, textColor = defaultOnPrimary),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier.size(80.dp,45.dp).border(BorderStroke(width = 1.dp, color = Color.Yellow),RoundedCornerShape(15.dp))
                )
            }
            Row {
                Text(
                    text = "type"
                )
                OutlinedButton(
                    onClick = {
                        robotTypeDropDown = true
                    },
                    shape = CircleShape
                ) {
                    Text(
                        text = "Selected Robot Type: $robotType ",
                        color = defaultOnPrimary
                    )
                }
            }
            Box(modifier=Modifier.padding(15.dp,0.dp)/*.offset(0.dp,-25.dp)*/) {
                DropdownMenu(
                    expanded = robotTypeDropDown,
                    onDismissRequest = { robotTypeDropDown = false },
                    modifier = Modifier.background(color = Color(15, 15, 15)).clip(RoundedCornerShape(7.5.dp))
                ) {
                    DropdownMenuItem(
                        onClick = {
                            robotTypeDropDown = false
                            robotType = "Swerve"
                        }
                    ) {
                        Text("Swerve")
                    }
                    DropdownMenuItem(
                        onClick = {
                            robotTypeDropDown = false
                            robotType = "Tank"
                        }
                    ) {
                        Text("Tank")
                    }
                    DropdownMenuItem(
                        onClick = {
                            robotTypeDropDown = false
                            robotType = "Mecanum"
                        }
                    ) {
                        Text("Mecanum")
                    }
                }
            }
            OutlinedButton(
                border = BorderStroke(2.dp, color = Color.Yellow),
                shape = RoundedCornerShape(10.dp),
                onClick = {
                    if(photoAmount<3) {
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
                    }else{
                        println("Too many photos")
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
                text = "Strengths:",
                fontSize = 30.sp
                )

            CheckBox("Amp:", ampStrength, modifier = Modifier.scale(1.25f))
            CheckBox("Speaker:", speakerStrength, modifier = Modifier.scale(1.25f))
            CheckBox("Climb", climbStrength, modifier = Modifier.scale(1.25f))
            CheckBox("Trap:", trapStrength, modifier = Modifier.scale(1.25f))

            Text(
                text ="Collection Preference:",
                fontSize = 30.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            OutlinedTextField(
                    value = collectionPreference,
                    onValueChange ={ collectionPreference = it},
                    colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = Color.Black, focusedBorderColor = Color.Yellow, textColor = defaultOnPrimary),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier.fillMaxWidth(9f/10f).align(Alignment.CenterHorizontally).height(90.dp)
                )

            Text(
                text ="Concerns:",
                fontSize = 30.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            OutlinedTextField(
                value = concerns,
                onValueChange ={ concerns = it},
                colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = Color.Black, focusedBorderColor = Color.Yellow, textColor = defaultOnPrimary),
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier.fillMaxWidth(9f/10f).align(Alignment.CenterHorizontally).height(90.dp)
            )
        }
    }
}