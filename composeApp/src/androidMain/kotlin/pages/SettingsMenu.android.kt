package pages

import RootNode
import android.widget.ImageView
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.sp
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import androidx.compose.material.Button

actual class SettingsMenu actual constructor(
    buildContext: BuildContext,
    private val backStack: BackStack<RootNode.NavTarget>
) : Node(buildContext) {


    @Composable
    actual override fun View(modifier: Modifier) {
        var userName by remember { mutableStateOf("") }
        var themeExpanded by remember { mutableStateOf(false) }
        var effectsChecked by remember { mutableStateOf(false) }
        var highContrastChecked by remember { mutableStateOf(false) }
        var Expanded by remember { mutableStateOf(false) }
        val themeSelection = listOf("Default,Bear Metal,Catppuccine")
        var SelectedTheme by remember { mutableStateOf("") }

        Column(modifier.verticalScroll(ScrollState(0))) {

            Text(
                text = "Settings",
                fontSize = 50.sp,

                modifier = Modifier
                    .align(Alignment.CenterHorizontally)

            )
            Text(
                text = "Account:",
                fontSize = 35.sp,

                modifier = Modifier
                    .align(Alignment.CenterHorizontally)

            )
            Row {
                Text(
                    text = "Name: ",
                    fontSize = 25.sp
                )
                TextField(
                    value = userName,
                    onValueChange = {
                        userName = it
                    }
                )
            }
            Text(
                text = "Appearence",
                fontSize = 35.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
                DropdownMenu(
                    expanded = themeExpanded,
                    onDismissRequest = { themeExpanded = false },
                    content = {
                        Button(
                            onClick = {themeExpanded = true},
                            content = {
                                Text(
                                    text = "Themes",
                                    fontSize = 25.sp,
                                )
                            }
                        )

                    }
                )

            Row {

                Switch(
                    checked = effectsChecked,
                    onCheckedChange = {effectsChecked = !effectsChecked },
                    modifier = Modifier
                        .scale(2f)

                )
                Text(
                    text = "Effects",
                    fontSize = 25.sp,
                    modifier = Modifier
                )
            }
            Text(
                text = "Accesability",
                fontSize = 35.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
           /* Row {
                Switch(
                    checked = highContrastChecked,
                    onCheckedChange = { highContrastChecked = !highContrastChecked },
                    modifier = Modifier
                        .scale(2f)
                )
                Text(
                    text = "High Contrast",
                    fontSize = 25.sp,
                    modifier = Modifier
                )
            } */
                Button(
                    content = {
                        Text(
                        text = "Reset to Default",
                        fontSize = 35.sp,
                        )
                    },
                    onClick = {
                        highContrastChecked = false
                        effectsChecked = false
                        userName = ""
                    },
                    modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                )

        }
    }

}
