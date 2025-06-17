package com.example.movie_app

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.movie_app.presentation.navigation.AppNavHostGraph
import com.example.movie_app.presentation.theme.MovieAppTheme
import com.example.utils_extension.events.BaseUiEvents
import com.example.utils_extension.events.Events
import com.example.utils_extension.extensions.context_extensions.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window,false)
        fullScreen()
        setContent {
            val navController = rememberNavController()
            MovieAppTheme {
                AppNavHostGraph(navController = navController)
            }
        }
        observeEvent()
    }

    private fun fullScreen(){
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT,Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT,Color.TRANSPARENT)
        )
    }

    private fun observeEvent(){
        lifecycleScope.launch(Dispatchers.Main) {
            Events.baseEventFlow.collectLatest {
                when(it){
                    is BaseUiEvents.ShowToast->{
                        showToast(it.message)
                    }
                    else->Unit
                }
            }
        }
    }
}