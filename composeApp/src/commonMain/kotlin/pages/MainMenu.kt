package pages

import RootNode
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.operation.push
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import defaultSecondary
import getCurrentTheme

class MainMenu(
    buildContext: BuildContext,
    private val backStack: BackStack<RootNode.NavTarget>
) : Node( buildContext = buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        Column {
            Row (modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Button(
                    content = {
                        Text(
                            text = "i",
                            color = getCurrentTheme().secondaryVariant,
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
                    fontSize = 30.sp
                )
                Button(
                    content = {
                        Text(
                            text = "s",
                            color = getCurrentTheme().secondaryVariant,
                            fontSize = 12.sp
                        )
                    },
                    onClick = {

                    },
                    modifier = Modifier.padding(3.dp),
                    contentPadding = PaddingValues(3.dp)
                )
            }
            Divider(color = getCurrentTheme().onSurface, thickness = 2.dp)
            OutlinedButton(
                border = BorderStroke(3.dp, Color.Yellow),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = defaultSecondary),
                contentPadding = PaddingValues(horizontal = 60.dp, vertical = 5.dp),
                onClick = {
                    backStack.push(RootNode.NavTarget.QuanScouting)
                },
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(horizontal = 50.dp, vertical = 50.dp),
            ) {
                Text(
                    text = "Match",
                    color = getCurrentTheme().primaryVariant,
                    fontSize = 35.sp
                )
            }

            OutlinedButton(
                border = BorderStroke(3.dp, Color.Yellow),
                shape = RoundedCornerShape(25.dp),
                contentPadding = PaddingValues(horizontal = 80.dp, vertical = 5.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = defaultSecondary),
                onClick = {
                    backStack.push(RootNode.NavTarget.PitsScouting)
                },
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(horizontal = 50.dp, vertical = 50.dp),

            ) {
                Text(
                    text = "Pits",
                    color = getCurrentTheme().primaryVariant,
                    fontSize = 35.sp
                )
            }
        }

    }
}