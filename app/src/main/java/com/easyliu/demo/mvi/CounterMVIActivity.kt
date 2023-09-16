package com.easyliu.demo.mvi

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class CounterMVIActivity : ComponentActivity(), MviView {
    private lateinit var counter: TextView
    private lateinit var add: TextView
    private lateinit var decrease: TextView

    private val viewModel by ViewModelLazy(CounterViewModel::class, { viewModelStore }, {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass == CounterViewModel::class.java) {
                    return CounterViewModel(CounterUiState(0)) as T
                }
                return super.create(modelClass)
            }
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_counter_mvi)
        counter = findViewById(R.id.tv_counter)
        add = findViewById<TextView?>(R.id.bt_add).apply {
            setOnClickListener {
                viewModel.setIntent(CounterUiIntent.Add)
            }
        }
        decrease = findViewById<TextView?>(R.id.bt_decrease).apply {
            setOnClickListener {
                viewModel.setIntent(CounterUiIntent.Decrease)
            }
        }
        viewModel.onEach(CounterUiState::count) {
            counter.text = buildString {
                append("计数:")
                append(it)
            }
        }
        viewModel.onEvent {
            when (it) {
                is CounterEvent.ShowAddToast -> {
                    Toast.makeText(this, "计数+1", Toast.LENGTH_SHORT).show()
                }

                is CounterEvent.ShowDecreaseToast -> {
                    Toast.makeText(this, "计数-1", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}