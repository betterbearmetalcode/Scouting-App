package composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import defaultBackground
import defaultOnBackground
import defaultOnPrimary

@Composable
fun Notes(text: MutableState<String>, isScrollEnabled: MutableState<Boolean>) {
    Column {
        Text("Notes", fontSize = 25.sp)
        Spacer(Modifier.height(5.dp))
        OutlinedTextField(
            value = text.value,
            placeholder = {
                Text("Write Here")
            },
            shape = RoundedCornerShape(25.dp),
            onValueChange = {
                text.value = it
                isScrollEnabled.value = false
            },
            modifier = Modifier
                .size(400.dp, 200.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = defaultBackground,
                focusedBorderColor = defaultOnBackground,
                unfocusedBorderColor = defaultOnBackground,
                textColor = defaultOnPrimary
            ),
        )
    }
}