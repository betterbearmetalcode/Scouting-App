import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalTextApi::class, ExperimentalResourceApi::class)
@Composable
fun LinkMaker(
label: String,
url: String,
color: Color,
//content: Unit
){
    val annotatedLinkString: AnnotatedString = buildAnnotatedString {
        val str = "$label $url"
        val startIndex = str.indexOf(url)
        var urlLegnth = url.length
        val endIndex = startIndex+ urlLegnth
        val urlAnnotation = UrlAnnotation(url)
        append(str)
        addStyle(
            style = SpanStyle(
                color = color,
                textDecoration = TextDecoration.Underline
            ), start = startIndex, end = endIndex
        )
        addUrlAnnotation(
            urlAnnotation = urlAnnotation,
            start = startIndex,
            end = endIndex
        )
    }
    val uriHandler = LocalUriHandler.current
    ClickableText(
        text = annotatedLinkString,
        onClick = {
            annotatedLinkString
                .getUrlAnnotations(it,it)
                .firstOrNull()?.let { annotation ->
                    uriHandler.openUri(annotation.item.url)
                }
        }
    )
}

@OptIn(ExperimentalResourceApi::class, ExperimentalTextApi::class)
@Composable
fun LinkMaker2(
    url: String,
    pictureLocation: String,
    Modifier: Modifier
//content: Unit
){
    var urlLegnth = url.length
    val startIndex = url.indexOf(url)
    val endIndex = startIndex+ urlLegnth
    val urlAnnotation = UrlAnnotation(url)
    val annotatedLinkString: AnnotatedString = buildAnnotatedString {
        addUrlAnnotation(
            UrlAnnotation(url),
            start = startIndex,
            end = endIndex
            )
    }
    val uriHandler = LocalUriHandler.current
    TextButton(
        onClick = {
            annotatedLinkString
                .getUrlAnnotations(startIndex,endIndex)
                .firstOrNull()?.let { annotation ->
                    uriHandler.openUri(annotation.item.url)
                }
        }
    ){
        Image(
            painter = painterResource(res = pictureLocation ),
            contentDescription = pictureLocation,
            modifier = Modifier
        )

    }
}