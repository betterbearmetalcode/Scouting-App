package pages

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import composables.EnumerableValue
import composables.Notes
import defaultSecondary
import exportScoutData
import keyboardAsState
import nodes.matchScoutArray
import nodes.*
import qrcode.QRCode
import qrcode.color.Colors
import java.io.File
import java.lang.Integer.parseInt

actual class TeleMenu actual constructor(
    buildContext: BuildContext,
    backStack: BackStack<AutoTeleSelectorNode.NavTarget>,
    selectAuto: MutableState<Boolean>,
    match: MutableState<String>,
    team: MutableIntState,
    robotStartPosition: MutableIntState,
    autoAmpNum: MutableIntState,
    autoSpeakerNum: MutableIntState,
    autoNotes: MutableState<String>,
    teleSpeakerNum: MutableIntState,
    teleAmpNum: MutableIntState,
    teleAmplified: MutableIntState,
    teleTrapNum: MutableState<Int>,
    selectedEndPos: MutableState<String>,
    teleNotes: MutableState<String>,
    lostComms: MutableState<Boolean>
) : Node(buildContext) {
    @Composable
    actual override fun View(modifier: Modifier) {
        val scrollState = rememberScrollState(0)
        val isScrollEnabled = remember{ mutableStateOf(true) }
        val isKeyboardOpen by keyboardAsState()
        var qrCodeBytes by remember{ mutableStateOf(File("src/commonMain/resources/Empty Qr Code.png").readBytes())}

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

            Notes(teleNotes, isScrollEnabled)

            OutlinedButton(
                border = BorderStroke(3.dp, Color.Yellow),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = defaultSecondary),
                onClick = {
                    val outputString = createOutput(team.value, robotStartPosition.value)

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
                    matchScoutArray[parseInt(match.value)] = createOutput(team, robotStartPosition)
                    match.value = (parseInt(match.value) + 1).toString()
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
                    teleNotes.value = ""
                    selectAuto.value = false
                    exportScoutData()
                    backStack.pop()
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Next Match", fontSize = 20.sp)
            }
        }
    }
}