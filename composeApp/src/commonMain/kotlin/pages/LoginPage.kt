package pages

import nodes.RootNode
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.operation.push
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import defaultBackground
import defaultOnPrimary
import defaultPrimaryVariant
import getCurrentTheme
import java.io.File

class LoginPage(
    buildContext: BuildContext,
    private val backStack: BackStack<RootNode.NavTarget>,
    private var scoutName: MutableState<String>,
    private var comp:  MutableState<String>,
) : Node(buildContext) {
    @Composable
    override fun View(modifier: Modifier) {
        val logo = File("Logo.png")
        var compDD by remember { mutableStateOf(false)}
        var compKey by remember { mutableStateOf("") }
        val tbaMatches = listOf(
            "2024wabon",
            "2024wasam",
            "2024orsal",
            "2024txhou"

        )
        Column {
//            Image(
//                painter = BitmapPainter(logo),//turn into bitmap
//                contentDescription = "Logo"
//            )
            Text(
                text = "Login",
                fontSize = 45.sp,
                color = getCurrentTheme().onPrimary,
                modifier = Modifier.align(Alignment.CenterHorizontally).offset(0.dp, (-28).dp)
            )
            Divider(
                color = defaultPrimaryVariant,
                modifier = Modifier.offset(0.dp, (-25).dp),
                thickness = 3.dp
            )
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(text = "Name", color = defaultOnPrimary)
                OutlinedTextField(
                    value = scoutName.value,
                    onValueChange = {scoutName.value = it},
                    shape = RoundedCornerShape(15.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = getCurrentTheme().background, focusedBorderColor = getCurrentTheme().onSecondary, unfocusedBorderColor = getCurrentTheme().onSecondary, textColor = getCurrentTheme().onPrimary, cursorColor = getCurrentTheme().onSecondary),
                )
            }
            Box(modifier = Modifier.padding(15.dp).fillMaxWidth()) {
                OutlinedButton(
                    onClick = { compDD = true },
                    shape = RoundedCornerShape(15.dp),
                    border = BorderStroke(3.dp, color = defaultPrimaryVariant),
                    colors = ButtonDefaults.buttonColors(backgroundColor = getCurrentTheme().primary)
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Competition: ${comp.value}",
                            color = getCurrentTheme().onPrimary,
                            modifier = Modifier.align(Alignment.CenterStart)
                        )
                        Text(
                            text = "V",
                            color = getCurrentTheme().onPrimary,
                            modifier = Modifier.align(Alignment.CenterEnd)
                        )
                    }
                }
                DropdownMenu(expanded = compDD, onDismissRequest = { compDD = false; },modifier=Modifier.background(color = getCurrentTheme().onSurface)) {
                    DropdownMenuItem(
                        onClick = { comp.value = "Bonney Lake"; compDD = false; compKey = tbaMatches[0]}
                    ) { Text(text = "Bonney Lake", color = getCurrentTheme().onPrimary,modifier=Modifier.background(color = getCurrentTheme().onSurface)) }
                    DropdownMenuItem(
                        onClick = { comp.value = "Lake Sammamish"; compDD = false; compKey = tbaMatches[1]}
                    ) { Text(text = "Lake Sammamish", color = getCurrentTheme().onPrimary,modifier=Modifier.background(color = getCurrentTheme().onSurface)) }
                    DropdownMenuItem(
                        onClick = { comp.value = "Salem"; compDD = false; compKey = tbaMatches[2]}
                    ) { Text(text = "Salem", color = getCurrentTheme().onPrimary,modifier=Modifier.background(color = getCurrentTheme().onSurface)) }
                    DropdownMenuItem(
                        onClick = { comp.value = "Houston"; compDD = false; compKey = tbaMatches[3]}
                    ) { Text(text = "Houston", color = getCurrentTheme().onPrimary,modifier=Modifier.background(color = getCurrentTheme().onSurface)) }
                }

            }
            Divider(
                color = defaultPrimaryVariant,
            )
            OutlinedButton(
                onClick = {
                    if (comp.value != "" && scoutName.value != "")
                    backStack.push(RootNode.NavTarget.MainMenu)
                },
                border = BorderStroke(color = defaultPrimaryVariant, width = 2.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = getCurrentTheme().primary)
            ) {
                Text(
                    text = "Submit",
                    color = getCurrentTheme().onPrimary
                )
            }
        }
    }
}
