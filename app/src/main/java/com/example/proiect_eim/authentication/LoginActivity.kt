package com.example.proiect_eim.authentication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.proiect_eim.DashboardActivity
import com.example.proiect_eim.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseAuth.getInstance().currentUser?.let{
            goToDashboardActivity()
        } ?: run {
            setUpUI()
        }

    }

    private fun setUpUI() {
        setContentView(R.layout.activity_login)

        reset_btn.setOnClickListener{
            val sharedPreferences = getSharedPreferences("IntroDone", Context.MODE_PRIVATE)
            sharedPreferences?.edit()?.putBoolean("introDoneFlag", false)?.apply()
        }

        login_email_et.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                login_email_et.hint = "Email"
                login_email_et.background = ContextCompat.getDrawable(this,
                    R.drawable.et_focus_background)
            } else {
                login_email_et.hint = ""
            }
        }

        login_password_et.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                login_password_et.hint = "Email"
                login_password_et.background = ContextCompat.getDrawable(this,
                    R.drawable.et_focus_background)
            } else {
                login_password_et.hint = ""
            }
        }

        setTextChangeListener(login_email_layout)
        setTextChangeListener(login_password_layout)

        login_btn?.setOnClickListener{
            loginUser()
        }

        no_acc_btn.setOnClickListener{
            goToRegisterActivity()
        }
    }

    private fun setTextChangeListener(textInputLayout: TextInputLayout?) {
        textInputLayout?.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (login_password_et.text.toString().isEmpty() ||
                        login_email_et.text.toString().isEmpty()) {
                    login_btn.isEnabled = false
                    login_btn.background = ContextCompat.getDrawable(this@LoginActivity,
                            R.drawable.rounded_background_disabled)
                } else {
                    login_btn.isEnabled = true
                    login_btn.background = ContextCompat.getDrawable(this@LoginActivity,
                            R.drawable.rounded_background)
                }
            }
        })
    }

    private fun loginUser() {
        val auth = Firebase.auth

        val email = login_email_et.text.toString()
        val password = login_password_et.text.toString()

        val validEmail = checkEmailValidity(email)
        displayErrorMessage(validEmail, login_email_et, "Please enter a valid email address")

        val validPasswordSize = verifyPasswordSize(password)
        displayErrorMessage(validPasswordSize, login_password_et, "Password should contain between 6 and 14 characters")

        val containingSymbol = verifySymbolOccurrence(password)
        displayErrorMessage(containingSymbol, login_password_et, "Password should contain at least one special character")

        val containingUpperCaseLetter = verifyUpperCaseOccurrence(password)
        displayErrorMessage(containingUpperCaseLetter, login_password_et, "Password should contain at least one upper case letter")


        if (!validPasswordSize || !containingSymbol || !containingUpperCaseLetter || !validEmail)
            return

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this){ task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Logged in", Toast.LENGTH_SHORT).show()
                goToDashboardActivity()
            } else {
                Toast.makeText(this, "Error: " + task.exception?.message, Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun displayErrorMessage(isValid : Boolean, editText: EditText, string: String) {
        if (!isValid) {
            editText.error = string
            Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkEmailValidity(email: String): Boolean {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return false
        }
        return true
    }

    fun verifyUpperCaseOccurrence(password: String): Boolean {
        val upperCaseRegex = ".*[A-Z].*".toRegex()
        if (!password.matches(upperCaseRegex)){
            return false
        }
        return true
    }

    fun verifySymbolOccurrence(password: String): Boolean {
        val symbolRegex = ".*[*.!@#\$%^&(){}:\";'<>,?/~`_+-=|].*".toRegex()
        if (!password.matches(symbolRegex)){
            return false
        }
        return true
    }

    fun verifyPasswordSize(password : String) : Boolean {
        if (password.length < 6 || password.length > 14 ){
            return false
        }
        return true
    }

    private fun goToDashboardActivity() {
        startActivity(Intent(this, DashboardActivity::class.java))
        this.finish()
    }


    private fun goToRegisterActivity() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

}