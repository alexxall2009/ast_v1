package com.example.myastjson_v3.Nav

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myastjson_v3.Auth.LoginScreen
import com.example.myastjson_v3.Dates.RootJson
import com.example.myastjson_v3.Screens.Screen
import com.example.myastjson_v3.Screens.Screen1.Screen2.ScreenMnemoshema
import com.example.myastjson_v3.Screens.Screen1.Screen2.Screen3.DetailElementScreen
import com.example.myastjson_v3.Screens.Screen1.ScreenLevel1

@Composable
fun AppNavigation(viewModel: RootJson = viewModel()) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Login.route) {

        // Экран авторизации
        composable(Screen.Login.route) {
            LoginScreen(onLoginSuccess={
                navController.navigate(Screen.List.route)
            })
        }

        // ЭКРАН 0: Список КП
        composable(Screen.List.route) {
            Screen(viewModel = viewModel,
                onItemClick = {rootId ->
                    navController.navigate(Screen.Detail.createRoute(rootId)) })
        }
        // ЭКРАН 1: Список
        composable(Screen.ListScreenCp.route) {
            ScreenLevel1(
                viewModel = viewModel,
                onItemClick = { rootId ->
                    navController.navigate(Screen.Detail.createRoute(rootId))
                }
            )
        }
        // ЭКРАН 2: Детали (передаем аргумент rootId)
        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("rootId") { type = NavType.IntType })
        ) { backStackEntry ->
            val rootId = backStackEntry.arguments?.getInt("rootId") ?: 0
            ScreenMnemoshema(rootId = rootId, viewModel = viewModel, onBack = {
                navController.popBackStack()
            }, onClickElement = { elementId ->
                navController.navigate(Screen.DetailElements.createRoute(rootId, elementId)) })
        }
        // ЭКРАН 3: Детали (передаем аргумент rootId)
        composable(
            route = Screen.DetailElements.route,
            arguments = listOf(navArgument("rootId") { type = NavType.IntType },
                navArgument("elementId") { type = NavType.IntType }

            )

        ) { backStackEntry ->
            val rootId = backStackEntry.arguments?.getInt("rootId") ?: 0
            val elementId = backStackEntry.arguments?.getInt("elementId") ?: 0
            DetailElementScreen(rootId = rootId, elementId = elementId, viewModel = viewModel, onBack = {
                navController.popBackStack()
            })
        }



    }
}

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object List : Screen("list")
    object ListScreenCp: Screen("listScreenCp")

    object Detail : Screen("detail/{rootId}") {
        fun createRoute(rootId: Int) = "detail/$rootId"
    }
    object DetailElements : Screen("detail/{rootId}/{elementId}") {
        fun createRoute(rootId: Int, elementId: Int) = "detail/$rootId/$elementId"
    }
}