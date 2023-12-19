package pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import getCurrentTheme
import kotlin.math.round

class QualScoutMenu(
    buildContext: BuildContext
) : Node( buildContext = buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        var text by remember { mutableStateOf("") }
        var defenceScore by remember { mutableStateOf(5f) }
        var theme by remember { mutableStateOf(getCurrentTheme()) }

        Column (modifier = modifier) {
            TextField(
                value = text,
                onValueChange = {
                    text = it
                },
                placeholder = {
                    Text("Notes")
                },
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(0.dp, 10.dp, 0.dp, 60.dp)
            )

            Text(
                text = "Defence Rating",
                fontSize = 30.sp,
                modifier = Modifier.padding(15.dp, 0.dp, 0.dp, 0.dp)
            )

            Slider(
                value = defenceScore,
                valueRange = 0f..10f,
                onValueChange = {
                    defenceScore = it
                    defenceScore = round(defenceScore * 10) / 10f
                },
                steps = 9
            )
            Text(
                text = defenceScore.toString(),
                modifier = Modifier.padding(15.dp, 0.dp, 0.dp, 0.dp)
            )

        }
    }
}