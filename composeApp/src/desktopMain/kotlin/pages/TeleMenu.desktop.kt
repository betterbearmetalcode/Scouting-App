package pages

import composables.CheckBox
import composables.EnumerableValue
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import org.jetbrains.compose.resources.ExperimentalResourceApi
import qrcode.QRCode
import java.io.File

actual class TeleMenu actual constructor(
    buildContext: BuildContext,
    private val backStack: BackStack<AutoTeleSelectorMenu.NavTarget>,
    private val match: MutableState<String>,
    private val team: MutableIntState,
    private val robotStartPosition: MutableIntState,
    private val autoSpeakerNum: MutableIntState,
    private val autoAmpNum: MutableIntState,
    private val autoNotes: MutableState<String>,

    private val teleSpeakerNum: MutableIntState,
    private val teleAmpNum: MutableIntState,
    private val teleAmplified: MutableIntState,
    private val teleTrapNum: MutableIntState,
    private val selectedEndPos: MutableState<String>,
    private val teleNotes: MutableState<String>,
    private val lostComms: MutableState<Boolean>

) : Node(buildContext) {
    @OptIn(ExperimentalResourceApi::class)
    @Composable
    actual override fun View(modifier: Modifier) {
        var endGameDropDown by remember { mutableStateOf(false) }
        val scrollState = rememberScrollState(0)
        var isScrollEnabled by remember{ mutableStateOf(true) }
        val isKeyboardOpen by keyboardAsState()
        var allianceColor by remember { mutableStateOf(false) }
        var qrCodeBytes by remember{ mutableStateOf(File("src/commonMain/resources/Empty Qr Code.png").readBytes())}
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

            EnumerableValue(label ="Speaker" , value = teleSpeakerNum)//No worky?
            EnumerableValue(label ="Amp" , value = teleAmpNum)
            EnumerableValue(label ="Amplified" , value = teleAmplified)
            EnumerableValue(label ="Trap" , value = teleTrapNum)

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

            TextField(
                value = teleNotes.value,
                placeholder = {
                    Text("Write Here")
                },
                onValueChange = {
                    teleNotes.value = it;
                    isScrollEnabled = false
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(400.dp, 200.dp)
            )
            Button(
                onClick = {

                    val outputString: String = match.value + "/" +
                            team.value + "/" + robotStartPosition.value + "/" +
                            autoSpeakerNum.value + "/" + autoAmpNum.value + "/" +
                            teleSpeakerNum.value + "/" + teleAmpNum.value + "/" +
                            teleAmplified.value + "/" + teleTrapNum.value + "/" +
                            selectedEndPos.value + "/" + lostComms.value + "/" +
                            autoNotes.value + "/" + teleNotes.value




                    val qrCode = QRCode.ofRoundedSquares()
                        .withSize(12)
                        .withBackgroundColor(qrcode.color.Colors.BLACK)
                        .withColor(qrcode.color.Colors.GOLD)
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
        }
    }

}