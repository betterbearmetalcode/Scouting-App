package pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.BackStackModel
import com.bumble.appyx.components.backstack.operation.pop
import com.bumble.appyx.components.backstack.operation.push
import com.bumble.appyx.components.backstack.ui.fader.BackStackFader
import com.bumble.appyx.navigation.composable.AppyxComponent
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import com.bumble.appyx.navigation.node.ParentNode
import com.bumble.appyx.utils.multiplatform.Parcelable
import com.bumble.appyx.utils.multiplatform.Parcelize

expect class AutoTeleSelectorMenu(
    buildContext: BuildContext,
    robotStartPosition: MutableIntState,
    match: MutableState<String> ,
    team: MutableIntState,
    backStack: BackStack<NavTarget> = BackStack(
        model = BackStackModel(
            initialTarget = NavTarget.AutoScouting,
            savedStateMap = buildContext.savedStateMap
        ),
        visualisation = { BackStackFader(it) }
    )



) : ParentNode<AutoTeleSelectorMenu.NavTarget> {

    sealed class NavTarget : Parcelable {
        @Parcelize
        data object AutoScouting : NavTarget

        @Parcelize
        data object TeleScouting : NavTarget
    }

    override fun resolve(interactionTarget: NavTarget, buildContext: BuildContext): Node

    @Composable
    override fun View(modifier: Modifier)
}