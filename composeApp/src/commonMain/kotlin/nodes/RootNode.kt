package nodes

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Modifier
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.BackStackModel
import androidx.compose.runtime.*
import com.bumble.appyx.components.backstack.operation.pop
import com.bumble.appyx.components.backstack.ui.fader.BackStackFader
import com.bumble.appyx.navigation.composable.AppyxComponent
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import com.bumble.appyx.navigation.node.ParentNode
import com.bumble.appyx.utils.multiplatform.Parcelable
import com.bumble.appyx.utils.multiplatform.Parcelize
import pages.*


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
    private var robotStartPosition = mutableIntStateOf(-1)
    private var pitsPerson = mutableStateOf("P1")
    private var scoutName =  mutableStateOf("")
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
            NavTarget.LoginPage -> LoginPage(buildContext,backStack, scoutName,comp)
            NavTarget.MainMenu -> MainMenu(buildContext, backStack, robotStartPosition,scoutName,comp)
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
val matchScoutArray = HashMap<Int, String>()

fun loadData(match: Int, team: MutableIntState/* robotStartPosition: MutableIntState*/){
    reset()
    if(matchScoutArray[match]?.isEmpty() == false) {
        val help = matchScoutArray[match].toString().split('/')
        autoSpeakerNum.value = Integer.parseInt(help[3])
        autoAmpNum.value = Integer.parseInt(help[4])
        autoSMissed.value = Integer.parseInt(help[5])
        autoAMissed.value = Integer.parseInt(help[6])
        f1.value = Integer.parseInt(help[7])
        f2.value = Integer.parseInt(help[8])
        f3.value = Integer.parseInt(help[9])
        m1.value = Integer.parseInt(help[10])
        m2.value = Integer.parseInt(help[11])
        m3.value = Integer.parseInt(help[12])
        m4.value = Integer.parseInt(help[13])
        m5.value = Integer.parseInt(help[14])
        teleSpeakerNum.value = Integer.parseInt(help[15])
        teleAmpNum.value = Integer.parseInt(help[16])
        teleTrapNum.value = Integer.parseInt(help[17])
        teleSMissed.value = Integer.parseInt(help[18])
        teleAMissed.value = Integer.parseInt(help[19])
        autoNotes = mutableStateOf(help[20])
        teleNotes = mutableStateOf(help[21])
        println("$autoNotes \n $autoSpeakerNum \n $autoSMissed  \n  $autoAMissed \n $f1 \n $f2 \n $f3 \n $m1 \n $m2 \n $m3 \n $m4 \n $m5 \n $teleSpeakerNum \n $autoNotes ")
        println(matchScoutArray[match].toString())
        //reset()
    }
}
fun reset(){
    autoSpeakerNum.value = 0
    autoAmpNum.value = 0
    collected.value = 0
    autoSMissed.value = 0
    autoAMissed.value = 0
    autoNotes.value = ""
    teleSpeakerNum.value = 0
    teleAmpNum.value = 0
    teleTrapNum.value = 0
    teleSMissed.value = 0
    teleAMissed.value = 0
    m1.intValue = 0
    m2.intValue = 0
    m3.intValue = 0
    m4.intValue = 0
    m5.intValue = 0
    f1.intValue = 0
    f2.intValue = 0
    f3.intValue = 0
}