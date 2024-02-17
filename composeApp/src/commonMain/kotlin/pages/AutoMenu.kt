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
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import composables.EnumerableValue
import defaultBackground
import defaultOnBackground
import defaultSecondary
import keyboardAsState

class AutoMenu (
    buildContext: BuildContext,
    private val mainMenuBackStack: BackStack<RootNode.NavTarget>,
    private val left: MutableState<Boolean>,
    private val autoSpeakerNum: MutableIntState,
    private val autoAmpNum: MutableIntState,
    private val collected: MutableIntState,
    private val sMissed: MutableIntState,
    private val aMissed: MutableIntState,
    private val quanNotes: MutableState<String>,

    ) : Node(buildContext) {
    @Composable
    override fun View(modifier: Modifier) {
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
            Box(Modifier.fillMaxWidth()) {
                Text("Leave?", Modifier.align(Alignment.CenterStart), fontSize = 25.sp)

                Row (Modifier.align(Alignment.Center)){
                    Text ("Y", Modifier.align(Alignment.CenterVertically))
                    Switch(
                        checked = left.value,
                        onCheckedChange = {
                            left.value = it
                        },
                        colors = SwitchDefaults.colors(
                            uncheckedTrackColor = defaultOnBackground,
                            uncheckedThumbColor = defaultBackground,
                            uncheckedTrackAlpha = 1f,
                            checkedTrackColor = defaultOnBackground,
                            checkedThumbColor = defaultBackground,
                            checkedTrackAlpha = 1f
                        )
                    )
                    Text("N", Modifier.align(Alignment.CenterVertically))
                }
            }

            EnumerableValue(label = "Speaker", value = autoSpeakerNum)
            EnumerableValue(label = "Amp", value = autoAmpNum)
            EnumerableValue(label = "Collected", value = collected)
            Spacer(modifier = Modifier.height(30.dp))
            EnumerableValue(label = "S Missed", value = sMissed)
            EnumerableValue(label = "A Missed", value = aMissed)

            Notes(quanNotes, isScrollEnabled)

            OutlinedButton(
                border = BorderStroke(2.dp, color = Color.Yellow),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = defaultSecondary),
                onClick = {
                    mainMenuBackStack.pop()
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
