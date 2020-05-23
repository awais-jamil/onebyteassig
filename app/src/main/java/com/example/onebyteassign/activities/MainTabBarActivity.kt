package com.example.onebyteassign.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.onebyteassign.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainTabBarActivity : AppCompatActivity() {

    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_tab_bar)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar = findViewById<View>(R.id.toolbar) as Toolbar

        val navController = findNavController(R.id.nav_host_fragment)

        bottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigationView.isItemHorizontalTranslationEnabled = false

        bottomNavigationView.setupWithNavController(navController)

        val topLevelScreens = setOf(
            R.id.homeFragment,
            R.id.homeFragment,
            R.id.homeFragment,
            R.id.settingsFragment
        )

        val appBarConfiguration = AppBarConfiguration.Builder(topLevelScreens).build()

        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {

        return findNavController(R.id.nav_host_fragment).navigateUp()
    }
}
