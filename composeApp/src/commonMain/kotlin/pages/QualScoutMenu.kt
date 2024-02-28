package pages

import RootNode
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import org.jetbrains.compose.resources.ExperimentalResourceApi

expect class QualScoutMenu(
    buildContext: BuildContext,
    backStack: BackStack<RootNode.NavTarget>
) : Node {

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun View(modifier: Modifier)
}