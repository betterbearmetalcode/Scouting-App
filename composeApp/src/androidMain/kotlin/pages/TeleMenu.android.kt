package pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.operation.pop
import composables.EnumerableValue
import composables.Notes
import defaultSecondary
import exportScoutData
import keyboardAsState
import nodes.*
import java.lang.Integer.parseInt

@Composable
actual fun TeleMenu (
    backStack: BackStack<AutoTeleSelectorNode.NavTarget>,
    mainMenuBackStack: BackStack<RootNode.NavTarget>,

    selectAuto: MutableState<Boolean>,

    match: MutableState<String>,
    team: MutableIntState,
    robotStartPosition: MutableIntState
) {
    val scrollState = rememberScrollState(0)
    val isScrollEnabled = remember{ mutableStateOf(true) }
    val isKeyboardOpen by keyboardAsState()
    val context = LocalContext.current

    fun bob() {
        mainMenuBackStack.pop()
        matchScoutArray.putIfAbsent(robotStartPosition.intValue, HashMap())
        matchScoutArray[robotStartPosition.intValue]?.set(parseInt(match.value), createOutput(team, robotStartPosition))
        exportScoutData(context)
    }

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


        HorizontalDivider(color = Color.Black, thickness = 4.dp)

        Notes(teleNotes, isScrollEnabled)

        Spacer(Modifier.height(15.dp))

        OutlinedButton(
            border = BorderStroke(3.dp, Color.Yellow),
            shape = RoundedCornerShape(25.dp),
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 15.dp),
            colors = ButtonDefaults.buttonColors(containerColor = defaultSecondary),
            onClick = {
                matchScoutArray.putIfAbsent(robotStartPosition.intValue, HashMap())
                matchScoutArray[robotStartPosition.intValue]?.set(parseInt(match.value),
                    createOutput(team, robotStartPosition)
                )
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

        OutlinedButton(
            border = BorderStroke(2.dp, color = Color.Yellow),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(containerColor = defaultSecondary),
            onClick = {
                bob()
            }
        ) {
            Text(
                text = "Back",
                color = Color.Yellow
            )
        }
    }
}
