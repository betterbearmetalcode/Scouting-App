package composables

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.unit.sp
import defaultError

@Composable
fun InternetErrorAlert(
    onDismissRequest: () -> Unit,
) {
    AlertDialog(
        text = {
            Text(
                "Failed to ping TheBlueAlliance, check your internet connection and try again",
                fontSize = 13.sp
            )
        },
        buttons = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(
                    "Confirm",
                    fontSize = 15.sp
                )
            }
        },
        onDismissRequest = onDismissRequest,
        backgroundColor = defaultError
    )
}