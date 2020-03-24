package mx.edu.ittepic.ladm_u3_ejercicio1_sqlite

import android.database.sqlite.SQLiteException
import android.icu.text.CaseMap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val nombreBD="Ejemplo1"
    var listaID = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        insertar.setOnClickListener {
            insertarPersona()
        }

        consultar.setOnClickListener {
            consultarPorID()
        }

        cargarLista()

    }//Fin de la seccion on create

    fun cargarLista(){
        try {
            var baseDatos = BaseDatos(this,nombreBD,null,1)
            var select = baseDatos.readableDatabase
            var SQL = "SELECT * FROM Persona"


            var cursor = select.rawQuery(SQL,null)
                if(cursor.count>0){
                    var arreglo = ArrayList<String>()
                    this.listaID = ArrayList<String>()
                    cursor.moveToFirst()
                    var cantidad = cursor.count-1

                    (0..cantidad).forEach {
                        var data = "Nombre: ${cursor.getString(1)}  \nDomicilio: ${cursor.getString(2)}"
                        arreglo.add(data)
                        listaID.add(cursor.getString(0))
                        cursor.moveToNext()
                    }
                    lista.adapter = ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1, arreglo)

                    //Action cuando se da clicka algun elemento del la lista
                    lista.setOnItemClickListener { parent, view, position, id ->
                        AlertDialog.Builder(this)
                            .setTitle("Atencion")
                            .setMessage("Que deseas hacer con este ITEM?")
                            .setPositiveButton("Eliminar"){d,i->
                                eliminarPorID(listaID[position])
                            }
                            .setNeutralButton("Actualizar"){d,i->}
                            .setNegativeButton("Cancelar"){d,i->}
                            .show()
                    }
                }else{
                    mensaje("No existen resultados amigos")
                }

            select.close()
            baseDatos.close()


        }catch (error: SQLiteException){
            mensaje(error.message.toString() + "verifique posibles errores")
        }
    }//Fin del metodo

    fun eliminarPorID(id: String){
        try{
            var baseDatos = BaseDatos(this,nombreBD, null,1)
            var insertar = baseDatos.writableDatabase
            var SQL ="DELETE FROM PERSONA WHERE ID=?"
            var parametros = arrayOf(id)


            insertar.execSQL(SQL,parametros)
            mensaje("Se ELIMINO el campo correctamente uwu")
            insertar.close()
            baseDatos.close()

            cargarLista()

            //Tarea crear el crud INSERT , DELETE , UPDATE

        }catch (er:SQLiteException){
            mensaje(er.message.toString())
        }
    }

    fun consultarPorID(){
       var campoIdBuscar = EditText(this)

        campoIdBuscar.setHint("Numero Entero")
        campoIdBuscar.inputType = InputType.TYPE_CLASS_NUMBER

        AlertDialog.Builder(this)
            .setTitle("Atencion")
            .setMessage("Escriba el ID a buscar")
            .setPositiveButton("BUSCAR"){d,i ->
                if(campoIdBuscar.text.isEmpty()){
                        mensaje("Error: No escribiste un ID a buscar")

                        return@setPositiveButton
                }
                buscarPorId(campoIdBuscar.text.toString())
            }
            .setNeutralButton("Cancelar"){d,i -> }
            .setView(campoIdBuscar)
            .show()
    }

    fun buscarPorId(id:String){
        try {
            var baseDatos = BaseDatos(this,nombreBD,null,1)
            var select = baseDatos.readableDatabase
            var SQL = "SELECT * FROM Persona WHERE ID =?"
            var parametros = arrayOf(id)

            var cursor = select.rawQuery(SQL, parametros)

            //Si existe una respuesta entra al if indicando que por lo menos arroja 1 resultado
            if(cursor.moveToFirst()){
                //Si hay almenos 1 resultado
                textView.setText("Nombre: " + cursor.getString(1) + "\nDOMICILIO: " + cursor.getString(2))
            }else{
                mensaje("No existen resultados")
            }
            select.close()
            baseDatos.close()


        }catch (error: SQLiteException){
            mensaje(error.message.toString() + "verifique posibles errores")
        }
    }

    fun insertarPersona(){
            try{
                var baseDatos = BaseDatos(this,nombreBD, null,1)
                var insertar = baseDatos.writableDatabase
                var SQL ="INSERT INTO PERSONA VALUES(NULL,'${editText.text.toString()}','${editText2.text.toString()}')"

                insertar.execSQL(SQL)
                mensaje("Se inserto el campo correctamente uwu")
                insertar.close()
                baseDatos.close()

                editText.setText("")
                editText2.setText("")
                cargarLista()

                //Tarea crear el crud INSERT , DELETE , UPDATE

            }catch (er:SQLiteException){
                mensaje(er.message.toString())
            }
    }

    fun mensaje(mensaje:String){
        AlertDialog.Builder(this)
            .setMessage(mensaje)
            .setTitle("Titutlo uwu")
            .show()
    }

}
