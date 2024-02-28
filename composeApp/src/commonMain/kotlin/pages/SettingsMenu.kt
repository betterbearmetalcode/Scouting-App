package pages

import BackButton
import LinkMaker2
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.operation.push
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import currentColors
import defaultOnPrimary
import defaultOnSecondary
import defaultOnSurface
import defaultPrimaryVariant
import defaultSecondary
import nodes.AutoTeleSelectorNode
import nodes.RootNode
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import themeDefault

class SettingsMenu(
    buildContext: BuildContext,
    private val backStack: BackStack<RootNode.NavTarget>,
    private var themeName: MutableState<String>
) : Node(buildContext) {

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun View(modifier: Modifier) {
        var userName by remember { mutableStateOf("") }
        var themeExpanded by remember { mutableStateOf(false) }
        var effectsChecked by remember { mutableStateOf(false) }
        var highContrastChecked by remember { mutableStateOf(false) }


        Column(modifier.verticalScroll(ScrollState(0)).padding(20.dp)) {
                Text(
                    text = "Settings",
                    fontSize = 50.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Divider(color = defaultPrimaryVariant)
            Row {
                Column {
                    Text(
                        text = "Effects",
                        fontSize = 25.sp,
                        modifier = Modifier
                            .padding(20.dp)
                    )
                    Text(
                        text = "High Contrast",
                        fontSize = 25.sp,
                        modifier = Modifier
                            .padding(20.dp)
                    )
                }
                Column {
                    Switch(
                        checked = effectsChecked,
                        onCheckedChange = { effectsChecked = !effectsChecked },
                        modifier = Modifier
                            .scale(2f)
                            .padding(20.dp,0.dp)

                    )
                    Switch(
                        checked = highContrastChecked,
                        onCheckedChange = { highContrastChecked = !highContrastChecked },
                        modifier = Modifier
                            .scale(2f)
                            .padding(20.dp)
                    )
                }
            }
                OutlinedButton(
                    onClick = {
                        themeExpanded = true
                    },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(backgroundColor =defaultOnSurface),
                    border = BorderStroke(2.dp,defaultPrimaryVariant),
                    modifier = Modifier.fillMaxWidth(9f/10f).align(Alignment.CenterHorizontally)
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text("Theme: ${themeName.value}", color = defaultOnPrimary,)
                        Text("V", color = defaultOnPrimary, modifier = Modifier.align(Alignment.CenterEnd))
                    }
                }
            DropdownMenu(
                expanded = themeExpanded,
                onDismissRequest = { themeExpanded = false },
            ) {

            }
            Text(
                text="INFO"
            )
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                LinkMaker2("https://www.facebook.com/FRC2046/", "f.png", Modifier.size(60.dp))
                LinkMaker2("https://www.instagram.com/bearmetal2046/", "Insta.png", Modifier.size(60.dp))
                LinkMaker2("https://www.youtube.com/@TahomaRoboticsFRC", "youtube.png", Modifier.size(60.dp))
                LinkMaker2("https://tahomarobotics.org/", "bear-clearBackground.png", Modifier.size(60.dp))
            }
            Button(
                onClick = {
                    highContrastChecked = false
                    effectsChecked = false
                    userName = ""
                    currentColors = themeDefault()
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ){
                Text(
                    text = "Reset to Default",
                    fontSize = 25.sp,
                )
            }
        OutlinedButton(colors = ButtonDefaults.buttonColors(backgroundColor = defaultOnSurface), onClick = {backStack.push(RootNode.NavTarget.MainMenu)}){ Text("Back", color = defaultOnPrimary)}
        }
    }
}

