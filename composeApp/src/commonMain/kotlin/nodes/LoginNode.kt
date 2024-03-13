package nodes

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
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
