package com.nestifff.learnwords.app.navigation.core

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry

interface NavGraphRoot<Args> {
    val route: String
    val startingDestination: Destination<Args>
    fun prepareRoute(arg: Args): String = startingDestination.prepareRoute(arg)
}

interface Destination<T> {
    val route: String
    val arguments: List<NamedNavArgument>
    fun prepareRoute(arg: T): String
    fun getArgs(navBackStackEntry: NavBackStackEntry): T
}

interface NoArgsDestination : Destination<Unit> {

    override val arguments: List<NamedNavArgument>
        get() = emptyList()

    override fun prepareRoute(arg: Unit): String = route

    override fun getArgs(navBackStackEntry: NavBackStackEntry): Nothing {
        throw IllegalArgumentException("There is no args")
    }
}
