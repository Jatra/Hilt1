package uk.co.jatra.hilt1.repositories

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject

//This version uses StateFlow - which is still experimental.
//An alternative would be to use channels.
@ExperimentalCoroutinesApi
class Repository @Inject constructor() {
    private val _flow = MutableStateFlow("")
    val flow: StateFlow<String>  = _flow

    init {
        //make an initial call to the API/db etc.
        _flow.value = ""
    }

    suspend fun update(value: String) {
        _flow.value = "Loading"
        //make an api call, update the db, whatever
        delay(1000)
        _flow.value = "hello $value"
    }

    //get the latest value.
    suspend fun getValue(): String {
        delay(3000)
        return _flow.value
    }
}

//Rather than using a string, the data should be a Result type sealed class, allowing for
// Loading. Data, Error, etc.