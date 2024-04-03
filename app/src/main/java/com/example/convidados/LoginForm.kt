package com.example.convidados

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import org.w3c.dom.Text

class LoginForm : AppCompatActivity() {

    private lateinit var loginButton : Button

    private lateinit var registerButton : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_form)

        //Abrindo a lista de convidados (ainda sem validação)
        loginButton = findViewById(R.id.btnLogin)
        loginButton.setOnClickListener{
            val intentMain = Intent (this, MainActivity::class.java)
            startActivity(intentMain)
        }

        //Abrindo a tela de registro
        registerButton = findViewById<TextView>(R.id.txtLoginRegister)
        registerButton.setOnClickListener{
            val intentRegister = Intent (this, RegisterForm::class.java)
            startActivity(intentRegister)
        }


    }
}