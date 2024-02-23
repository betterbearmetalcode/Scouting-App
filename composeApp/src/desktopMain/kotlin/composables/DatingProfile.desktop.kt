package composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import defaultOnPrimary
import defaultPrimaryVariant
import java.awt.image.BufferedImage

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Profile(
    photoArray: MutableState<ArrayList<ImageBitmap>>,
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
    SwipeToDismiss(background = {}, state = DismissState(DismissValue.Default)) {
        Card(elevation = 2.dp, backgroundColor = Color(15, 15, 15)) {
            Column {
                Image(painter = BitmapPainter(photoArray.value[0]), contentDescription = "Robot Image", modifier = Modifier.clip(RoundedCornerShape(7.5.dp)))
                Divider(color = Color.Gray)
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
                Divider(color = Color(150, 150, 150))
                Text(
                    text = "About Me:",
                    fontSize = 20.sp
                )
                Text(
                    text = "I am $robotWidth by $robotLength, I like to intake using $intakePref. I enjoy long, luxurious walks on the beach with my intense $driveType drive. As you'll find out I am very efficient in multiple ways;\n Amp: $ampStrength \n Speaker: $speakerStrength \n Climb: $climbStrength \n Trap: $trapStrength \n You should generally be concerned about my $concerns."
                )
                Row(modifier = Modifier.horizontalScroll(ScrollState(0))) {
                    photoArray.value.forEach {
                        Image(
                            painter = BitmapPainter(it),
                            contentDescription = "Robot image",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxSize(1.25f)
                                .clip(RoundedCornerShape(7.5.dp))
                        )
                    }
                }
                Text(text = "Scout: $scout")
            }
        }
    }
}