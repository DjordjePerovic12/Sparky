package ltd.bokadev.sparky_social_media.core.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ltd.bokadev.sparky_social_media.ui.theme.SparkyTheme

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    padding: Dp = 0.dp,
    text: String,
    color: Color,
    enabled: Boolean = true,
    borderColor: Color,
    textColor: Color,
    textStyle: TextStyle,
    shouldShowCircularProgressIndicator: Boolean = false,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        ),
        enabled = enabled,
        contentPadding = PaddingValues(),
        shape = RoundedCornerShape(11.dp),
        border = BorderStroke(1.dp, borderColor),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .background(color)
                .padding(padding)
                .fillMaxSize()
        ) {
            if (!shouldShowCircularProgressIndicator) Text(
                text = text,
                modifier = Modifier.align(Alignment.Center),
                style = textStyle,
                color = textColor,
                maxLines = 2
            )
            else CircularProgressIndicator(
                color = SparkyTheme.colors.primaryColor, modifier = Modifier.align(
                    Alignment.Center
                )
            )
        }
    }
}