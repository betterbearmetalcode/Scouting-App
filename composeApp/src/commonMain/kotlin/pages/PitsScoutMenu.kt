package pages

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node

expect class PitsScoutMenu(
    buildContext: BuildContext
) : Node {
    @Composable
    override fun View(modifier: Modifier)

}