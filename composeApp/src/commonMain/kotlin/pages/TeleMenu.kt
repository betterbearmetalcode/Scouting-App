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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.operation.pop
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import composables.CheckBox
import composables.EnumerableValue
import composables.Notes
import defaultSecondary
import keyboardAsState
import qrcode.QRCode
import qrcode.color.Colors
import java.io.File

class TeleMenu(
    buildContext: BuildContext,
    private val backStack: BackStack<AutoTeleSelectorMenu.NavTarget>,
    private val mainMenuBackStack: BackStack<RootNode.NavTarget>,

    private val selectAuto: MutableState<Boolean>,

    private val match: MutableState<String>,
    private val team: MutableIntState,
    private val robotStartPosition: MutableIntState,
    private val left: MutableState<Boolean>,
    private val autoSpeakerNum: MutableIntState,
    private val autoAmpNum: MutableIntState,
    private val collected: MutableIntState,
    private val autoSMissed: MutableIntState,
    private val autoAMissed: MutableIntState,
    private val autoNotes: MutableState<String>,

    private val teleSpeakerNum: MutableIntState,
    private val teleAmpNum: MutableIntState,
    private val teleTrapNum: MutableIntState,
    private val teleSMissed: MutableIntState,
    private val teleAMissed: MutableIntState,
    private val selectedEndPos: MutableState<String>,
    private val teleNotes: MutableState<String>,
    private val lostComms: MutableState<Boolean>

) : Node(buildContext) {
    @Composable
    override fun View(modifier: Modifier) {
        var endGameDropDown by remember { mutableStateOf(false) }
        val scrollState = rememberScrollState(0)
        val isScrollEnabled = remember{ mutableStateOf(true) }
        val isKeyboardOpen by keyboardAsState()
        var qrCodeBytes by remember{ mutableStateOf(File("src/commonMain/resources/Empty Qr Code.png").readBytes())}
        data class EndPosition(val endPos: String)



        fun endPosition() = listOf(
            EndPosition("None"),
            EndPosition("Parked"),
            EndPosition("Climbed"),
            EndPosition("Harmonized")
        )

        if(!isKeyboardOpen){
            isScrollEnabled.value = true
        }

        Column(
            modifier
                .verticalScroll(state = scrollState, enabled = isScrollEnabled.value)
                .padding(20.dp)) {

            EnumerableValue(label = "Speaker" , value = teleSpeakerNum)//It no worky?
            EnumerableValue(label = "Amp" , value = teleAmpNum)
            EnumerableValue(label = "Trap" , value = teleTrapNum)
            Spacer(modifier = Modifier.height(30.dp))
            EnumerableValue(label = "S Missed", value = teleSMissed)
            EnumerableValue(label = "A Missed", value = teleAMissed)


            Divider(color = Color.Black, thickness = 4.dp)
            Row{
                Button(
                    onClick = {endGameDropDown = !endGameDropDown},
                    content = {
                        Text(text = "v  " + selectedEndPos.value)

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
                                selectedEndPos.value = endPos
                            }
                        ){
                            Text(text = endPos)
                        }
                    }
                }
            }
            CheckBox(label ="Lost Comms:", ifChecked = lostComms )

            Notes(teleNotes, isScrollEnabled)

            Button(
                onClick = {
                    val teleNotesFinal = if (teleNotes.value == "") "No Notes" else teleNotes.value
                    val autoNotesFinal = if (autoNotes.value == "") "No Notes" else autoNotes.value

                    val outputString: String = match.value + "/" +
                            team.value + "/" + robotStartPosition.value + "/" +
                            (if(left.value) 1 else 0) + "/" + autoSpeakerNum.value + "/" +
                            autoAmpNum.value + "/" + collected.value + "/" +
                            autoSMissed.value + "/" + autoAMissed.value + "/" +
                            teleSpeakerNum.value + "/" + teleAmpNum.value + "/" +
                            teleTrapNum.value + "/" + teleSMissed.value + "/" +
                            teleAMissed.value + "/" + when (selectedEndPos.value) {"None" -> 0; "Parked" -> 1; "Climbed" -> 2; "Harmony" -> 3; else -> 0} + "/" +
                            autoNotesFinal + "/" + teleNotesFinal

                    val qrCode = QRCode.ofSquares()
                        .withSize(12)
                        .withBackgroundColor(Colors.GOLD)
                        .withColor(Colors.BLACK)
                        .build(outputString)

                    val pngBytes = qrCode.render()

                    qrCodeBytes = pngBytes.getBytes()
                }
            ) {
                Text("Export to QR code")
            }

            Image(
                painter = BitmapPainter(org.jetbrains.skia.Image.makeFromEncoded(qrCodeBytes).toComposeImageBitmap()),
                contentDescription = "QR Code",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize(1.25f)
            )

            Spacer(Modifier.height(15.dp))

            OutlinedButton(
                border = BorderStroke(3.dp, Color.Yellow),
                shape = RoundedCornerShape(25.dp),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 15.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = defaultSecondary),
                onClick = {
                    match.value = (Integer.parseInt(match.value) + 1).toString()
                    left.value = false
                    autoSpeakerNum.value = 0
                    autoAmpNum.value = 0
                    collected.value = 0
                    autoSMissed.value = 0
                    autoAMissed.value = 0
                    autoNotes.value = ""
                    teleSpeakerNum.value = 0
                    teleAmpNum.value = 0
                    teleTrapNum.value = 0
                    teleSMissed.value = 0
                    teleAMissed.value = 0
                    selectedEndPos.value = "None"
                    teleNotes.value = ""
                    selectAuto.value = false
                    backStack.pop()
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Next Match", fontSize = 20.sp)
            }

            OutlinedButton(
                border = BorderStroke(2.dp, color = Color.Yellow),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = defaultSecondary),
                onClick = {
                    mainMenuBackStack.pop()
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ){
                Text(
                    text = "Back",
                    color = Color.Yellow
                )
            }
        }
    }

}