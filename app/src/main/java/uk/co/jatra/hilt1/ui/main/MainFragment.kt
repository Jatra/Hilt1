package uk.co.jatra.hilt1.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import uk.co.jatra.hilt1.R

//this annotation required to allow the byViewModels to create a viewmodel that users Hilt.
@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var messageView: TextView
    private lateinit var input: EditText
    private lateinit var button: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)

        messageView = view.findViewById(R.id.message)
        input = view.findViewById(R.id.input)
        button = view.findViewById(R.id.button)

        button.setOnClickListener {
            viewModel.update(input.text.toString())
        }

        viewModel.state.observe(viewLifecycleOwner) {
            messageView.text = it
        }
        return view
    }
}