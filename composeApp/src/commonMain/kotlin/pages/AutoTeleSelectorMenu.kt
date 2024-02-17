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

    private val match = mutableStateOf("1")
    private var left = mutableStateOf(false)
    private val autoSpeakerNum = mutableIntStateOf(0)
    private val autoAmpNum = mutableIntStateOf(0)
    private val autoCollected = mutableIntStateOf(0)
    private val autoSMissed = mutableIntStateOf(0)
    private val autoAMissed = mutableIntStateOf(0)
    private val teleSpeakerNum  =  mutableIntStateOf(0)
    private val teleAmpNum  = mutableIntStateOf(0)
    private val teleTrapNum = mutableIntStateOf(0)
    private val teleSMissed = mutableIntStateOf(0)
    private val teleAMissed = mutableIntStateOf(0)
    private val selectedEndPos = mutableStateOf("None")
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
            NavTarget.AutoScouting -> AutoMenu(buildContext, mainMenuBackStack, left, autoSpeakerNum, autoAmpNum, autoCollected, autoSMissed, autoAMissed, autoNotes)
            NavTarget.TeleScouting -> TeleMenu(buildContext, backStack, mainMenuBackStack, selectAuto, match, team, robotStartPosition, left, autoSpeakerNum, autoAmpNum, autoCollected, autoSMissed, autoAMissed, autoNotes, teleSpeakerNum, teleAmpNum, teleTrapNum, teleSMissed, teleAMissed, selectedEndPos, teleNotes, lostComms)

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
                            uncheckedTrackColor = defaultOnBackground,
                            uncheckedThumbColor = defaultBackground,
                            uncheckedTrackAlpha = 1f,
                            checkedTrackColor = defaultOnBackground,
                            checkedThumbColor = defaultBackground,
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