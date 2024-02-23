//package pages
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.scale
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.bumble.appyx.components.backstack.BackStack
//import com.bumble.appyx.components.backstack.operation.pop
//import com.bumble.appyx.components.backstack.operation.push
//import com.bumble.appyx.navigation.modality.BuildContext
//import com.bumble.appyx.navigation.node.Node
//import defaultBackground
//import defaultOnBackground
//import defaultOnPrimary
//import defaultPrimaryVariant
//import nodes.*
//import setTeam
//
//@Composable
//actual fun AutoTeleSelectorMenu(
//    buildContext: BuildContext,
//    backStack: BackStack<RootNode.NavTarget>,
//    team: MutableIntState,
//    robotStartPosition: MutableIntState,
//    selectAuto: MutableState<Boolean>
//),
//visualisation = { BackStackFader(it) }
//)
//) : ParentNode<AutoTeleSelectorMenu.NavTarget>(
//appyxComponent = backStack,
//buildContext = buildContext
//) {
//
//    private var match = mutableStateOf("1")
//    private val autoSpeakerNum = mutableIntStateOf(0)
//    private val autoAmpNum = mutableIntStateOf(0)
//    private val teleSpeakerNum  =  mutableIntStateOf(0)
//    private val teleAmpNum  = mutableIntStateOf(0)
//    private val teleAmplified =  mutableIntStateOf(0)
//    private val teleTrapNum = mutableIntStateOf(0)
//    private var selectedEndPos = mutableStateOf("None")
//    private val lostComms = mutableStateOf(false)
//    private var autoNotes = mutableStateOf("")
//    private var teleNotes = mutableStateOf("")
//
//    sealed class NavTarget : Parcelable {
//        @Parcelize
//        data object AutoScouting : NavTarget()
//
//        @Parcelize
//        data object TeleScouting : NavTarget()
//    }
//
//    override fun resolve(interactionTarget: NavTarget, buildContext: BuildContext): Node =
//        when (interactionTarget) {
//            NavTarget.AutoScouting -> AutoMenu(buildContext, mainMenuBackStack, autoSpeakerNum, autoAmpNum, autoNotes)
//            NavTarget.TeleScouting -> TeleMenu(buildContext, backStack, match, team, robotStartPosition, autoSpeakerNum, autoAmpNum, autoNotes, teleSpeakerNum, teleAmpNum, teleAmplified, teleTrapNum, selectedEndPos, teleNotes, lostComms)
//
//        }
//    @Composable
//    override fun View(modifier: Modifier) {
//    setTeam(team, match, robotStartPosition.intValue)
//    var pageName by remember { mutableStateOf("Auto") }
//    var positionName by remember { mutableStateOf("") }
//
//    when (robotStartPosition.intValue){
//        0 -> {positionName = "R1"}
//        1 -> {positionName = "R2"}
//        2 -> {positionName = "R3"}
//        3 -> {positionName = "B1"}
//        4 -> {positionName = "B2"}
//        5 -> {positionName = "B3"}
//    }
//    pageName = if (!selectAuto.value) {
//        "Auto"
//    } else {
//        "Tele"
//    }
//
//
//    Column {
//        HorizontalDivider(color = defaultPrimaryVariant, thickness = 4.dp)
//
//
//        Row(Modifier.align(Alignment.CenterHorizontally).height(IntrinsicSize.Min)) {
//            Text(
//                text = positionName,
//                modifier = Modifier.scale(1.2f).align(Alignment.CenterVertically).padding(horizontal = 35.dp),
//                fontSize = 30.sp
//            )
//
//            VerticalDivider(
//                color = defaultPrimaryVariant,
//                thickness = 3.dp
//            )
//
//            Text(
//                text = "${team.intValue}",
//                modifier = Modifier.align(Alignment.CenterVertically).padding(horizontal = 35.dp),
//                fontSize = 33.sp,
//                color = Color(red = 0.1f, green = Color.Cyan.green - 0.4f, blue = Color.Cyan.blue - 0.2f)
//            )
//
//            VerticalDivider(
//                color = defaultPrimaryVariant,
//                thickness = 3.dp
//            )
//
//            Text(
//                text = "Match",
//                modifier = Modifier.align(Alignment.CenterVertically).padding(start = 35.dp),
//                fontSize = 30.sp
//            )
//
//            TextField(
//                value = match.value,
//                onValueChange = { value ->
//                    match.value = value.filter { it.isDigit() }
//                    setTeam(team, match, robotStartPosition.intValue)
//                },
//                modifier = Modifier.fillMaxWidth(),
//                colors = TextFieldDefaults.colors(
//                    focusedContainerColor = defaultBackground,
//                    unfocusedContainerColor = defaultBackground,
//                    focusedTextColor = defaultOnPrimary,
//                    unfocusedTextColor = defaultOnPrimary
//                ),
//                singleLine = true,
//                textStyle = TextStyle.Default.copy(fontSize = 30.sp)
//            )
//
//        }
//
//        HorizontalDivider(color = defaultPrimaryVariant, thickness = 3.dp)
//
//        Box(modifier = Modifier.fillMaxWidth()) {
//
//            Text(
//                text = pageName,
//                fontSize = 30.sp,
//                modifier = Modifier.align(Alignment.CenterStart).offset(x = 15.dp)
//            )
//
//            Row(Modifier.align(Alignment.CenterEnd).offset(x = (-15).dp)) {
//
//                Text("A", fontSize = 25.sp, modifier = Modifier.align(Alignment.CenterVertically))
//
//                Spacer(Modifier.width(5.dp))
//
//                Switch(
//                    checked = selectAuto.value,
//                    onCheckedChange = {
//                        selectAuto.value = it
//                        if (!selectAuto.value) {
//                            backStack.pop()
//                        } else
//                            backStack.push(AutoTeleSelectorNode.NavTarget.AutoScouting)
//                    },
//                    colors = SwitchDefaults.colors(
//                        uncheckedTrackColor = defaultOnBackground,
//                        uncheckedThumbColor = defaultBackground,
//                        checkedTrackColor = defaultOnBackground,
//                        checkedThumbColor = defaultBackground
//                    )
//                )
//
//                Spacer(Modifier.width(5.dp))
//
//                Text("T", fontSize = 25.sp, modifier = Modifier.align(Alignment.CenterVertically))
//            }
//        }
//        HorizontalDivider(
//            color = Color.Yellow,
//            thickness = 2.dp
//        )
//    }
//}