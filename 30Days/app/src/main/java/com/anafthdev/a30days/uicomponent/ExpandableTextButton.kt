package com.anafthdev.a30days.uicomponent

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withAnnotation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview

@Preview(showSystemUi = true)
@Composable
private fun ExpandableTextButtonPreview() {
    ExpandableTextButton(
        maxLines = 2,
        text = "Dedicate focused time to map out your 30-day clean eating journey. Allocate 30 " +
                "minutes for reflection and planning. Create a visual board or write down your goals, " +
                "intentions, and a rough schedule for the coming days. Consider your dietary " +
                "preferences and health objectives while setting achievable and realistic milestones" +
                " for each week. This session will help establish a clear direction and set the tone" +
                " for your successful 30-day clean eating commitment.",
        modifier = Modifier
            .fillMaxWidth()
    )
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun ExpandableTextButton(
    text: String,
    modifier: Modifier = Modifier,
    moreText: String = "More",
    lessText: String = "Less",
    overflow: TextOverflow = TextOverflow.Ellipsis,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    style: TextStyle = LocalTextStyle.current
) {

    // Variable to store a boolean whether the text is expandable or not
    var expandable by rememberSaveable { mutableStateOf(false) }

    // True if expanded, false otherwise
    var expanded by rememberSaveable { mutableStateOf(false) }

    var truncatedText by rememberSaveable { mutableStateOf(text) }

    val annotatedString = buildAnnotatedString {
        if (expandable) {
            // If not expanded, use truncated text, otherwise use text
            append(if (!expanded) "$truncatedText... " else "$text ")

            // Add more or less button
            withStyle(style.copy(color = MaterialTheme.colorScheme.primary).toSpanStyle()) {
                withAnnotation("expand_button", "annotation") {
                    append(if (!expanded) moreText else lessText)
                }
            }
        } else append(text) // If text not expandable, use "text"
    }

    Row(modifier = modifier) {
        ClickableText(
            text = annotatedString,
            overflow = overflow,
            softWrap = softWrap,
            maxLines = if (expanded) Int.MAX_VALUE else maxLines,
            style = style,
            onClick = { offset ->
                annotatedString.getStringAnnotations(offset, offset).firstOrNull()?.tag?.let { tag ->
                    expanded = !expanded
                }
            },
            onTextLayout = { result ->
                if (!expandable && result.hasVisualOverflow) {
                    expandable = true
                    truncatedText = text.substring(
                        startIndex = 0,
                        endIndex = result.getLineEnd(
                            lineIndex = maxLines - 2,
                            visibleEnd = true
                        ) - (moreText.length + 1 + 3) // end offset - (more text length + spaces + three dots)
                    )
                }
            }
        )
    }
}
