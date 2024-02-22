package ltd.bokadev.sparky_social_media.presentation.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ltd.bokadev.sparky_social_media.R
import ltd.bokadev.sparky_social_media.core.components.PrimaryButton
import ltd.bokadev.sparky_social_media.ui.theme.SparkyTheme

@Composable
fun CreatePostBottomSheet() {
    Column(
        modifier = Modifier
            .height(410.dp)
            .systemBarsPadding()
            .background(SparkyTheme.colors.primaryColor)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
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
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Text(
                    text = stringResource(R.string.create_new_post),
                    style = SparkyTheme.typography.poppinsMedium16,
                    color = SparkyTheme.colors.white,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(2f)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
                    .padding(end = 20.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .size(40.dp)
                        .background(SparkyTheme.colors.white.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_cross),
                        contentDescription = null,
                        tint = SparkyTheme.colors.white,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

        }
//        Spacer(modifier = Modifier.height(10.dp))
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .background(SparkyTheme.colors.lightGray)
                .height(1.dp)
        )
//        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(R.string.message),
            color = SparkyTheme.colors.white,
            style = SparkyTheme.typography.poppinsMedium12,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        PostTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(140.dp)
        )

        Spacer(
            modifier = Modifier
                .height(20.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            PrimaryButton(
                text = stringResource(R.string.create_post),
                color = SparkyTheme.colors.yellow,
                borderColor = SparkyTheme.colors.yellow,
                textColor = SparkyTheme.colors.primaryColor,
                textStyle = SparkyTheme.typography.poppinsMedium16,
                modifier = Modifier.height(50.dp)
            ) {

            }
        }

        Spacer(
            modifier = Modifier
                .height(20.dp)
        )
    }
}