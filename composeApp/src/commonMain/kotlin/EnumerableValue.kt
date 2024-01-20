import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
        Text(
            label,
            fontSize = 25.sp
        )
        Button(
            onClick = {
                value.value -= 1
            }
        ) {
            Text(
                text = "-",
                fontSize = 15.sp
            )
        }
        Text(
            text = value.value.toString(),
            fontSize = 15.sp,
            modifier = Modifier.offset(
                y = 15.dp,
            )
        )
        Button(
            onClick = {
                value.value += 1
            }
        ) {
            Text(
                text = "+",
                fontSize = 15.sp,
            )
        }
    }

}