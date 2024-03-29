package pages

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.operation.pop
import com.bumble.appyx.components.backstack.operation.push
import composables.InternetErrorAlert
import defaultBackground
import defaultOnBackground
import defaultOnPrimary
import defaultPrimaryVariant
import exportScoutData
import nodes.*
import org.json.JSONException
import setTeam
import java.lang.Integer.parseInt


@Composable
actual fun AutoTeleSelectorMenu(
    match: MutableState<String>,
    team: MutableIntState,
    robotStartPosition: MutableIntState,
    selectAuto: MutableState<Boolean>,
    backStack: BackStack<AutoTeleSelectorNode.NavTarget>,
    mainMenuBackStack: BackStack<RootNode.NavTarget>
) {

    var pageName by remember { mutableStateOf("Auto") }
    var positionName by remember { mutableStateOf("") }
    val context = LocalContext.current
    var teamNumAsText by remember { mutableStateOf(team.intValue.toString()) }

    when {
        openError.value -> {
            InternetErrorAlert {
                openError.value = false
                mainMenuBackStack.pop()
            }
        }
    }


    when (robotStartPosition.intValue){
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
        HorizontalDivider(color = defaultPrimaryVariant, thickness = 4.dp)


        Row(
            Modifier
                .align(Alignment.CenterHorizontally)
                .height(IntrinsicSize.Min)) {
            Text(
                text = positionName,
                modifier = Modifier
                    .scale(1.2f)
                    .align(Alignment.CenterVertically)
                    .padding(horizontal = 25.dp),
                fontSize = 28.sp
            )

            VerticalDivider(
                color = defaultPrimaryVariant,
                thickness = 3.dp
            )
            val textColor = if (positionName.lowercase().contains("b")) {
                Color(red = 0.1f, green = Color.Cyan.green - 0.4f, blue = Color.Cyan.blue - 0.2f)
            } else {
                Color.Red
            }

            TextField(
                value = teamNumAsText,
                onValueChange = { value ->
                    val filteredText = value.filter { it.isDigit() }
                    teamNumAsText = filteredText.slice(0..<filteredText.length.coerceAtMost(5))//WHY IS FILTER NOT FILTERING
                    if (teamNumAsText.isNotEmpty() || teamNumAsText.contains(','))
                        team.intValue = parseInt(teamNumAsText)
                    println(createOutput(team,robotStartPosition))
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = defaultBackground,
                    unfocusedContainerColor = defaultBackground,
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor
                ),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
//                    .padding(horizontal = 25.dp)
                    .width(125.dp),
                textStyle = TextStyle.Default.copy(fontSize = 31.sp),
                singleLine = true,
            )
//            Text(
//                text = "${team.intValue}",
//                modifier = Modifier
//                    .align(Alignment.CenterVertically)
//                    .padding(horizontal = 25.dp),
//                fontSize = 31.sp,
//                color = if (
//                    positionName.lowercase().contains("b")) {
//                        Color(red = 0.1f, green = Color.Cyan.green - 0.4f, blue = Color.Cyan.blue - 0.2f)
//                } else {
//                    Color.Red
//                }
//            )

            VerticalDivider(
                color = defaultPrimaryVariant,
                thickness = 3.dp
            )

            Text(
                text = "Match",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 25.dp),
                fontSize = 28.sp
            )

            TextField(
                value = match.value,
                onValueChange = { value ->
                    val temp = value.filter { it.isDigit() }
                    match.value = temp.slice(0..<temp.length.coerceAtMost(5))
                    if(match.value != ""){
                        loadData(parseInt(temp), team, robotStartPosition)
                    }
                    if(match.value != "") {
                        matchScoutArray[robotStartPosition.intValue]?.set(parseInt(match.value),
                            createOutput(team, robotStartPosition)
                        )
                        exportScoutData(context)
                    }
                    try {
                        setTeam(team, nodes.match, robotStartPosition.intValue)
                    } catch (e: JSONException) {
                        openError.value = true
                    }
                    teamNumAsText = team.intValue.toString()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = defaultBackground,
                    unfocusedContainerColor = defaultBackground,
                    focusedTextColor = defaultOnPrimary,
                    unfocusedTextColor = defaultOnPrimary
                ),
                singleLine = true,
                textStyle = TextStyle.Default.copy(fontSize = 28.sp)
            )

        }

        HorizontalDivider(color = defaultPrimaryVariant, thickness = 3.dp)

        Box(modifier = Modifier.fillMaxWidth()) {

            Text(
                text = pageName,
                fontSize = 30.sp,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .offset(x = 15.dp)
            )

            Row(
                Modifier
                    .align(Alignment.CenterEnd)
                    .offset(x = (-15).dp)) {

                Text("A", fontSize = 25.sp, modifier = Modifier.align(Alignment.CenterVertically))

                Spacer(Modifier.width(5.dp))

                Switch(
                    checked = selectAuto.value,
                    onCheckedChange = {
                        selectAuto.value = it
                        if (!selectAuto.value) {
                            backStack.pop()
                        } else
                            backStack.push(AutoTeleSelectorNode.NavTarget.TeleScouting)
                    },
                    colors = SwitchDefaults.colors(
                        uncheckedTrackColor = defaultOnBackground,
                        uncheckedThumbColor = defaultBackground,
                        checkedTrackColor = defaultOnBackground,
                        checkedThumbColor = defaultBackground
                    )
                )

                Spacer(Modifier.width(5.dp))

                Text("T", fontSize = 25.sp, modifier = Modifier.align(Alignment.CenterVertically))
            }
        }
        HorizontalDivider(
            color = Color.Yellow,
            thickness = 2.dp
        )
    }
}