package com.example.onebyteassign.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.onebyteassign.R
import com.example.onebyteassign.models.OBCurrentUser
import com.example.onebyteassign.networkLayer.OBAuthenticationService

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        if(OBAuthenticationService.needsAuthentication()) {

            goToOnBoarding()

        }

        else {

            proceedToLoggedFlow()

        }
    }

    fun goToOnBoarding(){
        val intent = Intent(applicationContext, AuthActivity::class.java)

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        Handler().postDelayed(Runnable {
            startActivity(intent)
            finish()

        }, 1000)

    }

    fun proceedToLoggedFlow(){

        OBCurrentUser.reloadUser()

        val intent = Intent(applicationContext, MainTabBarActivity::class.java)

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        Handler().postDelayed(Runnable {
            startActivity(intent)
            finish()

        }, 1000)
    }

}
