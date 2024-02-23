package ltd.bokadev.sparky_social_media.presentation.search_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ltd.bokadev.sparky_social_media.R
import ltd.bokadev.sparky_social_media.ui.theme.SparkyTheme

@Composable
fun ButtonUnfollow(onClick: () -> Unit) {
    Button(
        modifier = Modifier.size(width = 75.dp, height = 35.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = SparkyTheme.colors.white
        ),
        enabled = true,
        contentPadding = PaddingValues(),
        shape = RoundedCornerShape(11.dp),
        border = BorderStroke(1.dp, SparkyTheme.colors.red),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .background(SparkyTheme.colors.white)
                .padding(vertical = 8.dp, horizontal = 12.dp)
                .fillMaxSize()
        ) {
            Text(
                text = stringResource(R.string.unfollow),
                modifier = Modifier.align(Alignment.Center),
                style = SparkyTheme.typography.poppinsRegular12,
                color = SparkyTheme.colors.red
            )

        }
    }
}