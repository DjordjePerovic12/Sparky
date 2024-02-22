package ltd.bokadev.sparky_social_media.presentation.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ltd.bokadev.sparky_social_media.R
import ltd.bokadev.sparky_social_media.ui.theme.SparkyTheme

@Composable
fun SparkyPostItem(
    userFullName: String, createdAt: String, postContent: String, likes: Int, comments: Int
) {
    Card(
        shape = RoundedCornerShape(30.dp), colors = CardColors(
            containerColor = SparkyTheme.colors.primaryColor,
            contentColor = SparkyTheme.colors.white,
            disabledContainerColor = SparkyTheme.colors.primaryColor,
            disabledContentColor = SparkyTheme.colors.white
        ), modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    UserImageItem(userFullName = "Petar Gajevic")

                    Text(
                        text = userFullName,
                        color = SparkyTheme.colors.white,
                        style = SparkyTheme.typography.poppinsRegular16
                    )
                }
                Text(
                    text = createdAt,
                    color = SparkyTheme.colors.white,
                    style = SparkyTheme.typography.poppinsRegular12
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = postContent,
                color = SparkyTheme.colors.white,
                style = SparkyTheme.typography.poppinsRegular16
            )

            Spacer(modifier = Modifier.height(8.dp))

            Spacer(
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .fillMaxWidth()
                    .background(SparkyTheme.colors.white)
                    .height(1.dp)
            )

            Spacer(modifier = Modifier.height(15.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .height(25.dp)
                        .width(IntrinsicSize.Max)
                        .border(
                            width = 1.dp,
                            color = SparkyTheme.colors.white.copy(0.7f),
                            shape = RoundedCornerShape(20.dp)
                        ),
                    colors = CardColors(
                        contentColor = SparkyTheme.colors.white,
                        containerColor = SparkyTheme.colors.primaryColor,
                        disabledContentColor = SparkyTheme.colors.white,
                        disabledContainerColor = SparkyTheme.colors.primaryColor
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(7.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp)
                            .padding(5.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_like),
                            contentDescription = null
                        )

                        Text(
                            text = likes.toString() + "k",
                            style = SparkyTheme.typography.poppinsMedium12,
                            color = SparkyTheme.colors.white
                        )
                    }
                }
                Card(
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .height(25.dp)
                        .width(IntrinsicSize.Max)
                        .border(
                            width = 1.dp,
                            color = SparkyTheme.colors.white.copy(0.7f),
                            shape = RoundedCornerShape(20.dp)
                        ),
                    colors = CardColors(
                        contentColor = SparkyTheme.colors.white,
                        containerColor = SparkyTheme.colors.primaryColor,
                        disabledContentColor = SparkyTheme.colors.white,
                        disabledContainerColor = SparkyTheme.colors.primaryColor
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(7.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp)
                            .padding(5.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_comment),
                            contentDescription = null
                        )

                        Text(
                            text = comments.toString() + "k",
                            style = SparkyTheme.typography.poppinsMedium12,
                            color = SparkyTheme.colors.white
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}