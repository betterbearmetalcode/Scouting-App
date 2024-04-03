package nodes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import pages.LoginMenu

class LoginNode(
    buildContext: BuildContext,
    private val backStack: BackStack<RootNode.NavTarget>,
    private val scoutName: MutableState<String>,
    private val comp:  MutableState<String>,
) : Node(buildContext) {
    @Composable
    override fun View(modifier: Modifier) {
        LoginMenu(backStack, scoutName, comp)
    }
}
