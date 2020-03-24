package mx.edu.ittepic.ladm_u3_ejercicio1_sqlite

import android.content.Context
import android.database.sqlite.SQLiteCursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class BaseDatos(context:Context?,
                nombreBD: String?,
                cursor: SQLiteDatabase.CursorFactory?,
                versionBD:Int)
    : SQLiteOpenHelper(context,nombreBD,cursor,versionBD) {

    //se utiliza para construir la ESTRUCTURA DE TABLAS desde SQLite
    // UPDATE  = Actualizacion Menor = Modificar los DATOS
    override fun onCreate(db: SQLiteDatabase?) {

        try{
            db?.execSQL("CREATE TABLE PERSONA(ID INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE varchar(200),DOMICILIO Varchar(200))")
        }catch (error:SQLiteException){

        }

    }

    // UPGRADE = Actualizacion Mayor = Modificar la ESTRUCTURA
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}//Fin de la clase BD