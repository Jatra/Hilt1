package uk.co.jatra.hilt1.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import uk.co.jatra.hilt1.repositories.Repository

//The repository uses experimental stateflow
@ExperimentalCoroutinesApi
//ViewModelInject is similar to Inject, but is specifically for the Android Hilt ViewModel extensions
class MainViewModel @ViewModelInject constructor(private val repository: Repository): ViewModel() {

    lateinit var state: LiveData<String>

    init {
        viewModelScope.launch {
            state = repository.flow.asLiveData()
        }
    }

    fun update(text: String) {
        viewModelScope.launch {
            repository.update(text)
        }
    }
}