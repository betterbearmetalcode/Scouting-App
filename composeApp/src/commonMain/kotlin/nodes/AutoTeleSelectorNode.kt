package nodes

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.BackStackModel
import com.bumble.appyx.components.backstack.ui.fader.BackStackFader
import com.bumble.appyx.navigation.composable.AppyxComponent
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import com.bumble.appyx.navigation.node.ParentNode
import com.bumble.appyx.utils.multiplatform.Parcelable
import com.bumble.appyx.utils.multiplatform.Parcelize
import pages.AutoTeleSelectorMenu

class AutoTeleSelectorNode(
    buildContext: BuildContext,
    private var robotStartPosition: MutableIntState,
    private val team: MutableIntState,
    private val mainMenuBackStack: BackStack<RootNode.NavTarget>,
    private val backStack: BackStack<NavTarget> = BackStack(
        model = BackStackModel(
            initialTarget = NavTarget.AutoScouting,
            savedStateMap = buildContext.savedStateMap
        ),
        visualisation = { BackStackFader(it) }
    )
) : ParentNode<AutoTeleSelectorNode.NavTarget>(
    appyxComponent = backStack,
    buildContext = buildContext
) {
    private val selectAuto = mutableStateOf(false)


    sealed class NavTarget : Parcelable {
        @Parcelize
        data object AutoScouting : NavTarget()

        @Parcelize
        data object TeleScouting : NavTarget()
    }

    override fun resolve(interactionTarget: NavTarget, buildContext: BuildContext): Node =
        when (interactionTarget) {
            NavTarget.AutoScouting -> AutoNode(buildContext, backStack, mainMenuBackStack, selectAuto, match, team, robotStartPosition)
            NavTarget.TeleScouting -> TeleNode(buildContext, backStack, mainMenuBackStack, selectAuto, match, team, robotStartPosition)
        }

    @Composable
    override fun View(modifier: Modifier) {
        Column {
            AutoTeleSelectorMenu(match, team, robotStartPosition, selectAuto, backStack, mainMenuBackStack)
            AppyxComponent(
                appyxComponent = backStack,
                modifier = Modifier.weight(0.9f)
            )
        }
    }
}


val f1 = mutableStateOf(ToggleableState(false))
val f2 = mutableStateOf(ToggleableState(false))
val f3 = mutableStateOf(ToggleableState(false))
val m1 = mutableStateOf(ToggleableState(false))
val m2 = mutableStateOf(ToggleableState(false))
val m3 = mutableStateOf(ToggleableState(false))
val m4 = mutableStateOf(ToggleableState(false))
val m5 = mutableStateOf(ToggleableState(false))

val match = mutableStateOf("1")
val autoSpeakerNum = mutableIntStateOf(0)
val autoAmpNum = mutableIntStateOf(0)
val collected = mutableIntStateOf(0)
val autoSMissed = mutableIntStateOf(0)
val autoAMissed = mutableIntStateOf(0)
val teleSpeakerNum  =  mutableIntStateOf(0)
val teleAmpNum  = mutableIntStateOf(0)
val teleTrapNum = mutableIntStateOf(0)
val teleSMissed = mutableIntStateOf(0)
val teleAMissed = mutableIntStateOf(0)
var lostComms = mutableIntStateOf(0)
var teleNotes = mutableStateOf("")

fun createOutput(team: MutableIntState, robotStartPosition: MutableIntState): String {
    fun stateToInt(state: ToggleableState) = when (state) {
        ToggleableState.Off -> 0
        ToggleableState.Indeterminate -> 1
        ToggleableState.On -> 2
    }

    val teleNotesFinal = if (teleNotes.value == "") "No Comments" else teleNotes.value
    return match.value + "/" + team.value + "/" +
            robotStartPosition.value + "/" + autoSpeakerNum.value + "/" +
            autoAmpNum.value + "/" + autoSMissed.value + "/" +
            autoAMissed.value + "/" + stateToInt(f1.value) + "/" +
            stateToInt(f2.value) + "/" + stateToInt(f3.value) + "/" +
            stateToInt(m1.value) + "/" + stateToInt(m2.value) + "/" +
            stateToInt(m3.value) + "/" + stateToInt(m4.value) + "/" +
            stateToInt(m5.value) + "/" + teleSpeakerNum.value + "/" +
            teleAmpNum.value + "/" + teleTrapNum.value + "/" +
            teleSMissed.value + "/" + teleAMissed.value + "/" +
            lostComms.value + "/" + scoutName + teleNotesFinal
}