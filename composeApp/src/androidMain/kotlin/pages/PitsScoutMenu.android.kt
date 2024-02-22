package pages

import RootNode
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.operation.push
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import com.github.sarxos.webcam.Webcam
import composables.CheckBox
import composables.Profile
import defaultOnPrimary
import defaultPrimaryVariant
import java.io.File

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
        val photoArray by remember { mutableStateOf(ArrayList<ImageRequest>()) }
        var pitsPersonDD by remember { mutableStateOf(false) }
        val numOfPitsPeople by remember { mutableIntStateOf(6) }
        var scoutedTeamName by remember { mutableStateOf("") }
        var scoutedTeamNumber by remember { mutableStateOf("") }
        var robotLength by remember{mutableStateOf("")}
        var robotWidth by remember{ mutableStateOf("")}
        var robotTypeDropDown by remember { mutableStateOf(false) }
        var robotType by remember { mutableStateOf("NoneSelected") }
        var collectPrefDD by remember{ mutableStateOf(false)}
        var collectPreference by remember { mutableStateOf("None Selected") }
        var concerns by remember { mutableStateOf("") }
        val currentI by remember{ mutableIntStateOf(0) }
        val webcam = Webcam.getDefault()
        var photoAmount by remember { mutableIntStateOf(0) }
        val scrollState = rememberScrollState(0)
        val isScrollEnabled by remember{ mutableStateOf(true)}
        var robotCard by remember {mutableStateOf(false)}
        val context = LocalContext.current
        val trashCan = ImageRequest.Builder(context).data(File("trash.png")).build()
        val cam = ImageRequest.Builder(context).data(File("KillCam.png")).build()
        Column(modifier = Modifier
            .verticalScroll(state = scrollState, enabled = isScrollEnabled)
            .padding(5.dp)) {
            Box( modifier = Modifier
                .offset(20.dp, 15.dp)
                .fillMaxWidth()) {
                Text(
                    text = "Pits",
                    fontSize = 50.sp,
                    color = defaultOnPrimary,
                )
                TextButton(
                    onClick = { pitsPersonDD = true },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(15.dp)
                ) {
                    Text(
                        text = pitsPerson.value,
                        fontSize = 40.sp,
                        color = defaultOnPrimary,
                    )
                }
                Box(modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(15.dp)
                    .offset(0.dp, 15.dp)) {
                    DropdownMenu(
                        expanded = pitsPersonDD,
                        onDismissRequest = { pitsPersonDD = false },
                        modifier = Modifier
                            .background(color = Color(15, 15, 15))
                            .clip(RoundedCornerShape(7.5.dp))

                    ) {
                        for(x in 1..numOfPitsPeople){
                            DropdownMenuItem(
                                onClick = {
                                    pitsPersonDD = false
                                    pitsPerson.value = "P$x"
                                },
                                text ={
                                    Text("P$x")
                                }
                            )
                        }
                    }
                }
            }
            Row{
                Text(
                    text="Team Name: ",
                    fontSize = 20.sp,
                )
                OutlinedTextField(
                    value = scoutedTeamName,
                    onValueChange ={ scoutedTeamName = it},
                    textStyle = TextStyle.Default.copy(fontSize = 20.sp),
                    colors = TextFieldDefaults.colors(focusedContainerColor = Color(6,9,13),focusedTextColor = defaultOnPrimary),
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
                    colors = TextFieldDefaults.colors(focusedContainerColor = Color(6,9,13),focusedTextColor = defaultOnPrimary),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier.size(85.dp,60.dp)
                )
            }
            Spacer(modifier = Modifier.height(7.5.dp))
            HorizontalDivider(color = Color.Yellow, thickness = 2.dp, modifier = Modifier.clip(CircleShape))
            Spacer(modifier = Modifier.height(7.5.dp))
            Row {
                Text(
                    text="Dimensions"
                )
                OutlinedTextField(
                    value = robotLength + "in",
                    onValueChange ={ text -> robotLength = text.filter { it.isDigit()}},
                    colors = TextFieldDefaults.colors(focusedContainerColor = Color(6,9,13),focusedTextColor = defaultOnPrimary),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier
                        .size(80.dp, 60.dp)
                        .border(BorderStroke(width = 1.dp, color = Color.Yellow), RoundedCornerShape(15.dp))
                )
                Text(
                    text=" by "
                )
                OutlinedTextField(
                    value = robotWidth + "in",
                    onValueChange ={ text -> robotWidth = text.filter { it.isDigit()}},
                    colors = TextFieldDefaults.colors(focusedContainerColor = Color(6,9,13),focusedTextColor = defaultOnPrimary),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier
                        .size(80.dp, 60.dp)
                        .border(BorderStroke(width = 1.dp, color = Color.Yellow), RoundedCornerShape(15.dp))
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
                    modifier = Modifier
                        .background(color = Color(15, 15, 15))
                        .clip(RoundedCornerShape(7.5.dp))
                ) {
                    DropdownMenuItem(
                        onClick = {
                            robotTypeDropDown = false
                            robotType = "Swerve"
                        },
                        text = { Text("Swerve") }
                    )
                    DropdownMenuItem(
                        onClick = {
                            robotTypeDropDown = false
                            robotType = "Tank"
                        },
                        text = { Text("Tank") }
                    )
                    DropdownMenuItem(
                        onClick = {
                            robotTypeDropDown = false
                            robotType = "Mecanum"
                        },
                        text = { Text("Mecanum") }
                    )
                }
            }
            OutlinedButton(
                border = BorderStroke(2.dp, color = Color.Yellow),
                shape = RoundedCornerShape(10.dp),
                onClick = {
                    if(photoAmount<3) {
                        if (webcam != null) {
                            webcam.open()
                            photoArray.add(ImageRequest.Builder(context).data(webcam.image).build())
                            webcam.close()
                            photoAmount++

                        }
                    }else{
                        println("Too many photos")
                    }
                }
            ) {
                AsyncImage(
                    model = cam,
                    contentDescription = "Camera"
                )
                Box{
                    Text(
                        text ="Take Picture",
                        color= defaultOnPrimary
                    )
                    Text(
                        text ="*Ask Permission First",
                        color= Color.Gray,
                        fontSize = 10.sp,
                        modifier = Modifier.offset(0.dp,17.dp)
                    )
                }
            }
            Row(modifier = Modifier.horizontalScroll(ScrollState(0))) {
                photoArray.forEach {
                    Box {
                        AsyncImage(
                            model = it,
                            contentDescription = "Robor image",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(7.5.dp))
                        )
                        TextButton(
                            onClick = {
                                photoArray.remove(it)
                                photoAmount--
                            },
                            modifier = Modifier
                                .scale(0.25f)
                                .align(Alignment.BottomStart)
                        ) {
                            AsyncImage(
                                model = trashCan,
                                contentDescription = "Delete",
                            )
                        }
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
            HorizontalDivider(color = defaultPrimaryVariant, thickness = 2.dp, modifier = Modifier.clip(CircleShape))

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
            ){
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
                    modifier = Modifier
                        .background(color = Color(15, 15, 15))
                        .clip(RoundedCornerShape(7.5.dp))
                ) {
                    DropdownMenuItem(
                        onClick = {
                            collectPrefDD = false
                            collectPreference = "OverTheBumper"
                        },
                        text ={ Text("Over The Bumper") }
                    )
                    DropdownMenuItem(
                        onClick = {
                            collectPrefDD = false
                            collectPreference = "UnderTheBumper"
                        },
                        text ={ Text("Under The Bumper") }
                    )
                    DropdownMenuItem(
                        onClick = {
                            collectPrefDD = false
                            collectPreference = "from the Feeder Station"
                        },
                        text = { Text("Feeder Station") }
                    )
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
                colors = TextFieldDefaults.colors(focusedContainerColor = Color(6,9,13),focusedTextColor = defaultOnPrimary),
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier
                    .fillMaxWidth(9f / 10f)
                    .align(Alignment.CenterHorizontally)
                    .height(90.dp)
            )
            Row{
                OutlinedButton(onClick = { if (photoArray.size >= 1) { robotCard = true }}) { Text(text = "Submit", color = defaultOnPrimary) }
                OutlinedButton(onClick = { robotCard = false }) { Text(text = "Close", color = defaultOnPrimary) }
                OutlinedButton(onClick = {}) { Text(text = "Download", color = defaultOnPrimary) }
                OutlinedButton(onClick = { backStack.push(RootNode.NavTarget.MainMenu) }) { Text(text = "Back", color = defaultOnPrimary)
                }
            }
//            if(robotCard){
//                Box(modifier = Modifier.padding(5.dp)) {
//                    Profile(
//                        photoArray,  scoutedTeamName, scoutedTeamNumber, robotType, collectPreference,
//                        robotLength, robotWidth, ampStrength.value, speakerStrength.value,
//                        climbStrength.value, trapStrength.value, concerns,scoutName.value)
//                }
//            }
        }
    }
}
