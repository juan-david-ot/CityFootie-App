package com.example.cityfootie_compose.ui.screens.create_football_match

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cityfootie_compose.model.FootballMatch
import com.example.cityfootie_compose.usecases.post_match.PostFootballMatchUsecases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class CreateFootballMatchViewModel @Inject constructor(
    private val postFootballMatchUsecases: PostFootballMatchUsecases
) : ViewModel() {
    var dateString: String by mutableStateOf("")
    var numberMax: String by mutableStateOf("")

    private val _isButtonEnabled = MutableLiveData(false)
    val isButtonEnabled: LiveData<Boolean> = _isButtonEnabled

    var response: Response<Void>? by mutableStateOf(null)
    var isLoading: Boolean by mutableStateOf(false)
    var isCompleted: Boolean by mutableStateOf(false)
    var isError: Boolean by mutableStateOf(false)

    /**
     * Se encarga de darle formato al TimeStamp para poder hacer las solicitudes a la BBDD correctamente.
     */
    @SuppressLint("SimpleDateFormat")
    fun parseStringToTimestamp(dateTimeString: String): Timestamp {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val date: Date = format.parse(dateTimeString) as Date
        return Timestamp(date.time)
    }

    fun addLeadingZero(string: String): String {
        return string.padStart(2, '0')
    }

    private fun isValidDate(date: String): Boolean = date.length > 1
    fun onDateChange(value: String) {
        dateString = value
        _isButtonEnabled.value = isValidDate(value) && isValidNumberMax(numberMax)
    }

    private fun isValidNumberMax(numberMax: String): Boolean =
        numberMax.length > 0 && numberMax.toInt() > 1 && numberMax.toInt() < 30

    fun onNumberMaxChange(value: String) {
        numberMax = value.filter { it.isDigit() }
        _isButtonEnabled.value = isValidDate(dateString) && isValidNumberMax(value)
    }

    /**
     * Método encargado de crear un partido de fútbol en cierta pista.
     */
    fun postFootballMatch(latitude: String, longitude: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val formattedDate = parseStringToTimestamp(dateString)
            val newFootballMatch = FootballMatch(
                latitude.toDouble(),
                longitude.toDouble(),
                numberMax.toInt(),
                0,
                formattedDate
            )
            response = postFootballMatchUsecases.postFootballMatch(newFootballMatch)
            if (response != null) {
                if (response!!.isSuccessful) {
                    isCompleted = true
                    response!!.body()
                } else {
                    isError = true
                }
            }
        }
    }
}