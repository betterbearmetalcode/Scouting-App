package pages

import BackButton
import ThemeViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import currentColors
import getThemes
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import themeDefault

actual class SettingsMenu actual constructor(
    buildContext: BuildContext,
    private val backStack: BackStack<RootNode.NavTarget>
) : Node(buildContext) {

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    actual override fun View(modifier: Modifier) {
        val themeViewModel by remember{ mutableStateOf(ThemeViewModel()) }
        var userName by remember { mutableStateOf("") }
        var themeExpanded by remember { mutableStateOf(false) }
        var effectsChecked by remember { mutableStateOf(false) }
        var highContrastChecked by remember { mutableStateOf(false) }


        Column(modifier.verticalScroll(ScrollState(0)).padding(20.dp)) {
            Row {
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
                    text = "Settings",
                    fontSize = 50.sp
                )
            }
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

            Row {
                DropdownMenu(
                    expanded = themeExpanded,
                    onDismissRequest = { themeExpanded = false },
                ) {
                    getThemes().forEach { (name, colors) ->
                        fun Modifier.clip(shape: Shape) = graphicsLayer(shape = shape, clip = true)
                        var dropdownModifier = Modifier
                            .clip(shape = RoundedCornerShape(15.dp))
                        if (colors.background == themeViewModel.theme.value.background) {

                            var color = colors.surface
                            if (name == "light") {
                                color = Color.LightGray
                            }
                            color = Color(red = color.red, green = color.green, blue = color.blue, alpha = .5f)

                            dropdownModifier = dropdownModifier
                                .background(color, shape = RoundedCornerShape(15.dp))
                        }

                        DropdownMenuItem(
                            onClick = {
                                themeViewModel.updateTheme(colors)
                                currentColors = colors
                                themeExpanded = false
                            },
                            modifier = dropdownModifier
                        ) {
                            Text(name)
                        }
                    }
                }

                Button(
                    onClick = {
                        themeExpanded = true
                    }
                ) {
                    Text("Theme")
                }
            }

            Row {

                Switch(
                    checked = effectsChecked,
                    onCheckedChange = { effectsChecked = !effectsChecked },
                    modifier = Modifier
                        .scale(2f)
                        .padding(25.dp)

                )
                Text(
                    text = "Effects",
                    fontSize = 25.sp,
                    modifier = Modifier
                        .padding(28.dp)
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
                    currentColors = themeDefault()
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )

        }
    }

}

