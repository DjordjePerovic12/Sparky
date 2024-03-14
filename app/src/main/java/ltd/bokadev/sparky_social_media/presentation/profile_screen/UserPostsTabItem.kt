package ltd.bokadev.sparky_social_media.presentation.profile_screen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ltd.bokadev.sparky_social_media.R
import ltd.bokadev.sparky_social_media.core.utils.PostFilters
import ltd.bokadev.sparky_social_media.core.utils.ProfileScreenType
import ltd.bokadev.sparky_social_media.ui.theme.SparkyTheme

@Composable
fun UserPostsTabItem(
    isSelected: Boolean,
    filter: PostFilters,
    profileScreenType: ProfileScreenType,
    onClick: (PostFilters) -> Unit
) {
    Card(
        modifier = Modifier
            .size(width = 175.dp, height = 70.dp)
            .padding(vertical = 8.dp)
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(100.dp),
                color = if (isSelected) SparkyTheme.colors.yellow else SparkyTheme.colors.bottomNavigationUnselected
            )
            .clickable {
                onClick(filter)
            },
        shape = RoundedCornerShape(100.dp),
        colors = CardColors(
            containerColor = if (isSelected) SparkyTheme.colors.yellow else SparkyTheme.colors.white,
            contentColor = SparkyTheme.colors.primaryColor,
            disabledContentColor = SparkyTheme.colors.primaryColor,
            disabledContainerColor = if (isSelected) SparkyTheme.colors.yellow else SparkyTheme.colors.white
        ),
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = when (profileScreenType) {
                    ProfileScreenType.LOCAL_USER -> {
                        filter.title
                    }

                    ProfileScreenType.REMOTE_USER -> {
                        if (filter.id == 0) stringResource(R.string.posts) else filter.title
                    }
                },
                color = SparkyTheme.colors.primaryColor,
                style = if (isSelected) SparkyTheme.typography.poppinsMedium16 else SparkyTheme.typography.poppinsRegular16
            )
        }
    }
}