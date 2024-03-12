package ltd.bokadev.sparky_social_media.core.utils

enum class TopBarStyle(val style: String) {
    DEFAULT(style = "Default"),
    HOME(style = "Home"),
    PROFILE(style = "Profile"),
    SEARCH(style = "Search")
}

enum class PostFilters(val id: Int, val title: String) {
    YOUR_POSTS(id = 0, title = "Your posts"),
    LIKED_POSTS(id = 1, title = "Liked posts")
}

enum class ProfileScreenType(val type: String) {
    LOCAL_USER(type = "Local"),
    REMOTE_USER(type = "Remote")
}

enum class FollowUnfollowButtonStyle(val style: String) {
    SEARCH_SCREEN(style = "Search"),
    REMOTE_USER_PROFILE_SCREEN(style = "Profile")
}