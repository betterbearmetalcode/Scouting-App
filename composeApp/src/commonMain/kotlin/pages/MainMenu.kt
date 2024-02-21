package pages

import nodes.RootNode
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import org.jetbrains.compose.resources.ExperimentalResourceApi

expect class MainMenu(
    buildContext: BuildContext,
    backStack: BackStack<RootNode.NavTarget>,
    robotStartPosition: MutableIntState
) : Node {

    @Composable
    override fun View(modifier: Modifier)
}
