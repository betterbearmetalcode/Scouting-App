package pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node

actual class TeleMenu actual constructor(
    buildContext: BuildContext,
    private val backStack: BackStack<AutoTeleSelectorMenu.NavTarget>,
    private val match: MutableState<String>,
    private val team: MutableIntState,
    private val robotStartPosition: MutableIntState,
    private val autoSpeakerNum: MutableIntState,
    private val autoAmpNum: MutableIntState,
    private val autoNotes: MutableState<String>,

    private val teleSpeakerNum: MutableIntState,
    private val teleAmpNum: MutableIntState,
    private val teleAmplified: MutableIntState,
    private val teleTrapNum: MutableIntState,
    private val selectedEndPos: MutableState<String>,
    private val teleNotes: MutableState<String>,
    private val lostComms: MutableState<Boolean>
) : Node(buildContext) {
    @Composable
    actual override fun View(modifier: Modifier) {
    }

}