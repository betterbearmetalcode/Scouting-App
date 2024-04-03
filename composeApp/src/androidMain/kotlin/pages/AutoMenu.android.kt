package pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.operation.pop
import com.bumble.appyx.components.backstack.operation.push
import composables.AutoCheckboxesHorizontal
import composables.AutoCheckboxesVertical
import composables.EnumerableValue
import defaultSecondary
import exportScoutData
import keyboardAsState
import nodes.*

@Composable
actual fun AutoMenu(
    backStack: BackStack<AutoTeleSelectorNode.NavTarget>,
    mainMenuBackStack: BackStack<RootNode.NavTarget>,

    selectAuto: MutableState<Boolean>,

    match: MutableState<String>,
    team: MutableIntState,
    robotStartPosition: MutableIntState
) {
    val context = LocalContext.current
    fun bob() {
        mainMenuBackStack.pop()
        matchScoutArray.putIfAbsent(robotStartPosition.intValue, HashMap())
        matchScoutArray[robotStartPosition.intValue]?.set(Integer.parseInt(match.value),
            createOutput(team, robotStartPosition)
        )
        exportScoutData(context)
    }
    val scrollState = rememberScrollState(0)
    val isScrollEnabled = remember{ mutableStateOf(true) }
    val isKeyboardOpen by keyboardAsState()



    if(!isKeyboardOpen){
        isScrollEnabled.value = true
    }

    Column(
        Modifier
            .verticalScroll(state = scrollState, enabled = isScrollEnabled.value)
            .padding(20.dp)
    ) {


        EnumerableValue(label = "Speaker", value = autoSpeakerNum)
        EnumerableValue(label = "Amp", value = autoAmpNum)


        Spacer(modifier = Modifier.height(10.dp))

        if (rotateCheckboxes.value) {
            AutoCheckboxesVertical()
        } else {
            AutoCheckboxesHorizontal()

        }

        EnumerableValue(label = "S Missed", value = autoSMissed)
        EnumerableValue(label = "A Missed", value = autoAMissed)


        Spacer(Modifier.height(5.dp))

        OutlinedButton(
            border = BorderStroke(2.dp, color = Color.Yellow),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(containerColor = defaultSecondary),
            onClick = {
                backStack.push(AutoTeleSelectorNode.NavTarget.TeleScouting)
                selectAuto.value = true
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Tele",
                color = Color.Yellow,
                fontSize = 35.sp
            )
        }

        Spacer(Modifier.height(25.dp))

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