package nodes

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.BackStackModel
import com.bumble.appyx.components.backstack.ui.fader.BackStackFader
import com.bumble.appyx.navigation.composable.AppyxComponent
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import com.bumble.appyx.navigation.node.ParentNode
import com.bumble.appyx.utils.multiplatform.Parcelable
import com.bumble.appyx.utils.multiplatform.Parcelize
import pages.AutoMenu
import pages.AutoTeleSelectorMenu
import pages.TeleMenu

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
    private val teleAmplified = mutableIntStateOf(0)
    private val selectedEndPos = mutableStateOf(1)
    private val lostComms = mutableStateOf(false)

    sealed class NavTarget : Parcelable {
        @Parcelize
        data object AutoScouting : NavTarget()

        @Parcelize
        data object TeleScouting : NavTarget()
    }

    override fun resolve(interactionTarget: NavTarget, buildContext: BuildContext): Node =
        when (interactionTarget) {
            NavTarget.AutoScouting -> AutoMenu(buildContext, backStack, mainMenuBackStack, selectAuto, match, team, robotStartPosition)
            NavTarget.TeleScouting -> TeleMenu(
                buildContext, backStack, selectAuto, match, team, robotStartPosition, autoAmpNum, autoSpeakerNum, autoNotes, teleSpeakerNum, teleAmplified, teleTrapNum, selectedEndPos, teleNotes, lostComms)
        }

    @Composable
    override fun View(modifier: Modifier) {

        Column() {
            AutoTeleSelectorMenu(buildContext,robotStartPosition,team,mainMenuBackStack,backStack)//TODO get buildContext working... My BRaIn DiEd
            AppyxComponent(
                appyxComponent = backStack,
                modifier = Modifier.weight(0.9f)
            )
        }
    }
}


val f1 = mutableIntStateOf(0)
val f2 = mutableIntStateOf(0)
val f3 = mutableIntStateOf(0)
val m1 = mutableIntStateOf(0)
val m2 = mutableIntStateOf(0)
val m3 = mutableIntStateOf(0)
val m4 = mutableIntStateOf(0)
val m5 = mutableIntStateOf(0)

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
var autoNotes = mutableStateOf("")
var teleNotes = mutableStateOf("")

fun createOutput(team: MutableIntState, robotStartPosition: MutableIntState): String {
    val teleNotesFinal = if (teleNotes.value == "") "No Comments" else teleNotes.value
    val autoNotesFinal = if (autoNotes.value == "") "No Comments" else autoNotes.value
    return match.value + "/" + team.value + "/" +
            robotStartPosition.value + "/" + autoSpeakerNum.value + "/" +
            autoAmpNum.value + "/" + autoSMissed.value + "/" +
            autoAMissed.value + "/" + f1.value + "/" +
            f2.value + "/" + f3.value + "/" +
            m1.value + "/" + m2.value + "/" +
            m3.value + "/" + m4.value + "/" +
            m5.value + "/" + teleSpeakerNum.value + "/" +
            teleAmpNum.value + "/" + teleTrapNum.value + "/" +
            teleSMissed.value + "/" + teleAMissed.value + "/" +
            autoNotesFinal + "/" + teleNotesFinal

}