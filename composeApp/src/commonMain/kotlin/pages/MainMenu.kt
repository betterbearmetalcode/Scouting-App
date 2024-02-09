package pages

import RootNode
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.operation.push
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import defaultSecondary
import getCurrentTheme

expect class MainMenu(
    buildContext: BuildContext,
    backStack: BackStack<RootNode.NavTarget>,
    robotStartPosition: MutableIntState,
    match: MutableState<String>,
    teamNum: MutableIntState
) : Node {

    @Composable
    override fun View(modifier: Modifier)
}