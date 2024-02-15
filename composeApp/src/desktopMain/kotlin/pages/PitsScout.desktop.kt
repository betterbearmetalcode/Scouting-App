package pages

import RootNode
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

        var notes by remember { mutableStateOf("") }
        var teamName by remember { mutableStateOf("") }
        var teamNumber by remember { mutableStateOf("") }

        Column {  }
    }
}