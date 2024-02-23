package pages

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node

class PitsScoutMenu(
    buildContext: BuildContext
) : Node(
    buildContext = buildContext
) {
    @Composable
    override fun View(modifier: Modifier) {

    }
import RootNode
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import java.awt.image.BufferedImage


expect class PitsScoutMenu(
    buildContext: BuildContext,
    backStack: BackStack<RootNode.NavTarget>,
    pitsPerson: MutableState<String>,
    ampStrength: MutableState<Boolean>,
    speakerStrength: MutableState<Boolean>,
    climbStrength: MutableState<Boolean>,
    trapStrength: MutableState<Boolean>,
    scoutName: MutableState<String>
) : Node {
    @Composable
    override fun View(modifier: Modifier)

}