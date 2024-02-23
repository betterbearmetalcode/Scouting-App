package pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import com.bumble.appyx.components.backstack.BackStack
import nodes.AutoTeleSelectorNode

@Composable
expect fun AutoTeleSelectorMenu(
    team: MutableIntState,
    robotStartPosition: MutableIntState,
    selectAuto: MutableState<Boolean>,
    backStack: BackStack<AutoTeleSelectorNode.NavTarget>
)