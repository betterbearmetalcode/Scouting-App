package pages

import EnumerableValue
import RootNode
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node

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
                        uncheckedTrackColor = Color.Blue,
                        checkedThumbColor = Color.Red,
                        checkedTrackColor = Color.Red
                    ),
                    modifier = Modifier
                        .scale(1.5f)
                        .padding(15.dp)
                )
            }
            Text(
                text="Alliance"
            )

            Text(
                text = "Auto"
            )

            EnumerableValue(label = "Speaker", value = autoSpeakerNum)
            EnumerableValue(label = "Amp", value = autoAmpNum)

            EnumerableValue(label ="Speaker" , value = teleSpeakerNum )
            EnumerableValue(label ="Amp" , value = teleAmpNum )
            EnumerableValue(label ="Amplified" , value = teleAmplified )
            EnumerableValue(label ="Trap" , value = teleTrapNum )

        }
    }
}