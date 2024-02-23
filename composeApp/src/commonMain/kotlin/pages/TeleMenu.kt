package pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import nodes.AutoTeleSelectorNode


expect class TeleMenu(
    buildContext: BuildContext,
    backStack: BackStack<AutoTeleSelectorNode.NavTarget>,
    selectAuto: MutableState<Boolean>,
    match: MutableState<String>,
    team: MutableIntState,
    robotStartPosition: MutableIntState,
    autoAmpNum: MutableIntState,
    autoSpeakerNum: MutableIntState,
    autoNotes: MutableState<String>,
    teleSpeakerNum: MutableIntState,
    teleAmpNum: MutableIntState,
    teleAmplified: MutableIntState,
    teleTrapNum: MutableState<Int>,
    selectedEndPos: MutableState<String>,
    teleNotes: MutableState<String>,
    lostComms: MutableState<Boolean>
) : Node {
    @Composable
    override fun View(modifier: Modifier)
}