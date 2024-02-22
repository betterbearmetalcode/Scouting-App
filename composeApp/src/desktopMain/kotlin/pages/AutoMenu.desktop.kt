package pages

import composables.Notes
import nodes.RootNode
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.operation.pop
import com.bumble.appyx.components.backstack.operation.push
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import composables.CheckBox
import composables.EnumerableValue
import defaultSecondary
import exportScoutData
import keyboardAsState
import nodes.matchScoutArray
import nodes.*

actual class AutoMenu actual constructor(
    buildContext: BuildContext,
    private val backStack: BackStack<AutoTeleSelectorNode.NavTarget>,
    private val mainMenuBackStack: BackStack<RootNode.NavTarget>,

    private val selectAuto: MutableState<Boolean>,

    private val match: MutableState<String>,
    private val team: MutableIntState,
    private val robotStartPosition: MutableIntState,
) : Node(buildContext) {
    @Composable
    actual override fun View(modifier: Modifier) {
        fun bob() {
            mainMenuBackStack.pop()
            matchScoutArray[Integer.parseInt(match.value)] = createOutput(team, robotStartPosition)
            exportScoutData()
        }

        val dummyBool = mutableStateOf(false)
        val scrollState = rememberScrollState(0)
        val isScrollEnabled = remember{ mutableStateOf(true) }
        val isKeyboardOpen by keyboardAsState()

        if(!isKeyboardOpen){
            isScrollEnabled.value = true
        }

        Column(
            modifier
                .verticalScroll(state = scrollState, enabled = isScrollEnabled.value)
                .padding(20.dp)
        ) {


            EnumerableValue(label = "Speaker", value = autoSpeakerNum)
            EnumerableValue(label = "Amp", value = autoAmpNum)
            //EnumerableValue(label = "Collected", value = collected)
            Row {
                Text ("Auto Collect", Modifier.align(Alignment.CenterVertically), fontSize = 25.sp)
                Column {
                    Spacer(modifier = Modifier.height(10.dp))
                    val color = CheckboxDefaults.colors(
                        checkedColor = Color.Cyan,
                    )
                    Row {
                        Text(1.toString(), Modifier.align(Alignment.CenterVertically))
                        Checkbox(
                            when(f1.intValue) {0 -> false; 1 -> true; else -> false},
                            colors = color,
                            onCheckedChange = { when(it) {true -> f1.intValue = 1; false -> f1.intValue = 0}}
                        )
                        Spacer(Modifier.width(15.dp))
                        Text(2.toString(), Modifier.align(Alignment.CenterVertically))
                        Checkbox(
                            when(f2.intValue) {0 -> false; 1 -> true; else -> false},
                            colors = color,
                            onCheckedChange = { when(it) {true -> f2.intValue = 1; false -> f2.intValue = 0}}
                        )
                        Spacer(Modifier.width(15.dp))
                        Text(3.toString(), Modifier.align(Alignment.CenterVertically))
                        Checkbox(
                            when(f3.intValue) {0 -> false; 1 -> true; else -> false},
                            colors = color,
                            onCheckedChange = { when(it) {true -> f3.intValue = 1; false -> f3.intValue = 0}}
                        )
                    }
                    Row {
                        Text(1.toString(), Modifier.align(Alignment.CenterVertically))
                        Checkbox(
                            when(m1.intValue) {0 -> false; 1 -> true; else -> false},
                            colors = color,
                            onCheckedChange = { when(it) {true -> m1.intValue = 1; false -> m1.intValue = 0}}
                        )
                        Spacer(Modifier.width(15.dp))
                        Text(2.toString(), Modifier.align(Alignment.CenterVertically))
                        Checkbox(
                            when(m2.intValue) {0 -> false; 1 -> true; else -> false},
                            colors = color,
                            onCheckedChange = { when(it) {true -> m2.intValue = 1; false -> m2.intValue = 0}}
                        )
                        Spacer(Modifier.width(15.dp))
                        Text(3.toString(), Modifier.align(Alignment.CenterVertically))
                        Checkbox(
                            when(m3.intValue) {0 -> false; 1 -> true; else -> false},
                            colors = color,
                            onCheckedChange = { when(it) {true -> m3.intValue = 1; false -> m3.intValue = 0}}
                        )
                        Spacer(Modifier.width(15.dp))
                        Text(4.toString(), Modifier.align(Alignment.CenterVertically))
                        Checkbox(
                            when(m4.intValue) {0 -> false; 1 -> true; else -> false},
                            colors = color,
                            onCheckedChange = { when(it) {true -> m4.intValue = 1; false -> m4.intValue = 0}}
                        )
                        Spacer(Modifier.width(15.dp))
                        Text(5.toString(), Modifier.align(Alignment.CenterVertically))
                        Checkbox(
                            when(m5.intValue) {0 -> false; 1 -> true; else -> false},
                            colors = color,
                            onCheckedChange = { when(it) {true -> m5.intValue = 1; false -> m5.intValue = 0}}
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            EnumerableValue(label = "S Missed", value = autoSMissed)
            EnumerableValue(label = "A Missed", value = autoAMissed)

            Notes(autoNotes, isScrollEnabled)

            OutlinedButton(
                border = BorderStroke(2.dp, color = Color.Yellow),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = defaultSecondary),
                onClick = {
                    backStack.push(AutoTeleSelectorNode.NavTarget.TeleScouting)
                    selectAuto.value = true
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ){
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
                colors = ButtonDefaults.buttonColors(backgroundColor = defaultSecondary),
                onClick = {
                    exportScoutData()
                    bob()
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
