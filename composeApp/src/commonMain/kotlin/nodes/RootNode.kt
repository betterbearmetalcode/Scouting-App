package nodes

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Modifier
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.BackStackModel
import androidx.compose.runtime.*
import androidx.compose.ui.state.ToggleableState
import com.bumble.appyx.components.backstack.ui.fader.BackStackFader
import com.bumble.appyx.navigation.composable.AppyxComponent
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import com.bumble.appyx.navigation.node.ParentNode
import com.bumble.appyx.utils.multiplatform.Parcelable
import com.bumble.appyx.utils.multiplatform.Parcelize
import pages.*
import java.lang.Integer.parseInt


class RootNode(
    buildContext: BuildContext,
    private val backStack: BackStack<NavTarget> = BackStack(
        model = BackStackModel(
            initialTarget = NavTarget.LoginPage,
            savedStateMap = buildContext.savedStateMap
        ),
        visualisation = { BackStackFader(it) }
    )
) : ParentNode<RootNode.NavTarget>(
    appyxComponent = backStack,
    buildContext = buildContext
){
    private var team = mutableIntStateOf(1)
    private var robotStartPosition = mutableIntStateOf(0)
    private var pitsPerson = mutableStateOf("P1")
    private var comp =  mutableStateOf("")


    sealed class NavTarget : Parcelable {
        @Parcelize
        data object MainMenu : NavTarget()

        @Parcelize
        data object MatchScouting : NavTarget()

        @Parcelize
        data object PitsScouting : NavTarget()

        @Parcelize
        data object LoginPage : NavTarget()
    }

    override fun resolve(interactionTarget: NavTarget, buildContext: BuildContext): Node =
        when (interactionTarget) {
            NavTarget.LoginPage -> LoginNode(buildContext, backStack, scoutName, comp)
            NavTarget.MainMenu -> MainMenu(buildContext, backStack, robotStartPosition,scoutName, comp, team)
            NavTarget.MatchScouting -> AutoTeleSelectorNode(buildContext,robotStartPosition, team, backStack)
            NavTarget.PitsScouting -> PitsScoutMenu(buildContext,backStack,pitsPerson,scoutName)
        }

    @Composable
    override fun View(modifier: Modifier) {

        Column {

            AppyxComponent(
                appyxComponent = backStack,
                modifier = Modifier.weight(0.9f)
            )

        }

    }
}

var scoutName =  mutableStateOf("")
val matchScoutArray = HashMap<Int, HashMap<Int, String>>()


fun loadData(match: Int, team: MutableIntState, robotStartPosition: MutableIntState){
    reset()
    if(matchScoutArray[robotStartPosition.intValue]?.get(match)?.isEmpty() == false) {
        fun intToState(i: Int) = when (i) {
            0 -> ToggleableState.Off
            1 -> ToggleableState.Indeterminate
            2 -> ToggleableState.On
            else -> ToggleableState.Off
        }

        val help = matchScoutArray[robotStartPosition.intValue]?.get(match)?.split('/') ?: createOutput(team, robotStartPosition).split('/')
        team.intValue = parseInt(help[1])

        autoSpeakerNum.intValue = parseInt(help[3])
        autoAmpNum.intValue = parseInt(help[4])
        autoSMissed.intValue = parseInt(help[5])
        autoAMissed.intValue = parseInt(help[6])
        autoStop.intValue = parseInt(help[7])
        telePassed.intValue = parseInt(help[8])
        teleSpeakerNum.intValue = parseInt(help[9])
        teleAmpNum.intValue = parseInt(help[10])
        teleTrapNum.intValue = parseInt(help[11])
        teleSMissed.intValue = parseInt(help[12])
        teleAMissed.intValue = parseInt(help[13])
        lostComms.intValue = parseInt(help[14])

        val teleCommentsSplit = help[15].split(':')
        autos.value = teleCommentsSplit[0]
        teleNotes.value = teleCommentsSplit[1]
        scoutName.value = teleCommentsSplit[2]
        println(autos)
    }
}

fun reset(){
    autoSpeakerNum.value = 0
    autoAmpNum.value = 0
    collected.value = 0
    autoSMissed.value = 0
    autoAMissed.value = 0
    autos.value = ""
    lostComms.value = 0
    teleSpeakerNum.value = 0
    teleAmpNum.value = 0
    teleTrapNum.value = 0
    teleSMissed.value = 0
    teleAMissed.value = 0
    autoStop.intValue = 0
    telePassed.intValue = 0
    teleNotes.value = ""
}
