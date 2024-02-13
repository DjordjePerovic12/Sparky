package ltd.bokadev.sparky_social_media.core.utils

import ltd.bokadev.sparky_social_media.BuildConfig.BASE_URL
import ltd.bokadev.sparky_social_media.core.utils.Constants.NO_INFO
import okhttp3.Request

fun String?.toNonNull() = if (this.isNullOrEmpty()) NO_INFO else this

fun Int?.toNonNull() = this ?: -1

fun Double?.toNonNull() = this ?: 0.0

infix fun Request.signWithToken(accessToken: String?): Request {
    val builder = newBuilder().header("Accept", "application/json")
    if (this.url.toString()
            .contains(BASE_URL) && !accessToken.isNullOrEmpty() && !this.url.encodedPath.contains(
            "login"
        )
    ) {
        builder.header("Authorization", "Bearer $accessToken")
    }
    return builder.build()
}
