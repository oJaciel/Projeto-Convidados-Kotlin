package com.example.convidados

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text

class LoginForm : AppCompatActivity() {

    private lateinit var edtEmail : EditText

    private lateinit var edtPassword : EditText

    private lateinit var userDatabase : SQLiteDatabase

    private lateinit var loginButton : Button

    private lateinit var txtRegister : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_form)

        //Captando elementos do xml
        edtEmail = findViewById(R.id.edtLoginEmail)
        edtPassword = findViewById(R.id.edtLoginPassword)
        loginButton = findViewById(R.id.btnLogin)
        txtRegister = findViewById(R.id.txtLoginRegister)

        //Abrindo a lista de convidados
        loginButton.setOnClickListener{
            validateLogin(edtEmail.text.toString(), edtPassword.text.toString())
        }

        //Abrindo a tela de registro
        txtRegister.setOnClickListener{
            val intentRegister = Intent (this, RegisterForm::class.java)
            startActivity(intentRegister)
            //Quando abrir cadastro, limpa os campos, aí quando voltar pro login aparecem vazios
            edtEmail.setText("")
            edtPassword.setText("")
        }

        createDatabase()

    }




    //Função para criar banco de dados
    private fun createDatabase() {
        try {
            userDatabase = openOrCreateDatabase("dbGuestApp", MODE_PRIVATE, null)
            userDatabase.execSQL("CREATE TABLE IF NOT EXISTS usersTable" +
                    "(name VARCHAR, email VARCHAR, password VARCHAR)")
            userDatabase.close()
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }

    //Função de login / validar login
    private fun validateLogin(email : String, password : String) {
        try {
            //Primeiro validando se os campos estão preenchidos
            if (TextUtils.isEmpty(edtEmail.text) ||
                TextUtils.isEmpty(edtPassword.text)) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }

            //Caso estejam preenchidos, segue para validação no banco
            else{
                //Abrindo o banco
                userDatabase = openOrCreateDatabase("dbGuestApp", MODE_PRIVATE, null)
                //Executando query no banco buscando email e senha digitados
                val cursor : Cursor = userDatabase.rawQuery("SELECT email, password FROM usersTable " +
                        "WHERE email = '$email' AND password = '$password' ", null)

                //Caso query retorne resultado, abre tela main
                if (cursor.moveToFirst()) {
                    //Criando intent para abrir a tela main
                    val intentMain = Intent (this, MainActivity::class.java)
                    //Fazendo intent tornar tela main a 'raiz'
                    intentMain.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intentMain)
                }
                //Caso query não retorne resultado, mostra mensagem de dados inválidos]
                else {
                    Toast.makeText(this, "Email ou senha inválido", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e : Exception) {
            e.printStackTrace()
        }

    }

}