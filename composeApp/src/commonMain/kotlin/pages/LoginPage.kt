package pages

import nodes.RootNode
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.operation.push
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import defaultBackground
import defaultError
import defaultOnPrimary
import defaultPrimaryVariant
import getCurrentTheme
import nodes.loadData
import nodes.matchScoutArray
import nodes.reset
import java.io.File

expect class LoginPage (
    buildContext: BuildContext,
    backStack: BackStack<RootNode.NavTarget>,
    scoutName: MutableState<String>,
    comp:  MutableState<String>,
) : Node {
    @Composable
    override fun View(modifier: Modifier)

}
