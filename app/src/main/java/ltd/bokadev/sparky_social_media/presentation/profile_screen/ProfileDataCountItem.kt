package ltd.bokadev.sparky_social_media.presentation.profile_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ltd.bokadev.sparky_social_media.ui.theme.SparkyTheme

@Composable
fun ProfileDataCountItem(
    count: String, countedData: String
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = count,
            color = SparkyTheme.colors.white,
            style = SparkyTheme.typography.poppinsRegular16,
            textAlign = TextAlign.Center
        )
        Text(
            text = countedData,
            color = SparkyTheme.colors.white,
            style = SparkyTheme.typography.poppinsRegular16,
            textAlign = TextAlign.Center
        )
    }
}