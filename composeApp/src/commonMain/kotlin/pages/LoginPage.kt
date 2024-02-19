package pages

import RootNode
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.operation.push
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import defaultOnPrimary
import defaultPrimaryVariant

class LoginPage constructor(
    buildContext: BuildContext,
    private val backStack: BackStack<RootNode.NavTarget>,
) : Node(buildContext) {
    @Composable
    override fun View(modifier: Modifier) {

        Column(){
            Image(painter = painterResource("bearmetallogo.jpg"),
                contentDescription = "Bear Metal",
                modifier = Modifier.fillMaxSize(1f/2f).align(Alignment.CenterHorizontally)
            )
            Text(
                text = "Login",
                fontSize = 45.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Divider(
                color = defaultPrimaryVariant
            )

            OutlinedButton(
                onClick = {
                    backStack.push(RootNode.NavTarget.MainMenu)
                },
                border = BorderStroke(color = defaultPrimaryVariant, width = 2.dp)
            ){
                Text(
                    text = "Submit",
                    color = defaultOnPrimary
                )
            }

        }
    }
}
