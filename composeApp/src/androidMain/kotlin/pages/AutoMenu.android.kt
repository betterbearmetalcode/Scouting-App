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
        Column (Modifier.align(Alignment.CenterHorizontally)) {
            val color = CheckboxDefaults.colors(
                checkedColor = Color.Cyan,
                checkmarkColor = Color.Black
            )
            fun getNewState(state: ToggleableState) = when (state) {
                    ToggleableState.Off -> ToggleableState.Indeterminate
                    ToggleableState.Indeterminate -> ToggleableState.On
                    ToggleableState.On -> ToggleableState.Off
            }

            Row {
                Text(
                    "Auto Collect",
                    Modifier.align(Alignment.CenterVertically),
                    fontSize = 20.sp
                )
                Spacer(Modifier.width(10.dp))
                Text("c", Modifier.align(Alignment.CenterVertically))
                TriStateCheckbox(
                    state = f1.value,
                    onClick = {
                        f1.value = getNewState(f1.value)
                    },
                    colors = color
                )
                Text("b", Modifier.align(Alignment.CenterVertically))
                TriStateCheckbox(
                    state = f2.value,
                    onClick = {
                        f2.value = getNewState(f2.value)
                    },
                    colors = color
                )
                Text("a", Modifier.align(Alignment.CenterVertically))
                TriStateCheckbox(
                    state = f3.value,
                    onClick = {
                        f3.value = getNewState(f3.value)
                    },
                    colors = color
                )
            }
            Row {
                Spacer(Modifier.width(10.dp))
                Text(5.toString(), Modifier.align(Alignment.CenterVertically))
                TriStateCheckbox(
                    state = m1.value,
                    onClick = {
                        m1.value = getNewState(m1.value)
                    },
                    colors = color
                )
                Text(4.toString(), Modifier.align(Alignment.CenterVertically))
                TriStateCheckbox(
                    state = m2.value,
                    onClick = {
                        m2.value = getNewState(m2.value)
                    },
                    colors = color
                )
                Text(3.toString(), Modifier.align(Alignment.CenterVertically))
                TriStateCheckbox(
                    state = m3.value,
                    onClick = {
                        m3.value = getNewState(m3.value)
                    },
                    colors = color
                )
                Text(2.toString(), Modifier.align(Alignment.CenterVertically))
                TriStateCheckbox(
                    state = m4.value,
                    onClick = {
                        m4.value = getNewState(m4.value)
                    },
                    colors = color
                )
                Text(1.toString(), Modifier.align(Alignment.CenterVertically))
                TriStateCheckbox(
                    state = m5.value,
                    onClick = {
                        m5.value = getNewState(m5.value)
                    },
                    colors = color
                )
            }
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