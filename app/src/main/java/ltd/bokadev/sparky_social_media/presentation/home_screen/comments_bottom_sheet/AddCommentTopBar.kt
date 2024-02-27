package ltd.bokadev.sparky_social_media.presentation.home_screen.comments_bottom_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ltd.bokadev.sparky_social_media.R
import ltd.bokadev.sparky_social_media.ui.theme.SparkyTheme

@Composable
fun AddCommentTopBar() {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .systemBarsPadding()
            .padding(top = 15.dp)
            .clip(RoundedCornerShape(topEnd = 24.dp, topStart = 24.dp))
            .background(SparkyTheme.colors.primaryColor)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(SparkyTheme.colors.primaryColor)
        ) {
            Spacer(
                modifier = Modifier
                    .size(height = 5.dp, width = 35.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .background(SparkyTheme.colors.lightGray)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .background(SparkyTheme.colors.primaryColor)
        ) {
            Text(
                text = stringResource(R.string.comments),
                style = SparkyTheme.typography.poppinsMedium16,
                color = SparkyTheme.colors.white,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(2f)
            )
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .background(SparkyTheme.colors.lightGray)
                .height(1.dp)
        )
    }
}