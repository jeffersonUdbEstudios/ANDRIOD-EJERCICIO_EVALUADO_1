package com.example.ejercicio_evaluado_1

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.app.ActivityCompat
import android.app.AlertDialog
import android.util.Log
import android.content.Intent

class FormularioDatos : AppCompatActivity() {
    private lateinit var editTextFullName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var editTextBirthDate: EditText
    private lateinit var editTextAddress: EditText
    private lateinit var textViewCameraStatus: TextView
    private lateinit var buttonTakePhoto: Button
    private lateinit var buttonSave: Button

    companion object {
        private const val TAG = "FormularioDatos"
        private const val CODIGO_SOLICITUD_CAMARA = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_formulario_datos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        editTextFullName = findViewById(R.id.editTextFullName)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPhone = findViewById(R.id.editTextPhone)
        editTextBirthDate = findViewById(R.id.editTextBirthDate)
        editTextAddress = findViewById(R.id.editTextAddress)
        textViewCameraStatus = findViewById(R.id.textViewCameraStatus)
        buttonTakePhoto = findViewById(R.id.buttonTakePhoto)
        buttonSave = findViewById(R.id.buttonSave)

        buttonTakePhoto.setOnClickListener {
            configurarPermisos()
        }

        buttonSave.setOnClickListener {
            if (validateForm()) {
                // Obtener los datos del formulario
                val fullName = editTextFullName.text.toString().trim()
                val email = editTextEmail.text.toString().trim()
                val phone = editTextPhone.text.toString().trim()
                val birthDate = editTextBirthDate.text.toString().trim()
                val address = editTextAddress.text.toString().trim()

                // Crear Intent para navegar a la pantalla de resumen
                val intent = Intent(this, Resultado_formulario::class.java)
                intent.putExtra(Resultado_formulario.EXTRA_FULL_NAME, fullName)
                intent.putExtra(Resultado_formulario.EXTRA_EMAIL, email)
                intent.putExtra(Resultado_formulario.EXTRA_PHONE, phone)
                intent.putExtra(Resultado_formulario.EXTRA_BIRTH_DATE, birthDate)
                intent.putExtra(Resultado_formulario.EXTRA_ADDRESS, address)
                
                // Navegar a la pantalla de resumen
                startActivity(intent)
            }
        }
    }

    private fun configurarPermisos() {
        val permission = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.CAMERA
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permiso denegado para cámara")
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    android.Manifest.permission.CAMERA
                )
            ) {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Permiso para acceder a la cámara es " +
                        "requerido por esta App para tomar fotos.")
                    .setTitle("Permiso requerido")
                builder.setPositiveButton("OK") { dialog, id ->
                    Log.i(TAG, "Seleccionado")
                    hacerSolicitudPermiso()
                }
                val dialog = builder.create()
                dialog.show()
            } else {
                hacerSolicitudPermiso()
            }
        } else {
            textViewCameraStatus.text = "Permiso de cámara concedido"
        }
    }

    private fun hacerSolicitudPermiso() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.CAMERA),
            CODIGO_SOLICITUD_CAMARA
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CODIGO_SOLICITUD_CAMARA -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    textViewCameraStatus.text = "Permiso de cámara concedido"
                } else {
                    textViewCameraStatus.text = "No se puede acceder a la cámara"
                    Toast.makeText(this, "No se puede acceder a la cámara", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun validateForm(): Boolean {
        val fullName = editTextFullName.text.toString().trim()
        val email = editTextEmail.text.toString().trim()
        val phone = editTextPhone.text.toString().trim()
        val birthDate = editTextBirthDate.text.toString().trim()
        val address = editTextAddress.text.toString().trim()
        var valid = true
        val emailPattern = android.util.Patterns.EMAIL_ADDRESS
        val phonePattern = Regex("^[0-9]{7,15}$")
        val datePattern = Regex("^\\d{2}/\\d{2}/\\d{4}$")

        if (fullName.isEmpty()) {
            editTextFullName.error = "Campo requerido"
            valid = false
        }
        if (email.isEmpty() || !emailPattern.matcher(email).matches()) {
            editTextEmail.error = "Correo inválido"
            valid = false
        }
        if (phone.isEmpty() || !phonePattern.matches(phone)) {
            editTextPhone.error = "Teléfono inválido"
            valid = false
        }

        if (address.isEmpty()) {
            editTextAddress.error = "Campo requerido"
            valid = false
        }
        return valid
    }
}