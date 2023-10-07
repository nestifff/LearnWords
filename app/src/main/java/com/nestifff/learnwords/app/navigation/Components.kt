package com.nestifff.learnwords.app.navigation

// region Graphs
private const val MainGraphRoute = "main_graph"
private const val SettingsGraphRoute = "settings_graph"
// endregion
// region Destinations
private const val CollectionScreenRoute = "collection_screen"
private const val SettingsScreenRoute = "settings_screen"
private const val LearnScreenRoute = "learn_screen"
// endregion

sealed class Graph(val route: String) {
    data object MainGraph : Graph(MainGraphRoute)
    data object SettingsGraph : Graph(SettingsGraphRoute)
}

sealed class Destination(val route: String) {
    data object CollectionDestination : Destination(CollectionScreenRoute)
    data object SettingsDestination : Destination(SettingsScreenRoute)
    data object LearnDestination : Destination(LearnScreenRoute)
}
