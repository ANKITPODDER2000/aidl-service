package com.example.clientapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.example.serverapp.IDataShareAidlInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    private val _spValue = MutableStateFlow("")
    val spValue = _spValue.asStateFlow()

    fun getSpValue(binder: IDataShareAidlInterface?) {
        val spData = binder?.storedData ?: ""
        _spValue.value = spData.ifEmpty { "Use Client App to set some data" }
    }

}