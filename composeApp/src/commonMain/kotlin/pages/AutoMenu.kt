package pages

import composables.Notes
import RootNode
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
import composables.EnumerableValue
import defaultSecondary
import exportScoutData
import keyboardAsState
import matchScoutArray

class AutoMenu (
    buildContext: BuildContext,
    private val backStack: BackStack<AutoTeleSelectorMenu.NavTarget>,
    private val mainMenuBackStack: BackStack<RootNode.NavTarget>,

    private val selectAuto: MutableState<Boolean>,

    private val match: MutableState<String>,
    private val team: MutableIntState,
    private val robotStartPosition: MutableIntState,
) : Node(buildContext) {
    @Composable
    override fun View(modifier: Modifier) {
        fun bob() {
            mainMenuBackStack.pop()
            matchScoutArray[Integer.parseInt(match.value)] = createOutput(team, robotStartPosition)
            exportScoutData()
        }

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
            EnumerableValue(label = "Collected", value = collected)
            Spacer(modifier = Modifier.height(30.dp))
            EnumerableValue(label = "S Missed", value = autoSMissed)
            EnumerableValue(label = "A Missed", value = autoAMissed)

            Notes(autoNotes, isScrollEnabled)

            OutlinedButton(
                border = BorderStroke(2.dp, color = Color.Yellow),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = defaultSecondary),
                onClick = {
                    backStack.push(AutoTeleSelectorMenu.NavTarget.TeleScouting)
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
