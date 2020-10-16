package uk.co.jatra.hilt1.repositories

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import uk.co.jatra.hilt1.repositories.Result.Fetching
import javax.inject.Inject

//This class is a dependency for others, so needs to be injected.
//Here the Inject on the constructor is not injecting anything into this class,
//but is marking the class as being injectable.
//If the constructor took parameters, then they would be injected themselves.
class Repository @Inject constructor() {
    @ExperimentalCoroutinesApi
    private val _flow: MutableStateFlow<Result> = MutableStateFlow(Result.Idle)
    @ExperimentalCoroutinesApi
    val flow: StateFlow<Result> = _flow

    init {
        //Could make an initial call to the API/db etc
        //but that would need a corurtine scope.
    }

    @ExperimentalCoroutinesApi
    suspend fun update(value: String) {
        _flow.value = Fetching
        //make an api call, update the db, whatever
        delay(1000)
        _flow.value = Result.Content("hello $value")
    }

    @ExperimentalCoroutinesApi
    suspend fun update2(value: String): Result {
        _flow.value = Fetching
        //make an api call, update the db, whatever
        delay(1000)
        return Result.Content("hello $value")
    }

    //get the latest value.
    @ExperimentalCoroutinesApi
    suspend fun getValue(): Result {
        delay(3000)
        return _flow.value
    }
}

sealed class Result {
    object Idle: Result()
    object Fetching : Result()
    data class Content(val data: String) : Result()
    data class Error(val reason: String) : Result()
}