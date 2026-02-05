package com.example.sensorlogger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SimpleVmFactory<T : ViewModel>(
    private val creator: () -> T
) : ViewModelProvider.Factory {
    override fun <VM : ViewModel> create(modelClass: Class<VM>): VM {
        @Suppress("UNCHECKED_CAST")
        return creator() as VM
    }
}
