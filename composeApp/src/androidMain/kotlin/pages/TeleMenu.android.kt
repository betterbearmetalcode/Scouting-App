package pages

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme.colors
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
import composables.EnumerableValue
import composables.Comments
import defaultSecondary
import exportScoutData
import keyboardAsState
import nodes.matchScoutArray
import nodes.*
import qrcode.QRCode
import qrcode.color.Colors
import java.io.File
import java.lang.Integer.parseInt

@Composable
actual fun TeleMenu(
    backStack: BackStack<AutoTeleSelectorNode.NavTarget>,
    selectAuto: MutableState<Boolean>,
    match: MutableState<String>,
    team: MutableIntState,
    robotStartPosition: MutableIntState
) {
    val scrollState = rememberScrollState(0)
    val isScrollEnabled = remember{ mutableStateOf(true) }
    val isKeyboardOpen by keyboardAsState()
    val context = LocalContext.current
    var qrCodeFile by remember { mutableStateOf(ImageRequest.Builder(context).data(File("")).build()) }

    if(!isKeyboardOpen){
        isScrollEnabled.value = true
    }

    Column(
        Modifier
            .verticalScroll(state = scrollState, enabled = isScrollEnabled.value)
            .padding(20.dp)) {

        EnumerableValue(label = "Speaker" , value = teleSpeakerNum)//It no worky?
        EnumerableValue(label = "Amp" , value = teleAmpNum)
        EnumerableValue(label = "Trap" , value = teleTrapNum)
        Spacer(modifier = Modifier.height(30.dp))
        EnumerableValue(label = "S Missed", value = teleSMissed)
        EnumerableValue(label = "A Missed", value = teleAMissed)
        Row {
            Text("Lost Comms?")
            Checkbox(
                when(lostComms.intValue) {0 -> false; 1 -> true; else -> false},
                onCheckedChange = { when(it) {true -> lostComms.intValue = 1; false -> lostComms.intValue = 0} })
        }

        HorizontalDivider(color = Color.Black, thickness = 4.dp)

        Comments(teleNotes, isScrollEnabled)

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
                reset()
                teleNotes.value = ""
                selectAuto.value = false
                exportScoutData(context)
                loadData(parseInt(match.value),team)
                println(matchScoutArray[parseInt(match.value)])
                backStack.pop()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Next Match", fontSize = 20.sp)
        }
    }
}
