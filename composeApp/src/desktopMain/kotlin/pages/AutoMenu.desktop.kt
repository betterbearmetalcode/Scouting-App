@file:OptIn(ExperimentalResourceApi::class)

package pages

import androidx.compose.foundation.*
import composables.Notes
import nodes.RootNode
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
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
import getCurrentTheme
import keyboardAsState
import nodes.matchScoutArray
import nodes.*
import org.jetbrains.compose.resources.ExperimentalResourceApi

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
            Divider(color = getCurrentTheme().primaryVariant, thickness = 2.dp, modifier = Modifier.fillMaxWidth())
            Row {
                Column(modifier = Modifier.offset(0.dp,5.dp)){
                Text ("Auto", /*Modifier.align(Alignment.CenterVertically),*/ fontSize = 25.sp)
                Text ("Collect",/* Modifier.align(Alignment.CenterVertically),*/ fontSize = 25.sp)
                    }
                Column {
                    Spacer(modifier = Modifier.height(10.dp))
                    val color = CheckboxDefaults.colors(
                        checkedColor = Color.Cyan,
                    )
                    val sideColor = Color.Yellow
                    val midColor = Color.Cyan
                    Row {
                        Text(1.toString(), Modifier.align(Alignment.CenterVertically), color = sideColor)
                        Checkbox(
                            when(f1.intValue) {0 -> false; 1 -> true; else -> false},
                            colors = color,
                            onCheckedChange = { when(it) {true -> f1.intValue = 1; false -> f1.intValue = 0}}
                        )
//                        Spacer(Modifier.fillMaxWidth(0.05f))
                        Text(2.toString(), Modifier.align(Alignment.CenterVertically), color = sideColor)
                        Checkbox(
                            when(f2.intValue) {0 -> false; 1 -> true; else -> false},
                            colors = color,
                            onCheckedChange = { when(it) {true -> f2.intValue = 1; false -> f2.intValue = 0}}
                        )
//                        Spacer(Modifier.fillMaxWidth(0.05f))
                        Text(3.toString(), Modifier.align(Alignment.CenterVertically), color = sideColor)
                        Checkbox(
                            when(f3.intValue) {0 -> false; 1 -> true; else -> false},
                            colors = color,
                            onCheckedChange = { when(it) {true -> f3.intValue = 1; false -> f3.intValue = 0}}
                        )
                    }
                    Row {
                        Text(1.toString(), Modifier.align(Alignment.CenterVertically), color = midColor)
                        Checkbox(
                            when(m1.intValue) {0 -> false; 1 -> true; else -> false},
                            colors = color,
                            onCheckedChange = { when(it) {true -> m1.intValue = 1; false -> m1.intValue = 0}}
                        )
//                        Spacer(Modifier.fillMaxWidth(0.05f))
                        Text(2.toString(), Modifier.align(Alignment.CenterVertically), color = midColor)
                        Checkbox(
                            when(m2.intValue) {0 -> false; 1 -> true; else -> false},
                            colors = color,
                            onCheckedChange = { when(it) {true -> m2.intValue = 1; false -> m2.intValue = 0}}
                        )
//                        Spacer(Modifier.fillMaxWidth(0.05f))
                        Text(3.toString(), Modifier.align(Alignment.CenterVertically), color = midColor)
                        Checkbox(
                            when(m3.intValue) {0 -> false; 1 -> true; else -> false},
                            colors = color,
                            onCheckedChange = { when(it) {true -> m3.intValue = 1; false -> m3.intValue = 0}}
                        )
//                        Spacer(Modifier.fillMaxWidth(0.05f))
                        Text(4.toString(), Modifier.align(Alignment.CenterVertically), color = midColor)
                        Checkbox(
                            when(m4.intValue) {0 -> false; 1 -> true; else -> false},
                            colors = color,
                            onCheckedChange = { when(it) {true -> m4.intValue = 1; false -> m4.intValue = 0}}
                        )
//                        Spacer(Modifier.fillMaxWidth(0.05f))
                        Text(5.toString(), Modifier.align(Alignment.CenterVertically), color = midColor)
                        Checkbox(
                            when(m5.intValue) {0 -> false; 1 -> true; else -> false},
                            colors = color,
                            onCheckedChange = { when(it) {true -> m5.intValue = 1; false -> m5.intValue = 0}}
                        )
                    }
                }
            }
            Image(
                painter = org.jetbrains.compose.resources.painterResource("crescendoMap.png"),
                contentDescription = "dimensions checked",
                modifier = Modifier.fillMaxWidth().padding(5.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Divider(color = getCurrentTheme().primaryVariant, thickness = 2.dp, modifier = Modifier.fillMaxWidth())
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
