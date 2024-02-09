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
    private val team: MutableIntState,
    private val robotStartPosition: MutableIntState,
    private val autoSpeakerNum: MutableIntState,
    private val autoAmpNum: MutableIntState,
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
