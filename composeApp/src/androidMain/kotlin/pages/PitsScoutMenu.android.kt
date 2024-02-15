package pages

import RootNode
import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import org.example.project.ComposeFileProvider

actual class PitsScoutMenu actual constructor(
    buildContext: BuildContext,
    backStack: BackStack<RootNode.NavTarget>
) : Node(buildContext = buildContext)
{
    @Composable
    actual override fun View(modifier: Modifier) {
        val launcher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { _: Boolean ->

        }
        var hasImage by remember {
            mutableStateOf(false)
        }
        var imageUri by remember {
            mutableStateOf<Uri?>(null)
        }

        val imagePicker = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = { uri ->
                hasImage = uri != null
                imageUri = uri
            }
        )

        val cameraLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture(),
            onResult = { success ->
                hasImage = success
            }
        )

        val context = LocalContext.current

        var notes by remember { mutableStateOf("") }
        var teamName by remember { mutableStateOf("") }
        var teamNumber by remember { mutableStateOf("") }


        Column(modifier = modifier.verticalScroll(ScrollState(0))) {
            if (hasImage && imageUri != null) {
                AsyncImage(
                    model = imageUri,
                    modifier = Modifier.fillMaxWidth(),
                    contentDescription = "Selected image",
                )
            }
            Column(
                modifier = Modifier
                    .padding(bottom = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Button(
                    onClick = {
                        imagePicker.launch("image/*")
                    },
                ) {
                    Text(
                        text = "Select Image"
                    )
                }
                Button(
                    modifier = Modifier.padding(top = 16.dp),
                    onClick = {
                        when (PackageManager.PERMISSION_GRANTED) {
                            ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.CAMERA
                            ) -> {
                                val uri = ComposeFileProvider.getImageUri(context)
                                imageUri = uri
                                cameraLauncher.launch(uri)
                            }
                            else -> {
                                launcher.launch(Manifest.permission.CAMERA)
                            }
                        }

                    },
                ) {
                    Text(
                        text = "Take photo"
                    )
                }
            }
            Row {
                TextField(
                    value = teamNumber,
                    onValueChange = { it ->
                        teamNumber = it.filter { it.isDigit() }
                    },
                    placeholder = {Text(text = "Team Number") },
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth(1/2f)
                )
                TextField(
                    value = teamName,
                    onValueChange = {
                        teamName = it
                    },
                    placeholder = {Text(text = "Team Name")},
                    maxLines = 1
                )
            }

            TextField(
                value = notes,
                onValueChange = {
                    notes = it
                },
                placeholder = {Text(text = "Notes")},
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}