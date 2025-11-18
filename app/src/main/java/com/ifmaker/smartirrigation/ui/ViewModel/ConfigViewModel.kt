package com.ifmaker.smartirrigation.ui.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ifmaker.smartirrigation.data.Repository.ConfigRepository
import kotlinx.coroutines.launch

class ConfigViewModel: ViewModel() {

    private val repository = ConfigRepository()
    private val _latitude = MutableLiveData<Double>()
    val latitude: LiveData<Double> = _latitude

    fun getLatitude() {
        viewModelScope.launch {
            _latitude.value = repository.getLatitude()
        }
    }

    fun alterarLatitude(latitude: Double){
        viewModelScope.launch {
            repository.setLatitude(latitude, callback = {ok ->
                _latitude.value = latitude
            })
        }
    }

}