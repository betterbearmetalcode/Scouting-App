package pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
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
import composables.Comments
import defaultSecondary
import exportScoutData
import keyboardAsState
import nodes.*
import setTeam
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

        EnumerableValue(label = "Speaker", value = teleSpeakerNum)//It no worky?
        EnumerableValue(label = "Amp", value = teleAmpNum)
        EnumerableValue(label = "Shuttled", value = telePassed)
        EnumerableValue(label = "Trap", value = teleTrapNum)

        Spacer(modifier = Modifier.height(30.dp))

        EnumerableValue(label = "S Missed", value = teleSMissed)
        EnumerableValue(label = "A Missed", value = teleAMissed)

        Spacer(modifier = Modifier.height(30.dp))

        EnumerableValue(label = "S Received", value = teleSReceived)
        EnumerableValue(label = "A Received", value = teleAReceived)

        Row {
            Text("Lost Comms?")
            Checkbox(
                when(lostComms.intValue) {0 -> false; 1 -> true; else -> false},
                onCheckedChange = { when(it) {true -> lostComms.intValue = 1; false -> lostComms.intValue = 0} })
        }


        HorizontalDivider(color = Color.Yellow, thickness = 4.dp)

        Comments(teleNotes, isScrollEnabled)

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
                reset()
                teleNotes.value = ""
                selectAuto.value = false
                exportScoutData(context)
                loadData(parseInt(match.value), team, robotStartPosition)
                backStack.pop()
                setTeam(team,match,robotStartPosition.intValue)
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
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(
                text = "Back",
                color = Color.Yellow
            )
        }
    }
}
