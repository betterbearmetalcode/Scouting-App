package pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.operation.push
import compKey
import defaultError
import defaultOnPrimary
import defaultPrimaryVariant
import deleteFile
import getCurrentTheme
import nodes.RootNode
import nodes.matchScoutArray
import nodes.reset
import java.io.File

@Composable
actual fun LoginMenu(
    backStack: BackStack<RootNode.NavTarget>,
    scoutName: MutableState<String>,
    comp: MutableState<String>
) {
    val logo = File("Logo.png")
    var compDD by remember { mutableStateOf(false) }
    var deleteData by remember { mutableStateOf(false) }
    val tbaMatches = listOf(
        "2024wabon",
        "2024wasam",
        "2024orsal",
        "2024txhou"

    )
    Column {
        Image(
            painter = painterResource(logo.path),//turn into bitmap
            contentDescription = "Logo"
        )
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
                placeholder ={ Text("First Name Last Name", color = Color.Gray) },
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
            DropdownMenu(expanded = compDD, onDismissRequest = { compDD = false; },modifier= Modifier.background(color = getCurrentTheme().onSurface)) {
                DropdownMenuItem(
                    onClick = { comp.value = "Bonney Lake"; compDD = false; compKey = tbaMatches[0]}
                ) { Text(text = "Bonney Lake", color = getCurrentTheme().onPrimary,modifier= Modifier.background(color = getCurrentTheme().onSurface)) }
                DropdownMenuItem(
                    onClick = { comp.value = "Lake Sammamish"; compDD = false; compKey = tbaMatches[1]}
                ) { Text(text = "Lake Sammamish", color = getCurrentTheme().onPrimary,modifier= Modifier.background(color = getCurrentTheme().onSurface)) }
                DropdownMenuItem(
                    onClick = { comp.value = "Salem"; compDD = false; compKey = tbaMatches[2]}
                ) { Text(text = "Salem", color = getCurrentTheme().onPrimary,modifier= Modifier.background(color = getCurrentTheme().onSurface)) }
                DropdownMenuItem(
                    onClick = { comp.value = "Houston"; compDD = false; compKey = tbaMatches[3]}
                ) { Text(text = "Houston", color = getCurrentTheme().onPrimary,modifier= Modifier.background(color = getCurrentTheme().onSurface)) }
            }

        }
        Divider(
            color = defaultPrimaryVariant,
        )
        Box(modifier = Modifier.fillMaxWidth(9f/10f).align(Alignment.CenterHorizontally)) {
            OutlinedButton(
                onClick = {
                    if (comp.value != "" && scoutName.value != "")
                        backStack.push(RootNode.NavTarget.MainMenu)
                },
                border = BorderStroke(color = defaultPrimaryVariant, width = 2.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = getCurrentTheme().primary),
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Text(
                    text = "Submit",
                    color = getCurrentTheme().onPrimary
                )
            }
            OutlinedButton(
                onClick = { deleteData = true },
                border = BorderStroke(color = defaultPrimaryVariant, width = 2.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = getCurrentTheme().primary),
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Text(
                    text = "Delete Data",
                    color = getCurrentTheme().onPrimary
                )
            }
            if (deleteData) {
                AlertDialog(
                    title = { Text(text = "Are you sure?") },
                    onDismissRequest = { deleteData = false },
                    buttons = {
                        Box(modifier = Modifier.fillMaxWidth(8f / 10f)) {
                            Button(
                                onClick = {
                                    deleteData = false; matchScoutArray.clear(); reset(); deleteFile()
                                },
                                modifier = Modifier.align(Alignment.CenterStart)
                            ) {
                                Text(text = "Yes", color = defaultError)
                            }
                        }
                        Button(
                            onClick = { deleteData = false },
                            modifier = Modifier.align(Alignment.CenterEnd)
                        ) {
                            Text(text = "No", color = defaultError)
                        }
                    },
                    modifier = Modifier.clip(
                        RoundedCornerShape(5.dp)
                    ).border(BorderStroke(3.dp, defaultPrimaryVariant), RoundedCornerShape(5.dp))

                )

            }
        }
    }
}