package pages

import nodes.RootNode
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import nodes.AutoTeleSelectorNode

expect class AutoMenu (
    buildContext: BuildContext,
    backStack: BackStack<AutoTeleSelectorNode.NavTarget>,
    mainMenuBackStack: BackStack<RootNode.NavTarget>,
    selectAuto: MutableState<Boolean>,
    match: MutableState<String>,
    team: MutableIntState,
    robotStartPosition: MutableIntState
) : Node {
    @Composable
    override fun View(modifier: Modifier)
}
