package pages

import EnumerableValue
import RootNode
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.InspectableModifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import androidx.compose.material.DropdownMenuItem as DropdownMenuItem

actual class QuanScoutMenu actual constructor(
    buildContext: BuildContext,
    private val backStack: BackStack<RootNode.NavTarget>
) : Node(buildContext) {
    @Composable
    actual override fun View(modifier: Modifier) {
        var match by remember { mutableStateOf("") }
        var team by remember { mutableStateOf("") }
        var allianceColor by remember { mutableStateOf(false) }
        var autoSpeakerNum = remember { mutableStateOf(0) }
        var autoAmpNum = remember { mutableStateOf(0) }
        var teleSpeakerNum  = remember { mutableStateOf(0) }
        var teleAmpNum  = remember { mutableStateOf(0) }
        var teleAmplified = remember { mutableStateOf(0) }
        var teleTrapNum = remember { mutableStateOf(0) }
        var endGameDropDown by remember { mutableStateOf(false) }
        var selectedEndPos by remember { mutableStateOf("None") }
        data class EndPosition(val endPos: String)

        fun endPosition() = listOf(
            EndPosition("None"),
            EndPosition("Parked"),
            EndPosition("Climbed"),
            EndPosition("Harmonized")
        )

        Column(modifier) {

            Row {
                Text(
                    text = "Match"
                )
                TextField(
                    value = match,
                    onValueChange = { match = it },
                    modifier = Modifier.fillMaxWidth(1f/4f)
                )
                Text(
                    text = "Team"
                )
                TextField(
                    value = team,
                    onValueChange = { team = it },
                    modifier = Modifier.fillMaxWidth(1f/2f)
                )
                Switch(
                    checked = allianceColor,
                    onCheckedChange = { allianceColor = !allianceColor },
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
            Text(
                text = "Auto",
                fontSize = 25.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Divider(color = Color(255, 191, 0), thickness = 1.dp)

            EnumerableValue(label = "Speaker", value = autoSpeakerNum)
            EnumerableValue(label = "Amp", value = autoAmpNum)

            Text(
                text = "Teleop",
                fontSize = 25.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Divider(color = Color(255, 191, 0), thickness = 1.dp)

            EnumerableValue(label ="Speaker" , value = teleSpeakerNum, horizontalArrangement = Arrangement.Absolute.Center )//No worky?
            EnumerableValue(label ="Amp" , value = teleAmpNum )
            EnumerableValue(label ="Amplified" , value = teleAmplified )
            EnumerableValue(label ="Trap" , value = teleTrapNum )

            Divider(color = Color.Black, thickness = 4.dp)
            Row{
            Button(
                onClick = {endGameDropDown= !endGameDropDown},
                content = {
                    Text("v    " + selectedEndPos)

                }
            )
            DropdownMenu(
                expanded = endGameDropDown,
                onDismissRequest = {endGameDropDown = false},
                modifier = Modifier.background(MaterialTheme.colors.background)
            ){
                endPosition().forEach { (endPos) ->
                    DropdownMenuItem(
                        onClick = {
                            endGameDropDown = false
                            selectedEndPos = endPos
                        }
                    ){
                        Text(text = endPos)
                    }
                }
            }
            }
        }
    }
}
