package uk.co.jatra.hilt1.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_fragment.*
import uk.co.jatra.hilt1.R

//this annotation required to allow the by ViewModels to create a viewmodel that users Hilt.
@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()
    private lateinit var messageView: TextView
    private lateinit var input: EditText
    private lateinit var button: Button
    private lateinit var button2: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)

        messageView = view.findViewById(R.id.message)
        input = view.findViewById(R.id.input)
        button = view.findViewById(R.id.button)
        button2 = view.findViewById(R.id.button2)

        button.setOnClickListener {
            viewModel.update(input.text.toString())
        }

        button2.setOnClickListener {
            viewModel.update2(input.text.toString())
        }

        viewModel.state.observe(viewLifecycleOwner) {
            handleViewState(it)
        }
        viewModel.viewState.observe(viewLifecycleOwner) {
            handleViewState(it)
        }
        return view
    }

    private fun handleViewState(viewState: ViewState?) {
        when (viewState) {
            Loading -> {
                progressBar.isVisible = true
            }
            is Content<*> -> {
                when (viewState.data) {
                    is String -> messageView.text = viewState.data
                    else -> messageView.text = "unexpected data"
                }
                progressBar.isVisible = false
            }
            Error -> {
                progressBar.isVisible = false
                //show error message, retry or whatever.
            }
        }
    }
}