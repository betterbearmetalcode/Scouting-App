package composables

import android.net.Uri
import android.view.View
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import defaultOnPrimary
import defaultPrimaryVariant
import generateBitmap
import java.lang.reflect.Array.set

public var cardView: View
    get() {
        TODO()
    }
    set(value) {}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(
    photoArray: MutableList<Uri>,
    teamName: String,
    teamNumber: String,
    driveType: String,
    intakePref: String,
    robotWidth: String,
    robotLength: String,
    ampStrength: Boolean,
    speakerStrength: Boolean,
    climbStrength: Boolean,
    trapStrength: Boolean,
    concerns: String,
    scout: String
){
    //SwipeToDismissBox(state = SwipeToDismissBoxState()) {
    Card(colors = CardDefaults.cardColors(containerColor = Color(15, 15, 15))) {
            Column {
                AsyncImage(
                    model = photoArray[0],
                    contentDescription = "Robot Image",
                    modifier = Modifier.clip(RoundedCornerShape(7.5.dp))
                )
                HorizontalDivider(color = Color.Gray)
                Row {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "$teamName ",
                            fontSize = 20.sp,
                            color = defaultOnPrimary
                        )
                        Text(
                            text = teamNumber,
                            fontSize = 20.sp,
                            color = defaultPrimaryVariant,
                            modifier = Modifier.align(Alignment.CenterEnd)
                        )
                    }
                }
                HorizontalDivider(color = Color(150, 150, 150))
                Text(
                    text = "About Me:",
                    fontSize = 20.sp,
                    color = defaultOnPrimary
                )
                Text(
                    text = "I am $robotWidth by $robotLength, I like to intake using $intakePref. I enjoy long, luxurious walks on the beach with my intense $driveType drive. As you'll find out I am very efficient in multiple ways;\n Amp: $ampStrength \n Speaker: $speakerStrength \n Climb: $climbStrength \n Trap: $trapStrength \n You should generally be concerned about my $concerns.",
                    color = defaultOnPrimary
                )
                Row(modifier = Modifier.horizontalScroll(ScrollState(0))) {
                    photoArray.forEach {
                        AsyncImage(
                            model = it,
                            contentDescription = "Robot image",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.size(200.dp, 300.dp)
                                .clip(
                                    RoundedCornerShape(7.5.dp)
                                )
                        )
                    }
                }
                Text(text = "Scout: $scout")
            }
        val cardView = LocalView.current
        OutlinedButton(onClick = { generateBitmap(cardView) }) { Text(text = "Download", color = defaultOnPrimary) }


    }
}