package pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node

expect class AutoMenu(
    buildContext: BuildContext,
    backStack: BackStack<AutoTeleSelectorMenu.NavTarget>,
    match: MutableState<String>,
    team: MutableState<String>,
    allianceColor: MutableState<Boolean>,
    autoSpeakerNum: MutableState<Int>,
    autoAmpNum: MutableState<Int>,
    quanNotes: MutableState<String>
) : Node {

    @Composable
    override fun View(modifier: Modifier)
}