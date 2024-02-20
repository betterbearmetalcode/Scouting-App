package pages

import RootNode
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import defaultBackground
import defaultOnBackground
import defaultPrimaryVariant
import setTeam

class AutoTeleSelectorMenu(
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
) : ParentNode<AutoTeleSelectorMenu.NavTarget>(
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
            NavTarget.AutoScouting -> AutoMenu(buildContext, backStack, mainMenuBackStack, selectAuto, match, team, robotStartPosition)
            NavTarget.TeleScouting -> TeleMenu(buildContext, backStack, selectAuto, match, team, robotStartPosition)
        }

    @Composable
    override fun View(modifier: Modifier) {
        setTeam(team, match, robotStartPosition.value)
        var pageName by remember { mutableStateOf("Auto") }
        var positionName by remember { mutableStateOf("") }

        when (robotStartPosition.value){
            0 -> {positionName = "R1"}
            1 -> {positionName = "R2"}
            2 -> {positionName = "R3"}
            3 -> {positionName = "B1"}
            4 -> {positionName = "B2"}
            5 -> {positionName = "B3"}
        }
        pageName = if (!selectAuto.value) {
            "Auto"
        } else {
            "Tele"
        }


        Column {
            Divider(color = defaultPrimaryVariant, thickness = 4.dp)


            Row(Modifier.align(Alignment.CenterHorizontally).height(IntrinsicSize.Min)) {
                Text(
                    text = positionName,
                    modifier = Modifier.scale(1.2f).align(Alignment.CenterVertically).padding(horizontal = 35.dp),
                    fontSize = 30.sp
                )

                Divider(
                    color = defaultPrimaryVariant,
                    modifier = Modifier
                        .fillMaxHeight()  //fill the max height
                        .width(3.dp),
                )

                Text(
                    text = "${team.value}",
                    modifier = Modifier.align(Alignment.CenterVertically).padding(horizontal = 35.dp),
                    fontSize = 30.sp
                )

                Divider(
                    color = defaultPrimaryVariant,
                    modifier = Modifier
                        .fillMaxHeight()  //fill the max height
                        .width(3.dp),
                )

                Text(
                    text = "Match",
                    modifier = Modifier.align(Alignment.CenterVertically).padding(start = 35.dp),
                    fontSize = 30.sp
                )

                TextField(
                    value = match.value,
                    onValueChange = { value ->
                        match.value = value.filter { it.isDigit() }
                        setTeam(team, match, robotStartPosition.value)
                    },
                    modifier = Modifier.fillMaxWidth(1f/4f),
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = defaultBackground),
                    singleLine = true,
                    textStyle = TextStyle.Default.copy(fontSize = 30.sp)
                )

            }

            Divider(color = defaultPrimaryVariant, thickness = 3.dp)

            Box(modifier = Modifier.fillMaxWidth()) {

                Text(
                    text = pageName,
                    fontSize = 30.sp,
                    modifier = Modifier.align(Alignment.CenterStart).offset(x = 15.dp)
                )

                Row(Modifier.align(Alignment.CenterEnd).offset(x = (-15).dp)) {

                    Text("A", fontSize = 25.sp, modifier = Modifier.align(Alignment.CenterVertically))

                    Switch(
                        checked = selectAuto.value,
                        onCheckedChange = {
                            selectAuto.value = it
                            if (!selectAuto.value) {
                                backStack.pop()
                            } else
                                backStack.push(NavTarget.TeleScouting)
                        },
                        colors = SwitchDefaults.colors(
                            uncheckedTrackColor = Color(50, 50, 50),
                            uncheckedThumbColor = defaultOnBackground,
                            uncheckedTrackAlpha = 1f,
                            checkedTrackColor = Color(50, 50, 50),
                            checkedThumbColor = defaultOnBackground,
                            checkedTrackAlpha = 1f
                        )
                    )

                    Text("T", fontSize = 25.sp, modifier = Modifier.align(Alignment.CenterVertically))
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

val match = mutableStateOf("1")
var left = mutableStateOf(false)
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
val selectedEndPos = mutableStateOf("None")
var autoNotes = mutableStateOf("")
var teleNotes = mutableStateOf("")

fun createOutput(team: MutableIntState, robotStartPosition: MutableIntState): String {
    val teleNotesFinal = if (teleNotes.value == "") "No Comments" else teleNotes.value
    val autoNotesFinal = if (autoNotes.value == "") "No Comments" else autoNotes.value
    return match.value + "/" +
            team.value + "/" + robotStartPosition.value + "/" +
            (if (left.value) 1 else 0) + "/" + autoSpeakerNum.value + "/" +
            autoAmpNum.value + "/" + collected.value + "/" +
            autoSMissed.value + "/" + autoAMissed.value + "/" +
            teleSpeakerNum.value + "/" + teleAmpNum.value + "/" +
            teleTrapNum.value + "/" + teleSMissed.value + "/" +
            teleAMissed.value + "/" + when (selectedEndPos.value) {
        "None" -> 0; "Parked" -> 1; "Climbed" -> 2; "Harmony" -> 3; else -> 0
    } + "/" +
            autoNotesFinal + "/" + teleNotesFinal

}