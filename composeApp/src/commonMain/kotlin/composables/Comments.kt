package composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import defaultBackground
import defaultOnBackground
import defaultOnPrimary
import defaultPrimaryVariant
import androidx.compose.ui.graphics.Color
@Composable
fun Comments(text: MutableState<String>, isScrollEnabled: MutableState<Boolean>) {
    Column {
        Text("Comments", fontSize = 25.sp)
        Spacer(Modifier.height(5.dp))
        Box {
            OutlinedTextField(
                value = text.value,
                placeholder = {
                    Text("Write Here")
                },
                shape = RoundedCornerShape(25.dp),
                onValueChange = {
                    //val oldText = text.value
                    text.value = it
                    isScrollEnabled.value = false
//                    if (text.value.length > 150)
//                        text.value = oldText
                },
                modifier = Modifier
                    .size(400.dp, 75.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = defaultBackground,
                    focusedBorderColor = Color.Cyan,
                    unfocusedBorderColor = defaultOnBackground,
                    textColor = defaultOnPrimary,
                    cursorColor = defaultPrimaryVariant,
                )
            )

//            Text(
//                "${text.value.length}/150",
//                Modifier
//                    .align(Alignment.BottomEnd)
//                    .offset(x = (-7).dp, y = (-10).dp),
//                fontSize = 10.sp, color = defaultPrimaryVariant
//            )
        }
    }
}