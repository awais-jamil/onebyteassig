package com.example.onebyteassign.activities

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.onebyteassign.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class MainTabBarActivity : AppCompatActivity() {

    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var toolbar: Toolbar
    private lateinit var toggle: ActionBarDrawerToggle

    var mDrawerLayout: DrawerLayout? = null
    var nv: NavigationView? = null

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_tab_bar)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.menu);

        toolbar = findViewById<View>(R.id.toolbar) as Toolbar

        val navController = findNavController(R.id.nav_host_fragment)

        findViewById<NavigationView>(R.id.nav_view)
            .setupWithNavController(navController)

        mDrawerLayout = findViewById(R.id.layout) as (DrawerLayout)

        nv = findViewById(R.id.nav_view) as (NavigationView)
        nv!!.setNavigationItemSelectedListener(navSelectListener)

        toggle = ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close)

        mDrawerLayout!!.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        bottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigationView.isItemHorizontalTranslationEnabled = true

        bottomNavigationView.setupWithNavController(navController)

        val topLevelScreens = setOf(
            R.id.homeFragment,
            R.id.settingsFragment
        )

        val appBarConfiguration = AppBarConfiguration.Builder(topLevelScreens).build()

        setupActionBarWithNavController(navController, appBarConfiguration)
//        val headerView: View = mDrawerLayout!!.nav_header
//        val navUsername =
//            headerView.findViewById<View>(R.id.nav_header_textView) as TextView
//        navUsername.text = OBCurrentUser.user.name
    }

    override fun onSupportNavigateUp(): Boolean {

        return findNavController(R.id.nav_host_fragment).navigateUp()
    }

    public fun openDrawer(){
        mDrawerLayout!!.openDrawer(nv!!)
    }

    private val navSelectListener = object : NavigationView.OnNavigationItemSelectedListener {

        override fun onNavigationItemSelected(item: MenuItem): Boolean {

            item.setChecked(true)
            mDrawerLayout!!.closeDrawer(nv!!)

            when(item.itemId) {
                R.id.home -> {


                    true
                }
                R.id.fav -> {


                    true
                }

                R.id.series -> {


                    true
                }

            }
            return true
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
