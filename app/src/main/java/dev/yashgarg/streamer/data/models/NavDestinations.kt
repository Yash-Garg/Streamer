package dev.yashgarg.streamer.data.models

sealed class NavDestinations(val title: String, val route: String) {
    object HomeScreen : NavDestinations("Home", "home")
    object ConfigScreen : NavDestinations("Configure", "configure")
    object PlayerScreen : NavDestinations("Player", "player")
}
