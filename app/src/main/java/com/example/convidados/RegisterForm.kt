package com.example.convidados

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class RegisterForm : AppCompatActivity() {

    private lateinit var toolbar : androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_form)

        //Configurando botão da toolbar para voltar ao login
        toolbar = findViewById(R.id.toolbar_form_cadastro)
        toolbar.setNavigationOnClickListener(){
            finish()
        }

    }


}