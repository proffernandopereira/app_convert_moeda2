package com.example.app_convert_moeda.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.app_convert_moeda.R
import com.example.app_convert_moeda.data.repository.CurrencyRepository
import com.example.app_convert_moeda.databinding.ActivityMainBinding
import com.example.app_convert_moeda.viewmodel.CurrencyViewModel
import com.example.app_convert_moeda.viewmodel.CurrencyViewModelFactory
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityMainBinding
    private  lateinit var viewModel: CurrencyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate((layoutInflater))
        setContentView((binding.root))

        val repository = CurrencyRepository()
        val factory = CurrencyViewModelFactory(repository)
        viewModel = ViewModelProvider (this, factory).get((CurrencyViewModel::class.java))

        binding.buttonConvert.setOnClickListener {
            val realValue = binding.editTextRealValue.text.toString().toDoubleOrNull()
            if(realValue != null){
                try {
                    viewModel.getCurrencies("USD-BRL,EUR-BRL") { response ->
                        response?.let {
                            try {
                                val usdRate = it.usdBrl.bid?.toDoubleOrNull() ?: 0.0
                                val eurRate = it.eurBrl.bid?.toDoubleOrNull() ?: 0.0
                                binding.textViewUsdValue.text = String.format(Locale.getDefault(), "%.2f", realValue * usdRate)
                                binding.textViewEurValue.text = String.format(Locale.getDefault(), "%.2f", realValue * eurRate)
                            } catch (e: Exception) {
                                Log.e("MainActivity", "Erro ao analisar a resposta: ${e.message}")
                                Toast.makeText(this, "Erro ao analisar a resposta", Toast.LENGTH_SHORT).show()
                            }
                        } ?: run {
                            Log.e("MainActivity", "Resposta vazia")
                            Toast.makeText(this, "Falha ao obter resposta do servidor", Toast.LENGTH_SHORT).show()
                        }
                    }
                }catch (e: Exception) {
                    Log.e("MainActivity", "Erro ao buscar moedas: ${e.message}")
                    Toast.makeText(this, "Erro ao buscar moedas", Toast.LENGTH_SHORT).show()
                }
            }else{
                Log.e("MainActivity", "Valor de entrada inválido")
                Toast.makeText(this, "Por favor, insira um valor válido", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
