package composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import getCurrentTheme

@Composable
actual fun EnumerableValue(label: String, value: MutableIntState) {
    Box(modifier = Modifier.fillMaxWidth()) {

        Text(
            label,
            fontSize = 25.sp,
            modifier = Modifier.align(Alignment.CenterStart)
        )

        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.align(Alignment.Center)) {
            OutlinedButton(
                border = BorderStroke(2.dp, color = Color.Yellow),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = getCurrentTheme().secondary,
                    contentColor = getCurrentTheme().onPrimary
                ),
                onClick = {
                    value.value -= 1
                    if (value.value < 0)
                        value.value = 0
                }
            ) {
                Text(
                    text = "-",
                    fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
            Text(
                text = value.value.toString(),
                fontSize = 30.sp,
                modifier = Modifier.padding(5.dp).align(Alignment.CenterVertically)
            )
            OutlinedButton(
                border = BorderStroke(2.dp, color = Color.Yellow),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = getCurrentTheme().secondary,
                    contentColor = getCurrentTheme().onPrimary
                ),
                onClick = {
                    value.value += 1
                }
            ) {
                Text(
                    text = "+",
                    fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
    }
}

