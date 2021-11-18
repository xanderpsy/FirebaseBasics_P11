package com.example.firebasebasics_p11

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.json.JSONObject

private lateinit var  database: DatabaseReference
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myDB = FirebaseDatabase.getInstance()
        database = myDB.reference
        //database.setValue("hola mundo")
        //database.child("usuarios").setValue("primer usuario")
        //database.child("usuarios").setValue("segundo usuario")
        //  writeNewUser("003","nombretres","tres@hotmail.com")
       // getUser("002")
        val etId = findViewById<EditText>(R.id.etUserId)
        val etNombre = findViewById<EditText>(R.id.etUserName)
        val etcorreo = findViewById<EditText>(R.id.etUserEmail)
        val btnSend = findViewById<Button>(R.id.btnSet)
        val btnGet = findViewById<Button>(R.id.btnGet)
        val etUserIdToGet = findViewById<EditText>(R.id.etUserIdToGet)
        //val etId =findViewById<EditText>(R.id.etUserId)
        //val etId =findViewById<EditText>(R.id.etUserId)
        btnSend.setOnClickListener {
            writeNewUser(etId.text.toString(), etNombre.text.toString(), etcorreo.text.toString())
            etId.text.clear()
            etNombre.text.clear()
            etcorreo.text.clear()

        }
        btnGet.setOnClickListener {
          val json =  getUser(etUserIdToGet.text.toString())

            Log.d("demo","$json")
        }
    }
    fun writeNewUser(userID: String, name: String,  email:String){
        val user = User(name, email)
        database.child("users").child(userID).setValue(user)
    }
    fun getUser(userID: String){
        database.child("users").child(userID).get().addOnSuccessListener { record ->
            val json = JSONObject(record.value.toString())
            Log.d("Valores","got vale ${json}")
            val tv = findViewById<TextView>(R.id.tv)
            tv.text = "El Nombre es:${json.getString("nombre")}\n" +
                    "El Usuario es: ${json.getString("correo")}"


        }
    }


}
class User(name :String, email: String){
    val nombre = name
    val correo = email
}