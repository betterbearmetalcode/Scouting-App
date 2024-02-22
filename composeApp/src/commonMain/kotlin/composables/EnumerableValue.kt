package composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
expect fun EnumerableValue(
    label: String,
    value: MutableState<Int>
)