package composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxColors
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
@Composable
fun CheckBox(
    label: String,
    ifChecked: MutableState<Boolean>,
    modifier: Modifier
){
    Row {
        Text(
            text = label,
            modifier = Modifier.offset(0.dp, 15.dp)
        )
        Checkbox(
            checked = ifChecked.value,
            colors = CheckboxDefaults.colors(
                checkedColor = Color.Cyan,
            ),
            onCheckedChange = { ifChecked.value = it },
        )
    }
}