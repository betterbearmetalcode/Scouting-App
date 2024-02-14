package pages

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node

actual class PitsScoutMenu actual constructor(
    buildContext: BuildContext,
    backStack: BackStack<RootNode.NavTarget>
) : Node(buildContext = buildContext)
{
    @Composable
    actual override fun View(modifier: Modifier) {
    }
}