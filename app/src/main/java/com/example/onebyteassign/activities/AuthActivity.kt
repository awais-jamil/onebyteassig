package com.example.onebyteassign.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.example.onebyteassign.R

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val navController = findNavController(R.id.nav_host_fragment)

        val topLevelScreens = setOf(
            R.id.authFragment
        )

//        val appBarConfiguration = AppBarConfiguration.Builder(topLevelScreens).build()

//        setupActionBarWithNavController(navController, appBarConfiguration)
    }
}
