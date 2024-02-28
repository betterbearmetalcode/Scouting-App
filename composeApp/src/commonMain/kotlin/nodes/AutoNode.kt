package nodes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import pages.AutoMenu

class AutoNode(
    buildContext: BuildContext,
    private val backStack: BackStack<AutoTeleSelectorNode.NavTarget>,
    private val mainMenuBackStack: BackStack<RootNode.NavTarget>,

    private val selectAuto: MutableState<Boolean>,

    private val match: MutableState<String>,
    private val team: MutableIntState,
    private val robotStartPosition: MutableIntState,
) : Node(buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        AutoMenu(
            backStack,
            mainMenuBackStack,

            selectAuto,

            match,
            team,
            robotStartPosition
        )
    }
}