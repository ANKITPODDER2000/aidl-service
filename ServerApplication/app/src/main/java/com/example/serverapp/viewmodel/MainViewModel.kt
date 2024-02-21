package com.example.serverapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.serverapp.AppConstant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _sharedPrefValue = MutableStateFlow("")
    val sharedPrefValue = _sharedPrefValue.asStateFlow()

    fun init(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val sp = context.getSharedPreferences(AppConstant.PREF_NAME, AppConstant.MODE)
            _sharedPrefValue.value = sp.getString(AppConstant.STORED_STRING_KEY, "") ?: ""
        }
    }

    fun updateSharedPreference(context: Context, str: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val sp = context.getSharedPreferences(AppConstant.PREF_NAME, AppConstant.MODE)
            val editor = sp.edit()
            editor.putString(AppConstant.STORED_STRING_KEY, str)
            if (editor.commit()) {
                _sharedPrefValue.value = str
            }
        }
    }

}