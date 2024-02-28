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