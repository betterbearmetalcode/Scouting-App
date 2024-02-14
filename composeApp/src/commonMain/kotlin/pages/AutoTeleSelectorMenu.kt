package pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
import setTeam

class AutoTeleSelectorMenu(
    buildContext: BuildContext,
    private var robotStartPosition: MutableIntState,
    private val team: MutableIntState,
    private val backStack: BackStack<NavTarget> = BackStack(
        model = BackStackModel(
            initialTarget = NavTarget.AutoScouting,
            savedStateMap = buildContext.savedStateMap
        ),
        visualisation = { BackStackFader(it) }
    )
) : ParentNode<AutoTeleSelectorMenu.NavTarget>(
    appyxComponent = backStack,
    buildContext = buildContext
) {

    private var match = mutableStateOf("")
    private val autoSpeakerNum = mutableIntStateOf(0)
    private val autoAmpNum = mutableIntStateOf(0)
    private val teleSpeakerNum  =  mutableIntStateOf(0)
    private val teleAmpNum  = mutableIntStateOf(0)
    private val teleAmplified =  mutableIntStateOf(0)
    private val teleTrapNum = mutableIntStateOf(0)
    private var selectedEndPos = mutableStateOf("None")
    private val lostComms = mutableStateOf(false)
    private var autoNotes = mutableStateOf("")
    private var teleNotes = mutableStateOf("")

    sealed class NavTarget : Parcelable {
        @Parcelize
        data object AutoScouting : NavTarget()

        @Parcelize
        data object TeleScouting : NavTarget()
    }

    override fun resolve(interactionTarget: NavTarget, buildContext: BuildContext): Node =
        when (interactionTarget) {
            NavTarget.AutoScouting -> AutoMenu(buildContext, autoSpeakerNum, autoAmpNum, autoNotes)
            NavTarget.TeleScouting -> TeleMenu(buildContext, backStack, match, team, robotStartPosition, autoSpeakerNum, autoAmpNum, autoNotes, teleSpeakerNum, teleAmpNum, teleAmplified, teleTrapNum, selectedEndPos, teleNotes, lostComms)

        }

    @Composable
    override fun View(modifier: Modifier) {
        var selectAuto by remember { mutableStateOf(false) }
        var selectedPlacement by remember { mutableStateOf(false) }
        var pageName by remember { mutableStateOf("Auto") }
        var positionName by remember { mutableStateOf("") }

        when (robotStartPosition.value){
            0 -> {positionName = "Red 1"}
            1 -> {positionName = "Red 2"}
            2 -> {positionName = "Red 3"}
            3 -> {positionName = "Blue 1"}
            4 -> {positionName = "Blue 2"}
            5 -> {positionName = "Blue 3"}
        }
        pageName = if (!selectAuto) {
            "Auto"
        } else {
            "Tele"
        }


        Column {
            Row {
                Text(
                    text = "Match" + ""//blue alliance
                )
                TextField(
                    value = match.value,
                    onValueChange = {
                        match.value = it
                        setTeam(team, match, robotStartPosition.value)
                    },
                    modifier = Modifier.fillMaxWidth(1f/4f)
                )
                Text(
                    text = "Team" + team.value
                )
            }
            Row {
                Switch(
                    checked = selectAuto,
                    onCheckedChange = {
                        selectAuto = it
                        if (!selectAuto) {
                            backStack.pop()
                        } else
                            backStack.push(NavTarget.TeleScouting)
                    },
                    colors = SwitchDefaults.colors(
                        uncheckedTrackColor = Color.Black,
                        checkedTrackColor = Color.Yellow
                    )
                )
                Text(
                    text = pageName,
                    modifier = Modifier.scale(1.25f)
                )
                Button(
                    onClick = {selectedPlacement = true}
                ){
                    Text("Position:  $positionName" )
                }

            }
            Divider(
                color = Color.Yellow,
                thickness = 2.dp
            )
            AppyxComponent(
                appyxComponent = backStack,
                modifier = Modifier.weight(0.9f)
            )



        }
    }

}