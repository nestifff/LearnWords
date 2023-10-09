package com.nestifff.learnwords.app.navigation.destinations

import android.util.Log
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.nestifff.learnwords.app.di.utils.daggerViewModel
import com.nestifff.learnwords.app.navigation.core.Destination
import com.nestifff.learnwords.app.navigation.graphs.MainNavGraph
import com.nestifff.learnwords.ext.getApplication
import com.nestifff.learnwords.presentation.model.CollectionType
import com.nestifff.learnwords.presentation.model.WayToLearn
import com.nestifff.learnwords.presentation.screen.learn.LearnScreen
import com.nestifff.learnwords.presentation.screen.learn.LearnViewModel
import com.nestifff.learnwords.presentation.screen.learn.di.learnViewModelFactory
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object LearnScreenDestination : Destination<LearnScreenArgument> {

    private const val ArgumentKey = "argument_key"

    private val routeWithoutArguments = "${MainNavGraph.route}/learn"

    override val route: String = "$routeWithoutArguments/{$ArgumentKey}"

    override val arguments: List<NamedNavArgument>
        get() = listOf(
            navArgument(ArgumentKey) { type = NavType.StringType },
        )

    override fun getArgs(navBackStackEntry: NavBackStackEntry): LearnScreenArgument {
        val arguments = navBackStackEntry.arguments!!
        return Json.decodeFromString(arguments.getString(ArgumentKey)!!)
    }

    override fun prepareRoute(arg: LearnScreenArgument): String {
        val argument = Json.encodeToString(arg)
        return "$routeWithoutArguments/$argument"
    }
}

fun NavGraphBuilder.learnScreenDestination(
    navController: NavHostController,
) {
    composable(
        route = LearnScreenDestination.route,
    ) {

        val arg = LearnScreenDestination.getArgs(it)
        Log.i("Lalala", "learnScreenDestination: $arg")

        val daggerComponent = getApplication().appComponent.learnScreenComponent().create()
        val viewModel: LearnViewModel = daggerViewModel {
            learnViewModelFactory(daggerComponent.viewModelFactory, arg).create(LearnViewModel::class.java)
        }

        LearnScreen(viewModel = viewModel)
    }
}

@Serializable
data class LearnScreenArgument(
    val wordsNum: Int,
    val mayToLearn: WayToLearn,
    val collectionType: CollectionType
)
