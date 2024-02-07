package pages

import androidx.compose.foundation.layout.*
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

class AutoTeleSelectorMenu(
    buildContext: BuildContext,
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

    var match = mutableStateOf("")
    var team = mutableStateOf("")
    var allianceColor = mutableStateOf(false)
    val autoSpeakerNum = mutableIntStateOf(0)
    val autoAmpNum = mutableIntStateOf(0)
    val teleSpeakerNum  =  mutableIntStateOf(0)
    val teleAmpNum  = mutableIntStateOf(0)
    val teleAmplified =  mutableIntStateOf(0)
    val teleTrapNum = mutableIntStateOf(0)
    var selectedEndPos = mutableStateOf("None")
    val lostComms = mutableStateOf(false)
    var autoNotes = mutableStateOf("")
    var teleNotes = mutableStateOf("")

    sealed class NavTarget : Parcelable {
        @Parcelize
        data object AutoScouting : NavTarget()

        @Parcelize
        data object TeleScouting : NavTarget()
    }

    override fun resolve(interactionTarget: NavTarget, buildContext: BuildContext): Node =
        when (interactionTarget) {
            NavTarget.AutoScouting -> AutoMenu(buildContext, backStack, match, team, allianceColor, autoSpeakerNum, autoAmpNum, autoNotes)
            NavTarget.TeleScouting -> TeleMenu(buildContext, backStack, match, team, allianceColor, autoSpeakerNum, autoAmpNum, autoNotes, teleSpeakerNum, teleAmpNum, teleAmplified, teleTrapNum, selectedEndPos, teleNotes, lostComms)

        }

    @Composable
    override fun View(modifier: Modifier) {
        var selectAuto by remember { mutableStateOf(false) }
        var pageName by remember { mutableStateOf("Auto") }
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
                    onValueChange = { match.value = it },
                    modifier = Modifier.fillMaxWidth(1f/4f)
                )
                Text(
                    text = "Team" + ""//blue alliance
                )
                TextField(
                    value = team.value,
                    onValueChange = { team.value = it },
                    modifier = Modifier.fillMaxWidth(1f/2f)
                )
                Switch(
                    checked = allianceColor.value,
                    onCheckedChange = { allianceColor.value = it },
                    colors = SwitchDefaults.colors(
                        uncheckedThumbColor = Color.Blue,
                        uncheckedTrackColor = Color(38, 95, 240),
                        checkedThumbColor = Color.Red,
                        checkedTrackColor = Color(252,40,62)
                    ),
                    modifier = Modifier
                        .scale(1.5f)
                        .padding(15.dp)
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