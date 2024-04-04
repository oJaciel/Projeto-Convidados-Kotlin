package com.example.convidados

import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class RegisterForm : AppCompatActivity() {

    private lateinit var userDatabase:SQLiteDatabase

    private lateinit var edtName:EditText

    private lateinit var edtEmail:EditText

    private lateinit var edtPass:EditText

    private lateinit var btnRegister:Button

    private lateinit var toolbar : androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_form)

        //Captando os campos do xml
        edtName = findViewById(R.id.edtRegisterName)
        edtEmail = findViewById(R.id.edtRegisterEmail)
        edtPass = findViewById(R.id.edtRegisterPass)
        btnRegister = findViewById(R.id.btnRegister)

        //Configurando botão da toolbar para voltar ao login
        toolbar = findViewById(R.id.toolbar_form_cadastro)
        toolbar.setNavigationOnClickListener(){
            finish()
        }

        //Chamando funçao de insert ao clicar no botão
        btnRegister.setOnClickListener{
            register()
        }

    }

    private fun register() {
        try {
            //Validação com if para ver se algum dos campos está vazio
            if (TextUtils.isEmpty(edtName.text) ||
                TextUtils.isEmpty(edtEmail.text) ||
                TextUtils.isEmpty((edtPass.text))) {
                //Caso algum esteja vazio, dispara toast
                Toast.makeText(this, "Favor inserir todos os dados", Toast.LENGTH_SHORT).show()
            }
                //Se todos os campos estão preenchidos, dados são inseridos no banco
                else {
                //Abrindo banco de dados
                userDatabase = openOrCreateDatabase("dbGuestApp", MODE_PRIVATE, null)
                //Inserindo os dados no banco
                val sql = "INSERT INTO usersTable (name, email, password) VALUES (?, ?, ?) "
                val stmt = userDatabase.compileStatement(sql)
                stmt.bindString(1, edtName.text.toString())
                stmt.bindString(2, edtEmail.text.toString())
                stmt.bindString(3, edtPass.text.toString())
                stmt.executeInsert()
                //Fechando o banco
                userDatabase.close()
                //Exibindo mensagem toast de sucesso
                Toast.makeText(this, "Cadastrado com sucesso", Toast.LENGTH_SHORT).show()
                //Finalizando a intent
                finish()
            }
        } catch (e:Exception) {
            e.printStackTrace()
        }

    }

}