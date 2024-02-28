package ltd.bokadev.sparky_social_media.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import ltd.bokadev.sparky_social_media.R
import ltd.bokadev.sparky_social_media.ui.theme.SparkyTheme

@Composable
fun LogoutAlertDialog(
    modifier: Modifier = Modifier,
    headerText: String,
    messageText: String,
    shouldShow: Boolean,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit
) {
    if (shouldShow)
        AlertDialog(
            onDismissRequest = { onDismissRequest() }, buttons = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 25.dp)
                        .padding(bottom = 15.dp)
                ) {
                    Column(
                        modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center
                    ) {
                        PrimaryButton(
                            Modifier.size(width = 150.dp, height = 55.dp),
                            text = stringResource(R.string.confirm),
                            color = SparkyTheme.colors.yellow,
                            textStyle = SparkyTheme.typography.poppinsRegular14,
                            textColor = SparkyTheme.colors.primaryColor,
                            borderColor = SparkyTheme.colors.yellow
                        ) {
                            onDismissRequest()
                            onConfirm()
                        }
                    }
                    Column(
                        modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center
                    ) {
                        PrimaryButton(
                            Modifier.size(width = 150.dp, height = 55.dp),
                            text = stringResource(R.string.close),
                            color = SparkyTheme.colors.white,
                            textStyle = SparkyTheme.typography.poppinsRegular14,
                            textColor = SparkyTheme.colors.primaryColor,
                            borderColor = SparkyTheme.colors.yellow
                        ) {
                            onDismissRequest()
                        }
                    }
                }
            }, title = {
                Text(
                    text = headerText,
                    color = SparkyTheme.colors.primaryColor,
                    style = SparkyTheme.typography.poppinsRegular14
                )
            }, text = {
                Text(
                    text = messageText,
                    color = SparkyTheme.colors.primaryColor,
                    style = SparkyTheme.typography.poppinsRegular12,
                    maxLines = Int.MAX_VALUE,
                    textAlign = TextAlign.Start
                )
            },
            properties = DialogProperties(
                dismissOnBackPress = true, dismissOnClickOutside = true
            ),
            modifier = modifier
        )
}