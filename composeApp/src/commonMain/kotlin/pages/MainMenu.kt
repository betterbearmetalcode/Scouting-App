package pages

import RootNode
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.operation.push
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import currentColors
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

class MainMenu(
    buildContext: BuildContext,
    private val backStack: BackStack<RootNode.NavTarget>
) : Node( buildContext = buildContext) {

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun View(modifier: Modifier) {
        Column(modifier = modifier) {
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Button(
                    content = {
                        Text(
                            text = "i",
                            fontSize = 12.sp
                        )
                    },
                    onClick = {
                        backStack.push(RootNode.NavTarget.InfoPage)
                    },
                    modifier = Modifier.padding(3.dp),
                    contentPadding = PaddingValues(3.dp)
                )
                Text(
                    text = "Bear Metal Scout App",
                    fontSize = 25.sp
                )
                TextButton(
                    content = {
                        Image(
                            painter = painterResource(res = "settingsicon.png" ),
                            contentDescription = "Settings",
                        )
                    },
                    onClick = {
                        backStack.push(RootNode.NavTarget.SettingsMenu)
                    },
                    modifier = Modifier
                        .padding(3.dp)
                        .size(50.dp),
                    contentPadding = PaddingValues(3.dp)
                )
            }
            Divider(color  = currentColors.secondary, thickness = 2.dp)
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
                onClick = {
                    backStack.push(RootNode.NavTarget.PitsScouting)
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .padding(horizontal = 50.dp, vertical = 50.dp),

            ) {
                Text(
                    text = "Pits Scouting",
                    fontSize = 22.sp
                )
            }

            Button(
                onClick = {
                    backStack.push(RootNode.NavTarget.QualScouting)
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .padding(horizontal = 50.dp, vertical = 50.dp),

            ) {
                Text(
                    text = "Qualitative Scouting",
                    fontSize = 22.sp
                )
            }

        }


    }
}