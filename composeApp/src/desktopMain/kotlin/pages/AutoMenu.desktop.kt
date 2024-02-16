package pages

import RootNode
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.operation.pop
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import composables.EnumerableValue
import defaultSecondary
import keyboardAsState

actual class AutoMenu actual constructor(
    buildContext: BuildContext,
    private val mainMenuBackStack: BackStack<RootNode.NavTarget>,
    private val autoSpeakerNum: MutableIntState,
    private val autoAmpNum: MutableIntState,
    private val quanNotes: MutableState<String>,
) : Node(buildContext) {
    @Composable
    actual override fun View(modifier: Modifier) {
        val scrollState = rememberScrollState(0)
        var isScrollEnabled by remember{ mutableStateOf(true) }
        val isKeyboardOpen by keyboardAsState()

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
