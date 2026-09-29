package com.example.calculimc

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    val historique = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val editTextPoids = findViewById<EditText>(R.id.editTextPoids)
        val editTextTaille = findViewById<EditText>(R.id.editTextTaille)

        val buttonCalculer = findViewById<Button>(R.id.buttonCalculer)
        val buttonEffacer = findViewById<Button>(R.id.buttonEffacer)

        val textViewImc = findViewById<TextView>(R.id.textViewImc)
        val textViewCategorie = findViewById<TextView>(R.id.textViewCategorie)

        val historiqueTextView = findViewById<TextView>(R.id.historiqueTextView)


        buttonEffacer.setOnClickListener {
            editTextPoids.text.clear()
            editTextTaille.text.clear()
            textViewImc.text = ""
            textViewCategorie.text = ""
        }
        buttonCalculer.setOnClickListener {
            val poidsTexte = editTextPoids.text.toString()
            val tailleTexte = editTextTaille.text.toString()
            if (poidsTexte.isEmpty() || tailleTexte.isEmpty()){
                textViewImc.text = getString(R.string.champs_vides)
                textViewCategorie.text = ""
                return@setOnClickListener
            }
            val poids = poidsTexte.toDouble()
            val taille = tailleTexte.toDouble()/100

            if (poids <= 0 || taille <= 0){
                textViewImc.text = getString(R.string.valeurs_positives)
                textViewCategorie.text = ""
                return@setOnClickListener
            }
            val imc = poids / (taille * taille)
            val resultat = "IMC : %.2f".format(imc)
            historique.add(resultat)
            historiqueTextView.text = historique.joinToString("\n")
            textViewImc.text = resultat
            if (imc < 18.5) {
                textViewCategorie.text = getString(R.string.insuffisance)
                textViewCategorie.setTextColor(Color.rgb(255, 165, 0))

            } else if (imc < 25) {
                textViewCategorie.text = getString(R.string.normale)
                textViewCategorie.setTextColor(Color.GREEN)

            } else if (imc < 30) {
                textViewCategorie.text = getString(R.string.surpoids)
                textViewCategorie.setTextColor(Color.rgb(255, 165, 0))

            } else if (imc < 35) {
                textViewCategorie.text = getString(R.string.obesite_moderee)
                textViewCategorie.setTextColor(Color.RED)

            } else if (imc < 40) {
                textViewCategorie.text = getString(R.string.obesite_severe)
                textViewCategorie.setTextColor(Color.RED)

            } else {
                textViewCategorie.text = getString(R.string.obesite_morbide)
                textViewCategorie.setTextColor(Color.rgb(139, 0, 0))
            }
        }
    }
}