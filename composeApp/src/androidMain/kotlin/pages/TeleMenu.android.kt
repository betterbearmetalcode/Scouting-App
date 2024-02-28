package pages

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
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
    private val backStack: BackStack<AutoTeleSelectorNode.NavTarget>,

    private val selectAuto: MutableState<Boolean>,

    private val match: MutableState<String>,
    private val team: MutableIntState,
    private val robotStartPosition: MutableIntState
) : Node(buildContext) {
    @Composable
    actual override fun View(modifier: Modifier) {
        val scrollState = rememberScrollState(0)
        val isScrollEnabled = remember{ mutableStateOf(true) }
        val isKeyboardOpen by keyboardAsState()
        val context = LocalContext.current
        var qrCodeFile by remember { mutableStateOf(ImageRequest.Builder(context).data(File("")).build()) }

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


            HorizontalDivider(color = Color.Black, thickness = 4.dp)

            Notes(teleNotes, isScrollEnabled)

            OutlinedButton(
                border = BorderStroke(3.dp, Color.Yellow),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = defaultSecondary),
                onClick = {
                    val outputString = createOutput(team, robotStartPosition)

                    val qrCode = QRCode.ofSquares()
                        .withSize(12)
                        .withBackgroundColor(Colors.GOLD)
                        .withColor(Colors.BLACK)
                        .build(outputString)

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

            )

            Spacer(Modifier.height(15.dp))

            OutlinedButton(
                border = BorderStroke(3.dp, Color.Yellow),
                shape = RoundedCornerShape(25.dp),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 15.dp),
                colors = ButtonDefaults.buttonColors(containerColor = defaultSecondary),
                onClick = {
                    matchScoutArray[parseInt(match.value)] = createOutput(team, robotStartPosition)
                    match.value = (parseInt(match.value) + 1).toString()
                    autoSpeakerNum.intValue = 0
                    autoAmpNum.intValue = 0
                    collected.intValue = 0
                    autoSMissed.intValue = 0
                    autoAMissed.intValue = 0
                    autoNotes.value = ""
                    teleSpeakerNum.intValue = 0
                    teleAmpNum.intValue = 0
                    teleTrapNum.intValue = 0
                    teleSMissed.intValue = 0
                    teleAMissed.intValue = 0
                    m1.intValue = 0
                    m2.intValue = 0
                    m3.intValue = 0
                    m4.intValue = 0
                    m5.intValue = 0
                    f1.intValue = 0
                    f2.intValue = 0
                    f3.intValue = 0
                    teleNotes.value = ""
                    selectAuto.value = false
                    exportScoutData(context)
                    backStack.pop()
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Next Match", fontSize = 20.sp)
            }
        }
    }
}