package pages

import RootNode
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node

expect class PitsScoutMenu(
    buildContext: BuildContext,
    backStack: BackStack<RootNode.NavTarget>,
    pitsPerson: MutableState<String>
) : Node {
    @Composable
    override fun View(modifier: Modifier)
}