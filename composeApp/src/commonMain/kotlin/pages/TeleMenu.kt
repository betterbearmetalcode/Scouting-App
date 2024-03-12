package pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import com.bumble.appyx.components.backstack.BackStack
import nodes.AutoTeleSelectorNode


@Composable
expect fun TeleMenu(
    backStack: BackStack<AutoTeleSelectorNode.NavTarget>,

    selectAuto: MutableState<Boolean>,
    lostComms: MutableState<Boolean>,

    match: MutableState<String>,
    team: MutableIntState,
    robotStartPosition: MutableIntState
)