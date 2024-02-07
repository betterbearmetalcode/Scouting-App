package pages

import EnumerableValue
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node

actual class AutoMenu actual constructor(
    buildContext: BuildContext,
    private val backStack: BackStack<AutoTeleSelectorMenu.NavTarget>,
    private val match: MutableState<String>,
    private val team: MutableState<String>,
    private val allianceColor: MutableState<Boolean>,
    private val autoSpeakerNum: MutableState<Int>,
    private val autoAmpNum: MutableState<Int>,
    private val quanNotes: MutableState<String>
) : Node(buildContext) {
    @Composable
    actual override fun View(modifier: Modifier) {
        val scrollState = rememberScrollState(0)
        var isScrollEnabled by remember{ mutableStateOf(true) }
        val isKeyboardOpen by keyboardAsState()

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
            Row {
                Text(
                    text = "Match" + ""//blue alliance
                )
                TextField(
                    value = match.value,
                    onValueChange = { match.value = it },
                    modifier = Modifier.fillMaxWidth(1f/4f)
                )
                Text(
                    text = "Team" + ""//blue alliance
                )
                TextField(
                    value = team.value,
                    onValueChange = { team.value = it },
                    modifier = Modifier.fillMaxWidth(1f/2f)
                )
                Switch(
                    checked = allianceColor.value,
                    onCheckedChange = { allianceColor.value = it },
                    colors = SwitchDefaults.colors(
                        uncheckedThumbColor = Color.Blue,
                        uncheckedTrackColor = Color(38, 95, 240),
                        checkedThumbColor = Color.Red,
                        checkedTrackColor = Color(252,40,62)
                    ),
                    modifier = Modifier
                        .scale(1.5f)
                        .padding(15.dp)
                )
            }

            EnumerableValue(label = "Speaker", value = autoSpeakerNum)
            EnumerableValue(label = "Amp", value = autoAmpNum)

            TextField(
                value = quanNotes.value,
                placeholder = {
                    Text("Write Here")
                },
                onValueChange = {
                    quanNotes.value = it;
                    isScrollEnabled = false
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(400.dp, 200.dp)
            )
        }
    }
}
