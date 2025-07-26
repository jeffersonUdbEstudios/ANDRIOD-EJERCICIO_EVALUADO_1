package com.example.ejercicio_evaluado_1

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.TextView

class Resultado_formulario : AppCompatActivity() {
    
    private lateinit var textViewFullName: TextView
    private lateinit var textViewEmail: TextView
    private lateinit var textViewPhone: TextView
    private lateinit var textViewBirthDate: TextView
    private lateinit var textViewAddress: TextView
    private lateinit var buttonBackToHome: Button
    private lateinit var buttonNewProfile: Button

    companion object {
        const val EXTRA_FULL_NAME = "extra_full_name"
        const val EXTRA_EMAIL = "extra_email"
        const val EXTRA_PHONE = "extra_phone"
        const val EXTRA_BIRTH_DATE = "extra_birth_date"
        const val EXTRA_ADDRESS = "extra_address"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_resultado_formulario)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar vistas
        initializeViews()
        
        // Obtener y mostrar datos del usuario
        displayUserData()
        
        // Configurar listeners de botones
        setupButtonListeners()
    }

    private fun initializeViews() {
        textViewFullName = findViewById(R.id.textViewFullName)
        textViewEmail = findViewById(R.id.textViewEmail)
        textViewPhone = findViewById(R.id.textViewPhone)
        textViewBirthDate = findViewById(R.id.textViewBirthDate)
        textViewAddress = findViewById(R.id.textViewAddress)
        buttonBackToHome = findViewById(R.id.buttonBackToHome)
        buttonNewProfile = findViewById(R.id.buttonNewProfile)
    }

    private fun displayUserData() {
        val fullName = intent.getStringExtra(EXTRA_FULL_NAME) ?: "No especificado"
        val email = intent.getStringExtra(EXTRA_EMAIL) ?: "No especificado"
        val phone = intent.getStringExtra(EXTRA_PHONE) ?: "No especificado"
        val birthDate = intent.getStringExtra(EXTRA_BIRTH_DATE) ?: "No especificado"
        val address = intent.getStringExtra(EXTRA_ADDRESS) ?: "No especificado"

        textViewFullName.text = fullName
        textViewEmail.text = email
        textViewPhone.text = phone
        textViewBirthDate.text = birthDate
        textViewAddress.text = address
    }

    private fun setupButtonListeners() {
        buttonBackToHome.setOnClickListener {
            // Regresar al inicio (MainActivity)
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }

        buttonNewProfile.setOnClickListener {
            // Crear nuevo perfil (ir a FormularioDatos)
            val intent = Intent(this, FormularioDatos::class.java)
            startActivity(intent)
            finish()
        }
    }
}