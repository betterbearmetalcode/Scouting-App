package pages

import nodes.RootNode
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
import com.bumble.appyx.components.backstack.operation.push
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import com.github.sarxos.webcam.Webcam
import composables.CheckBox
import composables.Profile
import defaultError
import defaultOnPrimary
import defaultPrimaryVariant
import getCurrentTheme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import java.lang.Integer.parseInt

@OptIn(ExperimentalResourceApi::class)
actual class PitsScoutMenu actual constructor(
    buildContext: BuildContext,
    private val backStack: BackStack<RootNode.NavTarget>,
    private var pitsPerson: MutableState<String>,
    private val ampStrength: MutableState<Boolean>,
    private val speakerStrength: MutableState<Boolean>,
    private val climbStrength: MutableState<Boolean>,
    private val trapStrength: MutableState<Boolean>,
    private val scoutName: MutableState<String>
) : Node(buildContext = buildContext) {
    @Composable
    actual override fun View(modifier: Modifier) {
        val photoArray = mutableStateOf(ArrayList<ImageBitmap>())
        var pitsPersonDD by remember { mutableStateOf(false) }
        val numOfPitsPeople by remember { mutableStateOf(6) }
        var scoutedTeamName by remember { mutableStateOf("") }
        var scoutedTeamNumber by remember { mutableStateOf("") }
        var robotLength by remember{mutableStateOf("")}
        var robotWidth by remember{ mutableStateOf("")}
        var robotTypeDropDown by remember { mutableStateOf(false) }
        var robotType by remember { mutableStateOf("NoneSelected") }
        var collectPrefDD by remember{ mutableStateOf(false)}
        var collectPreference by remember { mutableStateOf("None Selected") }
        var concerns by remember { mutableStateOf("") }
        val webcam = Webcam.getDefault()
        var photoAmount by remember { mutableStateOf(0) }
        val scrollState = rememberScrollState(0)
        val isScrollEnabled by remember{ mutableStateOf(true)}
        var robotCard by remember {mutableStateOf(false)}
        var photoAlert by remember { mutableStateOf(false) }
        val perimeterChecked by remember { mutableStateOf(if (2 * (parseInt(robotLength) + parseInt(robotWidth)) > 120 && robotLength!="" && robotWidth!="") "crossmark.png" else "checkmark.png") }

        Column(modifier = Modifier.verticalScroll(state = scrollState, enabled = isScrollEnabled).padding(5.dp)) {
            Box( modifier = Modifier.offset(20.dp, 15.dp).fillMaxWidth()) {
                Text(
                    text = "Pits",
                    fontSize = 50.sp,
                    color = getCurrentTheme().onPrimary,
                )
                TextButton(
                    onClick = { pitsPersonDD = true },
                    modifier = Modifier.align(Alignment.CenterEnd).padding(15.dp)
                ) {
                    Text(
                        text = pitsPerson.value,
                        fontSize = 40.sp,
                        color = getCurrentTheme().onPrimary,
                    )
                }
                Box(modifier = Modifier.align(Alignment.CenterEnd).padding(15.dp).offset(0.dp,15.dp)) {
                    DropdownMenu(
                        expanded = pitsPersonDD,
                        onDismissRequest = { pitsPersonDD = false },
                        modifier = Modifier.background(color = getCurrentTheme().onSurface).clip(RoundedCornerShape(7.5.dp))

                    ) {
                        for(x in 1..numOfPitsPeople){
                            DropdownMenuItem(
                                onClick = {
                                    pitsPersonDD = false
                                    pitsPerson.value = "P$x"
                                }
                            ) {
                                Text("P$x", color = getCurrentTheme().onPrimary)
                            }
                        }
                    }
                }
            }
            Row(modifier = Modifier.scale(0.75f)){
                Text(
                    text="Team Name: ",
                    fontSize = 20.sp,
                    color = getCurrentTheme().onPrimary
                )
                OutlinedTextField(
                    value = scoutedTeamName,
                    onValueChange ={ scoutedTeamName = it},
                    textStyle = TextStyle.Default.copy(fontSize = 20.sp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = getCurrentTheme().onError, focusedBorderColor = getCurrentTheme().secondary, textColor = getCurrentTheme().onPrimary),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier.size(85.dp,60.dp)
                )
                Text(
                    text="Team Number: ",
                    fontSize = 20.sp,
                )
                OutlinedTextField(
                    value = scoutedTeamNumber,
                    onValueChange ={ scoutedTeamNumber = it},
                    textStyle = TextStyle.Default.copy(fontSize = 20.sp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = getCurrentTheme().onError, focusedBorderColor = getCurrentTheme().secondary, textColor = getCurrentTheme().onPrimary),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier.size(85.dp,60.dp)
                )
            }
            Spacer(modifier = Modifier.height(7.5.dp))
            Divider(color = getCurrentTheme().onSecondary, thickness = 2.dp, modifier = Modifier.clip(CircleShape))
            Spacer(modifier = Modifier.height(7.5.dp))
            Row {
                Text(
                    text = "Dimensions"
                )
                OutlinedTextField(
                    value = robotLength,
                    onValueChange = { text -> robotLength = text.filter { it.isDigit() }; val oldText = robotLength; if (robotLength.length > 8) robotLength = oldText},
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color(6, 9, 13),
                        focusedBorderColor = Color.Yellow,
                        textColor = defaultOnPrimary
                    ),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier.size(80.dp, 60.dp)
                        .border(BorderStroke(width = 1.dp, color = Color.Yellow), RoundedCornerShape(15.dp))
                )
                Text(
                    text = "inches by ",
                    color = Color.Gray
                )
                OutlinedTextField(
                    value = robotWidth,
                    onValueChange = { text -> robotWidth = text.filter { it.isDigit() }; val oldText = robotWidth; if (robotWidth.length > 8) robotWidth = oldText},
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color(6, 9, 13),
                        focusedBorderColor = Color.Yellow,
                        textColor = defaultOnPrimary
                    ),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier.size(80.dp, 60.dp)
                        .border(BorderStroke(width = 1.dp, color = Color.Yellow), RoundedCornerShape(15.dp))
                )
                Text(
                    text = "inches",
                    color = Color.Gray
                )
                }
            Image(
                org.jetbrains.compose.resources.painterResource(res = perimeterChecked),
                contentDescription = "dimensions checked",
                modifier = Modifier
                    .size(30.dp)
                    .offset(x = (98.5).dp),
            )

            Row {
                Text(
                    text = "Type"
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
                            webcam.open()
                            photoArray.value.add(webcam.image.toComposeImageBitmap())
                            webcam.close()
                            photoAmount++

                        }
                    }else{
                        photoAlert = true
                    }
                }
            ) {
                Image(
                    painter = painterResource("KillCam.png"),
                    contentDescription = "Camera"
                )
                Column {
                    Text(
                        text ="Take Picture",
                        color= defaultOnPrimary
                    )
                    Text(
                        text ="*Ask Permission First",
                        color= Color.Gray,
                        fontSize = 10.sp
                    )
                }
            }
            if(photoAlert) {
                AlertDialog(title = {Text(text ="TOO MANY PHOTOS!!!")}, onDismissRequest = {photoAlert = false}, buttons = { Box(modifier = Modifier.fillMaxWidth()){Button(onClick = {photoAlert = false},modifier = Modifier.align(Alignment.Center)){Text(text="Dismiss",color = defaultError)}}}, modifier = Modifier.clip(
                    RoundedCornerShape(5.dp)).border(BorderStroke(3.dp,defaultPrimaryVariant),RoundedCornerShape(5.dp)))
            }
            Row(modifier = Modifier.horizontalScroll(ScrollState(0))) {
                    photoArray.value.forEach {
                        Image(
                            painter = BitmapPainter(it),
                            contentDescription = "Robot image",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .clip(RoundedCornerShape(7.5.dp))
                                .fillMaxSize()
                        )
                        TextButton(
                            onClick = {
                                photoArray.value.remove(it)
                                photoAmount--
                            },
                        ) {
                            Image(
                                painter = painterResource("trash.png"),
                                contentDescription = "Delete",
                            )
                        }
                    }
            }
            if (photoAmount >= 1){
                Box(modifier = Modifier.fillMaxWidth()){
                    Text(text = "Amount of Photos: $photoAmount",color = Color.Gray ,modifier = Modifier.align(Alignment.CenterEnd))
                }
            }
            Text(
                text = "Strengths:",
                fontSize = 30.sp
            )
            Divider(color = getCurrentTheme().primaryVariant, thickness = 2.dp, modifier = Modifier.clip(CircleShape))

            CheckBox("Amp:", ampStrength, modifier = Modifier.scale(1.25f))
            CheckBox("Speaker:", speakerStrength, modifier = Modifier.scale(1.25f))
            CheckBox("Climb", climbStrength, modifier = Modifier.scale(1.25f))
            CheckBox("Trap:", trapStrength, modifier = Modifier.scale(1.25f))


            OutlinedButton(
                onClick = {
                    collectPrefDD = true
                },
                border = BorderStroke(2.dp, color = Color.Yellow),
                shape = CircleShape
            ) {
                Text(
                    text ="Collection Preference: $collectPreference",
                    fontSize = 15.sp,
                    color = defaultOnPrimary
                )
            }
            Box(modifier=Modifier.padding(15.dp,0.dp)) {
                DropdownMenu(
                    expanded = collectPrefDD,
                    onDismissRequest = { collectPrefDD = false },
                    modifier = Modifier.background(color = Color(15, 15, 15)).clip(RoundedCornerShape(7.5.dp))
                ) {
                    DropdownMenuItem(
                        onClick = {
                            collectPrefDD = false
                            collectPreference = "OverTheBumper"
                        }
                    ) {
                        Text("Over The Bumper")
                    }
                    DropdownMenuItem(
                        onClick = {
                            collectPrefDD = false
                            collectPreference = "UnderTheBumper"
                        }
                    ) {
                        Text("Under The Bumper")
                    }
                    DropdownMenuItem(
                        onClick = {
                            collectPrefDD = false
                            collectPreference = "from the Feeder Station"
                        }
                    ) {
                        Text("Feeder Station")
                    }
                }
            }

            Text(
                text ="Concerns:",
                fontSize = 30.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            OutlinedTextField(
                value = concerns,
                onValueChange ={ concerns = it},
                colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = Color(6,9,13), focusedBorderColor = Color.Yellow, textColor = defaultOnPrimary),
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier.fillMaxWidth(9f/10f).align(Alignment.CenterHorizontally).height(90.dp)
            )
            Row{
                OutlinedButton(onClick = { if (photoArray.value.size >= 1) { robotCard = true }}) { Text(text = "Submit", color = defaultOnPrimary) }
                OutlinedButton(onClick = { robotCard = false }) { Text(text = "Close", color = defaultOnPrimary) }
                OutlinedButton(onClick = {}) { Text(text = "Download", color = defaultOnPrimary) }
                OutlinedButton(onClick = { backStack.push(RootNode.NavTarget.MainMenu) }) { Text(text = "Back", color = defaultOnPrimary)
                }
            }
            if(robotCard){
                Box(modifier = Modifier.padding(5.dp)) {
                    Profile(
                        photoArray,  scoutedTeamName, scoutedTeamNumber, robotType, collectPreference,
                        robotLength, robotWidth, ampStrength.value, speakerStrength.value,
                        climbStrength.value, trapStrength.value, concerns,scoutName.value)
                }
            }
        }
    }
}
