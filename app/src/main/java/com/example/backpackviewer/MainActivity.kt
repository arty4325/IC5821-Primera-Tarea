package com.example.backpackviewer

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.media.Image
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView

// val = final, constante
// var = se puede cambiar
// fun = funciones --> fun greet(){...}

//var <nombre>: <Tipo> = <valor>
//para que la variable pueda ser null, se pone un ? despues del tipo

/*
* Parametros para funciones es --> fun add(a: Int, b: Int): Int {...}
*
* Para encontrar un elemento del gui, es de esta forma:
* val <nombre>: <Tipo> = findviewById(R.id.button)
* Para declarar una variable sin inicializarla, se usa lateinit
* */


import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var image : ImageView //lateinit es que aún no se inicializa
    private val REQUEST_CODE = 1 //Es un número cualquiera que vamos a usar para identificar el resultado del intent.

    private val openCam = //lo que guarda es solo la referencia como para el proceso de abrir la cámara
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result -> //funcion lambda con result como parametro, result es lo que devuelve el activity.
            if(result.resultCode == Activity.RESULT_OK) {//Si devuelve que se hizo bien el activity
                val imageBitmap = result.data?.extras?.get("data") as Bitmap //De lo que devolvió, coger el data, y de ahí pasarlo a un Bitmap porque se ocupa para ponerlo en el imageView
                image.setImageBitmap(imageBitmap)
            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val btnTakePicture = findViewById<Button>(R.id.btnTakePicture)
        image = findViewById(R.id.imgPic)

        btnTakePicture.setOnClickListener{
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE) //el intent es un objeto que se usa para comunicar  entre componentes de una aplicacion
            openCam.launch(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }




}