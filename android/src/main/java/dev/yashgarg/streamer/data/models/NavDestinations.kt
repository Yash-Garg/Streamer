package dev.yashgarg.streamer.data.models

sealed class NavDestinations(val title: String, val route: String) {
    data object HomeScreen : NavDestinations("Home", "home")

    data object ConfigScreen : NavDestinations("Configure", "configure")

    data object PlayerScreen : NavDestinations("Player", "player")

    data object GridPlayerScreen : NavDestinations("GridPlayer", "gridplayer")
}
