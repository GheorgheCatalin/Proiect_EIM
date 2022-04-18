package com.example.proiect_eim.authentication

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
import com.example.proiect_eim.objects.UserItem
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setUpEditTexts()

        buttonActivation()

        register_btn.setOnClickListener {
            registerUser()
        }
    }

    private fun setUpEditTexts() {
        setUpChangeListener(register_email_et, "Email")
        setUpChangeListener(register_password_et, "Password")
        setUpChangeListener(register_firstname_et, "First Name")
        setUpChangeListener(register_lastname_et, "Last Name")
        setUpChangeListener(register_country_et, "Country")
        setUpChangeListener(register_date_et, "Date")
    }

    private fun setUpChangeListener(et: EditText, hint: String) {
        et.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                et.hint = hint
                et.background = ContextCompat.getDrawable(this,
                    R.drawable.et_focus_background)
            } else {
                et.hint = ""
                et.background = ContextCompat.getDrawable(this,
                        R.drawable.et_background)
            }
        }
    }

    private fun buttonActivation() {
        setTextChangeListener(register_email_layout)
        setTextChangeListener(register_password_layout)
        setTextChangeListener(register_firstname_layout)
        setTextChangeListener(register_lastname_layout)
        setTextChangeListener(register_country_layout)
        setTextChangeListener(register_date_layout)
    }
    
    private fun setTextChangeListener(textInputLayout: TextInputLayout?) {
        textInputLayout?.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (register_email_et.text.toString().isEmpty() ||
                        register_password_et.text.toString().isEmpty() ||
                        register_firstname_et.text.toString().isEmpty() ||
                        register_lastname_et.text.toString().isEmpty() ||
                        register_country_et.text.toString().isEmpty() ||
                        register_date_et.text.toString().isEmpty() ) {
                    register_btn.isEnabled = false
                    register_btn.background = ContextCompat.getDrawable(this@RegisterActivity,
                        R.drawable.rounded_background_disabled)
                } else {
                    register_btn.isEnabled = true
                    register_btn.background = ContextCompat.getDrawable(this@RegisterActivity,
                        R.drawable.rounded_background)
                }
            }
        })
    }

    private fun registerUser() {
        val auth = Firebase.auth
        val email = register_email_et.text.toString()
        val password = register_password_et.text.toString()
        val firstName = register_firstname_et.text.toString()
        val lastName = register_lastname_et.text.toString()
        val country = register_country_et.text.toString()
        val date = register_date_et.text.toString()

        val validFields = validatedFields(email, password, firstName, lastName, country,
                date)
        if (!validFields)
            return

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this){ task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Account Created", Toast.LENGTH_SHORT).show()
                createUserEntry(email, firstName, lastName, country, date)
                startActivity(Intent(this, DashboardActivity::class.java))
            } else {
                Toast.makeText(this, "Error: " + task.exception?.message,
                        Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, RegisterActivity::class.java))
                this.finish()
            }
        }
    }

    private fun createUserEntry(email: String, firstName: String, lastName: String, country: String,
                                date: String) {
        val user = UserItem(email, firstName, lastName, country, date)
        FirebaseFirestore.getInstance()
                .collection("users")
                .document(email)
                .set(user)
    }

    private fun validatedFields(email: String, password: String, firstName: String, lastName: String,
                                country: String, date: String): Boolean{
        val validEmail = checkEmailValidity(email)
        displayErrorMessage(validEmail, register_email_et, "Please enter a valid email address")

        val validPasswordSize = verifyPasswordSize(password)
        displayErrorMessage(validPasswordSize, register_password_et, "Password should contain between 6 and 14 characters")

        val containingSymbol = verifySymbolOccurrence(password)
        displayErrorMessage(containingSymbol, register_password_et, "Password should contain at least one special character")

        val containingUpperCaseLetter = verifyUpperCaseOccurrence(password)
        displayErrorMessage(containingUpperCaseLetter, register_password_et, "Password should contain at least one upper case letter")

        val onlyLettersFirstName = verifyOnlyLetters(firstName)
        displayErrorMessage(onlyLettersFirstName, register_firstname_et, "First name should contain only letters")

        val onlyLettersLastName = verifyOnlyLetters(lastName)
        displayErrorMessage(onlyLettersLastName, register_lastname_et, "Last name should contain only letters")

        val onlyLettersCountry = verifyOnlyLetters(country)
        displayErrorMessage(onlyLettersCountry, register_country_et, "Country should contain only letters")

        val validDate = verifyValidDate(date)
        displayErrorMessage(validDate, register_date_et, "Date should have format dd/mm/yyyy")

        if (!validPasswordSize || !containingSymbol || !containingUpperCaseLetter || !validEmail ||
                !onlyLettersCountry || !onlyLettersFirstName || !onlyLettersLastName
                || !validDate)
            return false

        return true
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

    private fun verifyUpperCaseOccurrence(password: String): Boolean {
        val upperCaseRegex = ".*[A-Z].*".toRegex()
        if (!password.matches(upperCaseRegex)){
            return false
        }
        return true
    }

    private fun verifySymbolOccurrence(password: String): Boolean {
        val symbolRegex = ".*[*.!@#\$%^&(){}:\";'<>,?/~`_+-=|].*".toRegex()
        if (!password.matches(symbolRegex)){
            return false
        }
        return true
    }

    private fun verifyPasswordSize(password : String) : Boolean {
        if (password.length < 6 || password.length > 14 ){
            return false
        }
        return true
    }

    private fun verifyOnlyLetters(string: String): Boolean {
        val symbolRegex =  "^[a-zA-Z]*$".toRegex()
        if (!string.matches(symbolRegex)){
            return false
        }
        return true
    }

    private fun verifyValidDate(date: String): Boolean {
        val symbolRegex =  "^\\d{2}/\\d{2}/\\d{4}$".toRegex()
        if (!date.matches(symbolRegex)){
            return false
        }
        return true
    }

}