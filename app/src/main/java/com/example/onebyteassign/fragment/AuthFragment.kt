package com.example.onebyteassign.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer

import com.example.onebyteassign.R
import com.example.onebyteassign.activities.AuthActivity
import com.example.onebyteassign.activities.MainTabBarActivity
import com.example.onebyteassign.supports.BaseFragment
import com.example.onebyteassign.viewmodels.AuthViewModel
import kotlinx.android.synthetic.main.auth_fragment.view.*

class AuthFragment : BaseFragment() {

    val viewModel by activityViewModels<AuthViewModel>()

    lateinit var signUpView: LinearLayout
    lateinit var loginView: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.auth_fragment, container, false)

        loginView = view.findViewById(R.id.login_view)
        loginView.visibility = View.VISIBLE
        signUpView = view.findViewById(R.id.signup_view)
        signUpView.visibility = View.GONE

        view.btn_login.setOnClickListener {
            if(!view.input_email.text.toString().equals("") && !view.input_password.text.toString().equals("")){
                displayLoadingIndicator("Loading...")
                viewModel.login(view.input_email.text.toString(), view.input_password.text.toString())
            }
        }

        view.btn_signup.setOnClickListener {
            if(!view.s_input_email.text.toString().equals("") && !view.s_input_password.text.toString().equals("") && !view.s_input_name.text.toString().equals("") && !view.s_input_phone.text.toString().equals("")){
                displayLoadingIndicator("Signing Up...")
                viewModel.signup(view.s_input_email.text.toString(), view.s_input_password.text.toString(), view.s_input_name.text.toString(), view.s_input_phone.text.toString())
            }
        }

        view.link_signin.setOnClickListener {
            loginView.visibility = View.VISIBLE
            signUpView.visibility = View.GONE
        }

        view.link_signup.setOnClickListener {
            loginView.visibility = View.GONE
            signUpView.visibility = View.VISIBLE
        }

        viewModel.authState.observe(
            viewLifecycleOwner,
            Observer { state ->

                if(state == AuthViewModel.authStatus.Success) {
                    hideLoadingIndicator()

                    val intent = Intent(context, MainTabBarActivity::class.java)

                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

                    startActivity(intent)

                    activity!!.finish()

                }

                else if(state == AuthViewModel.authStatus.Error) {
                    hideLoadingIndicator()
                    showPrompt(context!!, "Error!", "Request Failed")
                }
            })


        return view
    }
}
