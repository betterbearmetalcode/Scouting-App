package composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier

@Composable
expect fun CheckBox(
    label: String,
    ifChecked: MutableState<Boolean>,
    modifier: Modifier
)