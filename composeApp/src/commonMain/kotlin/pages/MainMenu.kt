package pages

import RootNode
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.operation.push
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import currentColors

class MainMenu(
    buildContext: BuildContext,
    private val backStack: BackStack<RootNode.NavTarget>
) : Node( buildContext = buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        var theme by remember { mutableStateOf(currentColors) }
        MaterialTheme(theme) {
            Column(modifier = modifier) {
                Row {
                    Button(
                        content = {
                            Text(
                                text = "i",
                                fontSize = 12.sp
                            )
                        },
                        onClick = {

                        },
                        modifier = Modifier.padding(3.dp),
                        contentPadding = PaddingValues(3.dp)
                    )
                    Text(
                        text = "Bear Metal Scout App",
                        fontSize = 25.sp
                    )
                    Button(
                        content = {
                            Text(
                                text = "s",
                                fontSize = 12.sp
                            )
                        },
                        onClick = {
                            backStack.push(RootNode.NavTarget.SettingsMenu)
                        },
                        modifier = Modifier.padding(3.dp),
                        contentPadding = PaddingValues(3.dp)
                    )
                }
                Divider(color = theme.secondary, thickness = 2.dp)
                Button(
                    content = {
                        Text(
                            text = "Quantitative Scouting",
                            fontSize = 23.sp
                        )
                    },
                    onClick = {
                        backStack.push(RootNode.NavTarget.QuanScouting)
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                        .padding(horizontal = 50.dp, vertical = 50.dp),
                )

                Button(
                    content = {
                        Text(
                            text = "Pits Scouting",
                            fontSize = 22.sp
                        )
                    },
                    onClick = {
                        backStack.push(RootNode.NavTarget.PitsScouting)
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                        .padding(horizontal = 50.dp, vertical = 50.dp),

                    )

                Button(
                    content = {
                        Text(
                            text = "Qualitative Scouting",
                            fontSize = 22.sp
                        )
                    },
                    onClick = {
                        backStack.push(RootNode.NavTarget.QualScouting)
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                        .padding(horizontal = 50.dp, vertical = 50.dp),

                    )
            }
        }

    }
}