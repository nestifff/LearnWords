package com.nestifff.learnwords.app.navigation

// region Graphs
private const val MainGraphRoute = "main_graph"
private const val SettingsGraphRoute = "settings_graph"
// endregion
// region Destinations
private const val CollectionScreenRoute = "collection_screen"
private const val SettingsScreenRoute = "settings_screen"
// endregion

sealed class Graph(val route: String) {
    object MainGraph : Graph(MainGraphRoute)
    object SettingsGraph : Graph(SettingsGraphRoute)
}

sealed class Destination(val route: String) {
    object CollectionDestination : Destination(CollectionScreenRoute)
    object SettingsDestination : Destination(SettingsScreenRoute)
}