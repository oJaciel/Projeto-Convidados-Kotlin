package com.example.convidados

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var databaseApp:SQLiteDatabase
    private var guestList: ArrayList<GuestModel> = ArrayList()
    private lateinit var listViewGuests: ListView
    private lateinit var btnAdd : FloatingActionButton
    private var ID_SELECIONADO:Int? = null

    override fun onResume() {
        super.onResume()
        getGuestList()
        //Quando voltar para a tela depois de adicionar, atualiza os dados
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listViewGuests = findViewById(R.id.listViewGuests)
        btnAdd = findViewById(R.id.btnAdd)

        //Gerando intent para abrir nova tela
        btnAdd.setOnClickListener{
            val intent = Intent(this, GuestForm::class.java)
            startActivity(intent)
        }

        listViewGuests.setOnItemClickListener { _/*O _ é para omitir*/, _, idx/*Index*/, _ ->
            println("O indíce da lista clicado é o $idx")

            println("O id do cara clicado é o ${guestList[idx].id}")

            //Alimentando a variável com o ID
            ID_SELECIONADO = guestList[idx].id

            val intent = Intent(this, GuestForm::class.java)
            //Passando parâmetro para adicional para quando ativar a intent
            intent.putExtra("ID_SELECIONADO", ID_SELECIONADO)
            startActivity(intent)
        }



        createDatabase()
        getGuestList()
    }

    private fun getGuestList() {
        try {
            guestList = ArrayList()
            databaseApp = openOrCreateDatabase("dbGuestApp", MODE_PRIVATE, null)
            //retorna o resultado da consulta
            val cursor: Cursor = databaseApp.rawQuery("SELECT id, name from guestTable", null)

            //alimentando o array list de convidados
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                guestList.add(GuestModel(cursor.getInt(0), cursor.getString(1)))
                cursor.moveToNext()
            }
            cursor.close()
            //adaptando o conteudo ao list view
            val adapterGuest: ArrayAdapter<GuestModel> = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                guestList
            )
            listViewGuests.adapter = adapterGuest
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun createDatabase() {
        try {
            databaseApp = openOrCreateDatabase("dbGuestApp", MODE_PRIVATE, null)
            databaseApp.execSQL("CREATE TABLE IF NOT EXISTS guestTable" +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR)")
            databaseApp.close()
        } catch(e: Exception) {
           e.printStackTrace()
        }
    }
}