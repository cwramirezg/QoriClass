package com.cwramirezg.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.cwramirezg.core.navigation.ClassRoom
import com.cwramirezg.core.navigation.Course
import com.cwramirezg.core.navigation.Home
import com.cwramirezg.core.navigation.HomeNavGraph
import com.cwramirezg.core.navigation.Student
import com.cwramirezg.core.navigation.StudentImport
import com.cwramirezg.home.presentation.ui.screens.ClassRoomScreen
import com.cwramirezg.home.presentation.ui.screens.CourseScreen
import com.cwramirezg.home.presentation.ui.screens.HomeScreen
import com.cwramirezg.home.presentation.ui.screens.StudentImportScreen
import com.cwramirezg.home.presentation.ui.screens.StudentScreen

fun NavGraphBuilder.homeNavGraph(
    navController: NavController
) {
    navigation<HomeNavGraph>(
        startDestination = ClassRoom
    ) {
        composable<Home> {
            HomeScreen()
        }
        composable<Student> {
            StudentScreen()
        }
        composable<StudentImport> {
            StudentImportScreen()
        }
        composable<Course> {
            CourseScreen()
        }
        composable<ClassRoom> {
            ClassRoomScreen()
        }
    }
}