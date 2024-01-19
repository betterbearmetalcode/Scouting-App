import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier

@Composable
fun EnumerableValue(
    label: String,
    value: MutableState<Int>,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start
) {
    Row (
        modifier = modifier,
        horizontalArrangement = horizontalArrangement
    ) {
        Text(label)
        Button(
            onClick = {
                value.value -= 1
            }
        ) {
            Text(
                text = "-"
            )
        }
        Text(
            text = value.value.toString()
        )
        Button(
            onClick = {
                value.value += 1
            }
        ) {
            Text(
                text = "+"
            )
        }
    }

}