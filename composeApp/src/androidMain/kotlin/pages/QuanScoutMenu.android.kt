package pages

import RootNode
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node

actual class QuanScoutMenu actual constructor(
    buildContext: BuildContext,
    private val backStack: BackStack<RootNode.NavTarget>
) : Node(buildContext) {
    @Composable
    actual override fun View(modifier: Modifier) {
        var match by remember { mutableStateOf("")}
        Column(modifier) {

            Row {

                Text(
                    text = "Team"
                )
            }
            Row {
                Text(
                    text = "Auto"
                )
            }
        }
    }

}