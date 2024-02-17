package composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CheckBox(
    label: String,
    ifChecked: MutableState<Boolean>
){
    Row {
        Text(
            text = label,
            modifier = Modifier.offset(0.dp, 15.dp)
        )
        Checkbox(
            checked = ifChecked.value,
            onCheckedChange = { ifChecked.value = !ifChecked.value },
        )
    }
}