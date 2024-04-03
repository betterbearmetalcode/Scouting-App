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
    private val ampStrength = mutableStateOf(false)
    private val speakerStrength = mutableStateOf(false)
    private val climbStrength = mutableStateOf(false)
    private val trapStrength = mutableStateOf(false)

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
            NavTarget.PitsScouting -> PitsScoutMenu(buildContext,backStack,pitsPerson,ampStrength,speakerStrength,trapStrength,climbStrength,scoutName)
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
        f1.value = intToState(parseInt(help[7]))
        f2.value = intToState(parseInt(help[8]))
        f3.value = intToState(parseInt(help[9]))
        m1.value = intToState(parseInt(help[10]))
        m2.value = intToState(parseInt(help[11]))
        m3.value = intToState(parseInt(help[12]))
        m4.value = intToState(parseInt(help[13]))
        m5.value = intToState(parseInt(help[14]))
        teleSpeakerNum.intValue = parseInt(help[15])
        teleAmpNum.intValue = parseInt(help[16])
        teleTrapNum.intValue = parseInt(help[17])
        teleSMissed.intValue = parseInt(help[18])
        teleAMissed.intValue = parseInt(help[19])
        lostComms.intValue = parseInt(help[20])
        teleNotes.value = help[21]
    //reset()
    }
}

fun reset(){
    autoSpeakerNum.value = 0
    autoAmpNum.value = 0
    collected.value = 0
    autoSMissed.value = 0
    autoAMissed.value = 0
    lostComms.value = 0
    teleSpeakerNum.value = 0
    teleAmpNum.value = 0
    teleTrapNum.value = 0
    teleSMissed.value = 0
    teleAMissed.value = 0
    m1.value = ToggleableState.Off
    m2.value = ToggleableState.Off
    m3.value = ToggleableState.Off
    m4.value = ToggleableState.Off
    m5.value = ToggleableState.Off
    f1.value = ToggleableState.Off
    f2.value = ToggleableState.Off
    f3.value = ToggleableState.Off
}
