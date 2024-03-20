package com.example.convidados

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText

class GuestForm : AppCompatActivity() {

    private lateinit var databaseApp: SQLiteDatabase

    private lateinit var edtGuestName:EditText

    private lateinit var btnSave:Button

    //Variável para recebr o parâmetro passado da tela main via intent (editar)
    private var idReceivedParam: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_form)

        edtGuestName = findViewById(R.id.edtGuestName)

        //Recebendo parâmetro passado de uma tela a outra via intent
        val intent: Intent = intent
        idReceivedParam = intent.getIntExtra("ID_SELECIONADO", 0)

        //Função para captar nome do convidado no banco
        load()



        btnSave = findViewById(R.id.btnSave)

        btnSave.setOnClickListener{
            save()
        }
    }

    //Função para carregar nome do convidado a ser editado
    private fun load() {
        try {
            databaseApp = openOrCreateDatabase("dbGuestApp", MODE_PRIVATE, null)
            //retorna o resultado da consulta, buscando o ID passado da outra tela
            val cursor: Cursor = databaseApp.rawQuery("SELECT id, name from guestTable WHERE id = " + idReceivedParam, null)

            //Mesmo que retorne só um, o cursor ainda tem que ir para o primeiro registro
            cursor.moveToFirst()

            //Atribuindo o valor do campo de edição para o nome do banco
            edtGuestName.setText(cursor.getString(1))

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun save() {
        try {
            //Abrindo o banco de dados
            databaseApp = openOrCreateDatabase("dbGuestApp", MODE_PRIVATE, null)
            val sql = "INSERT INTO guestTable (name) VALUES (?)" //Criando uma string
            // a ? é um parâmetro que entra a variável que será digitada
            val stmt = databaseApp.compileStatement(sql) //Preparando uma query
            stmt.bindString(1, edtGuestName.text.toString())
            //Index é o ? no sql, pode ter mais de um
            //O segundo valor é o parâmetro a ser colocado
            stmt.executeInsert() //Executando o insert no banco

            databaseApp.close()

        } catch (e:Exception){
            e.printStackTrace()
        }
        finish() //Função para finalizar a activity (volta pra main)
    }

}