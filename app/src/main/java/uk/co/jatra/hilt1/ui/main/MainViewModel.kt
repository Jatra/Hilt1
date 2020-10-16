package uk.co.jatra.hilt1.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import uk.co.jatra.hilt1.repositories.Result
import uk.co.jatra.hilt1.repositories.Repository

//ViewModelInject is similar to Inject, but is specifically for the Android Hilt ViewModel extensions
@ExperimentalCoroutinesApi
class MainViewModel @ViewModelInject constructor(private val repository: Repository): ViewModel() {

    lateinit var state: LiveData<ViewState>
    var _viewState: MutableLiveData<ViewState> = MutableLiveData()
    var viewState: LiveData<ViewState> = _viewState

    init {
        //THe data is here is of the type that can change
        // there are two types of data to display:
        // 1/ A result of an operation.
        //    - this is a one shot. Completable.
        // 2/ Data that can change due to external events
        //    - this is a flow, or Flowable, or Observable.
        //Watch the data source.
        viewModelScope.launch {
            state = repository.flow
                .map {
                    toViewState(it)
                }
                .asLiveData()
                .distinctUntilChanged()

        }
    }

    fun update(text: String) {
        viewModelScope.launch {
            repository.update(text)
        }
    }

    fun update2(text: String) {
        viewModelScope.launch {
            val data = repository.update2(text)
            _viewState.postValue(toViewState(data))
        }
    }


    private fun toViewState(it: Result): ViewState {
        return when (it) {
            Result.Idle -> Content("")
            Result.Fetching -> Loading
            is Result.Content -> Content(it.data)
            is Result.Error -> Error
        }
    }

}

//This appears to be redundant, since it is identical to Data
//A viewstate would actually have many more variables than just the data
//eg what fields are enabled,
//whether the loading spinner should be show
//etc
sealed class ViewState
object Loading: ViewState()
class Content<T>(val data: T): ViewState()
object Error: ViewState()
