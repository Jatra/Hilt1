package uk.co.jatra.hilt1.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uk.co.jatra.hilt1.repositories.Repository

//ViewModelInject is similar to Inject, but is specifically for the Android Hilt ViewModel extensions
class MainViewModel @ViewModelInject constructor(private val repository: Repository): ViewModel() {

    private var _state = MutableLiveData<String>()
    var state: LiveData<String> = _state

    init {
        viewModelScope.launch {
            _state.value = repository.getValue()
        }
    }

    fun update(text: String) {
        viewModelScope.launch {
            _state.value = repository.update(text)
        }
    }
}