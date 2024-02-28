package pages

import BackButton
import LinkMaker
import LinkMaker2
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
class InfoPage(
    buildContext: BuildContext,
    private val backStack: BackStack<RootNode.NavTarget>
) : Node(buildContext) {
    @OptIn(ExperimentalResourceApi::class, ExperimentalTextApi::class)
    @Composable
    override fun View(modifier: Modifier) {
        Column(modifier.padding(20.dp)) {
            Row{
                BackButton(
                    backStack = backStack,
                    content = {
                        Image(
                            painter = painterResource(res = "back-arrow.png"),
                            contentDescription = "Back Arrow",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxSize(1f / 6f)
                                .padding(0.dp)
                        )
                    }
                )
                Text(
                    text="INFO"
                )
            }
            Text("Find us here:")
            LinkMaker2("https://www.facebook.com/FRC2046/","f.png",Modifier.size(60.dp))
            LinkMaker2("https://www.instagram.com/bearmetal2046/","Insta.png",Modifier.size(60.dp))
            LinkMaker2("https://www.youtube.com/@TahomaRoboticsFRC","youtube.png",Modifier.size(60.dp))
            LinkMaker2("https://tahomarobotics.org/","bear-clearBackground.png",Modifier.size(60.dp))
            }

    }
}