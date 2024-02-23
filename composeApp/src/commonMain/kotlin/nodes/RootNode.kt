package nodes

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Modifier
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.BackStackModel
import androidx.compose.runtime.*
import com.bumble.appyx.components.backstack.ui.fader.BackStackFader
import com.bumble.appyx.navigation.composable.AppyxComponent
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.ParentNode
import com.bumble.appyx.utils.multiplatform.Parcelable
import com.bumble.appyx.utils.multiplatform.Parcelize
import pages.*
import com.bumble.appyx.navigation.node.Node

class RootNode(
    buildContext: BuildContext,
    private val mainMenuBackStack: BackStack<RootNode.NavTarget>,
    private val matchBackStack: BackStack<AutoTeleSelectorMenu.NavTarget>,
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
    private var robotStartPosition = mutableIntStateOf(-1)
    private var pitsPerson = mutableStateOf("P1")
    private var scoutName =  mutableStateOf("")
    private var comp =  mutableStateOf("")
    private val ampStrength = mutableStateOf(false)
    private val speakerStrength = mutableStateOf(false)
    private val climbStrength = mutableStateOf(false)
    private val trapStrength = mutableStateOf(false)
    private val selectAuto = mutableStateOf(false)
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

    override fun resolve(interactionTarget: NavTarget, buildContext: BuildContext): Node =//NOTE: This was the only thing I changed for the most part after the merge. I did change AutoTeleSelector Node, but it can be reverted easily
        when (interactionTarget) {
            NavTarget.LoginPage -> LoginPage(buildContext,backStack, scoutName,comp)
            NavTarget.MainMenu -> MainMenu(buildContext, backStack, robotStartPosition,scoutName,comp)
            NavTarget.MatchScouting -> AutoTeleSelectorMenu(buildContext,robotStartPosition, team, mainMenuBackStack, matchBackStack)
            NavTarget.PitsScouting -> PitsScoutMenu(buildContext,backStack,pitsPerson, ampStrength,speakerStrength, climbStrength, trapStrength,scoutName)
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
val matchScoutArray = HashMap<Int, String>()