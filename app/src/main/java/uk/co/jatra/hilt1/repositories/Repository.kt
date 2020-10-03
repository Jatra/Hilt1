package uk.co.jatra.hilt1.repositories

import kotlinx.coroutines.delay
import javax.inject.Inject

//This class is a dependency for others, so needs to be injected.
//Here the Inject on the constructor is not injecting anything into this class,
//but is marking the class as being injectable.
//If the constructor took parameters, then they would be injected themselves.
class Repository @Inject constructor() {
    //for illustrative purposes
    private var value = ""

    suspend fun getValue(): String {
        //make an api call, update the db, whatever
        delay(3000)
        return "hello $value"
    }

    suspend fun setValue(value: String) {
        //make an api call, update the db, whatever
        delay(1000)
        this.value = value
    }

    suspend fun update(value: String): String {
        //make an api call, update the db, whatever
        delay(3000)
        this.value = value
        return "hello $value"
    }
}

//Another way would be to make the respository observable.
//Choices for that: Rx, LiveData, or since we're in coroutines: a flow.