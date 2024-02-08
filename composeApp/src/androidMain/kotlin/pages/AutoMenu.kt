package pages

import BackButton
import CheckBox
import EnumerableValue
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import java.nio.charset.Charset
import java.util.zip.Deflater

actual class AutoMenu actual constructor(
    buildContext: BuildContext,
    private val backStack: BackStack<AutoTeleSelectorMenu.NavTarget>,
    match: MutableState<String>,
    team: MutableState<String>,
    robotStartPosition: MutableIntState,
    autoSpeakerNum: MutableIntState,
    autoAmpNum: MutableIntState,
    quanNotes: MutableState<String>
) : Node(buildContext) {
    @OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
    @Composable
    actual override fun View(modifier: Modifier) {
        var match by remember { mutableStateOf("") }
        var team by remember { mutableStateOf("") }
        var allianceColor by remember { mutableStateOf(false) }
        val autoSpeakerNum = remember { mutableIntStateOf(0) }
        val autoAmpNum = remember { mutableIntStateOf(0) }
        val teleSpeakerNum  = remember { mutableIntStateOf(0) }
        val teleAmpNum  = remember { mutableIntStateOf(0) }
        val teleAmplified = remember { mutableIntStateOf(0) }
        val teleTrapNum = remember { mutableIntStateOf(0) }
        var endGameDropDown by remember { mutableStateOf(false) }
        var selectedEndPos by remember { mutableStateOf("None") }
        val lostComms = remember { mutableStateOf(false)}
        val iForgotWhatGoesHere = remember {mutableStateOf(false)}
        val refactorLater = remember {mutableStateOf(false)}
        val somethingGoesHereButWhoKnowsWhat = remember {mutableStateOf(false)}
        var quanNotes by remember { mutableStateOf("")}
        val scrollState = rememberScrollState(0)
        var isScrollEnabled by remember{ mutableStateOf(true)}
        val isKeyboardOpen by keyboardAsState()
        val context = LocalContext.current
        var qrCodeFile by remember { mutableStateOf(ImageRequest.Builder(context).data(File("")).build()) }
        data class EndPosition(val endPos: String)

        fun endPosition() = listOf(
            EndPosition("None"),
            EndPosition("Parked"),
            EndPosition("Climbed"),
            EndPosition("Harmonized")
        )

        if(!isKeyboardOpen){
            isScrollEnabled = true
        }

        Column(
            modifier
                .verticalScroll(state = scrollState, enabled = isScrollEnabled,)
                .padding(20.dp)) {
            Row {
                Text(
                    text = "Match" + ""//blue alliance
                )
                TextField(
                    value = match,
                    onValueChange = { match = it },
                    modifier = Modifier.fillMaxWidth(1f/4f)
                )
                Text(
                    text = "Team" + ""//blue alliance
                )
                TextField(
                    value = team,
                    onValueChange = { team = it },
                    modifier = Modifier.fillMaxWidth(1f/2f)
                )
                Switch(
                    checked = allianceColor,
                    onCheckedChange = { allianceColor = !allianceColor },
                    colors = SwitchDefaults.colors(
                        uncheckedThumbColor = Color.Blue,
                        uncheckedTrackColor = Color(38, 95, 240),
                        checkedThumbColor = Color.Red,
                        checkedTrackColor = Color(252,40,62)
                    ),
                    modifier = Modifier
                        .scale(1.5f)
                        .padding(15.dp)
                )
            }
            Text(
                text = "Auto",
                fontSize = 25.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Divider(color = Color(255, 191, 0), thickness = 1.dp)

            EnumerableValue(label = "Speaker", value = autoSpeakerNum)
            EnumerableValue(label = "Amp", value = autoAmpNum)

            Text(
                text = "Teleop",
                fontSize = 25.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Divider(color = Color(255, 191, 0), thickness = 1.dp)

            EnumerableValue(label ="Speaker" , value = teleSpeakerNum)//No worky?
            EnumerableValue(label ="Amp" , value = teleAmpNum)
            EnumerableValue(label ="Amplified" , value = teleAmplified)
            EnumerableValue(label ="Trap" , value = teleTrapNum)

            Divider(color = Color.Black, thickness = 4.dp)
            Row{
            Button(
                onClick = {endGameDropDown= !endGameDropDown},
                content = {
                    Text(text = "v  " + selectedEndPos)

                }
            )
            DropdownMenu(
                expanded = endGameDropDown,
                onDismissRequest = {endGameDropDown = false},
                modifier = Modifier.background(MaterialTheme.colors.background)
            ){
                endPosition().forEach { (endPos) ->
                    DropdownMenuItem(
                        onClick = {
                            endGameDropDown = false
                            selectedEndPos = endPos
                        }
                    ){
                        Text(text = endPos)
                    }
                }
            }
            }
            Row{
                CheckBox(label ="Lost Comms:", ifChecked = lostComms )
                CheckBox(label =":", ifChecked = iForgotWhatGoesHere )
            }
            Row {
                CheckBox(label = ":", ifChecked = refactorLater)
                CheckBox(label = ":", ifChecked = somethingGoesHereButWhoKnowsWhat)
            }
            TextField(
                value = quanNotes,
                placeholder = {
                      Text("Write Here")
                    },
                onValueChange = {
                    quanNotes = it;
                    isScrollEnabled = false
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(400.dp, 200.dp)
            )
            Button(
                onClick = {
                    val jsonObject = JSONObject()

                    jsonObject.put("M", match)
                    jsonObject.put("T", team)
                    jsonObject.put("A", allianceColor)
                    jsonObject.put("AS", autoSpeakerNum)
                    jsonObject.put("AA", autoAmpNum)
                    jsonObject.put("TS", teleSpeakerNum)
                    jsonObject.put("TA", teleAmpNum)
                    jsonObject.put("TA2", teleAmplified)
                    jsonObject.put("TT", teleTrapNum)
                    jsonObject.put("EP", selectedEndPos)
                    jsonObject.put("LC", lostComms)
                    jsonObject.put("N", quanNotes)

                    var inputString = jsonObject.toString()
                    var input = inputString.toByteArray(Charset.defaultCharset())

                    var output = ByteArray(100)
                    val compressor = Deflater()
                    compressor.setInput(input)
                    compressor.finish()
                    val compressedDataLength = compressor.deflate(output)
                    compressor.end()


                    val qrCode = QRCode.ofRoundedSquares()
                        .withSize(12)
                        .withBackgroundColor(Colors.BLACK)
                        .withColor(Colors.GOLD)
                        .build(String(output, 0, compressedDataLength, Charset.defaultCharset()) + "&$compressedDataLength")

                    val pngBytes = qrCode.render()

                    qrCodeFile = ImageRequest.Builder(context).data(pngBytes.getBytes()).build()

                }
            ) {
                Text("Export to QR code")
            }

            AsyncImage(
                model = qrCodeFile,
                contentDescription = "QR Code",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize(1.25f)
            )
        }
    }
}
