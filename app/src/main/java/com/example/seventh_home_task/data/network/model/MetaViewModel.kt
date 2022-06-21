package com.example.seventh_home_task.data.network.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seventh_home_task.data.network.ClevertecApi
import com.example.seventh_home_task.domain.models.Fields
import com.example.seventh_home_task.domain.models.Form
import kotlinx.coroutines.launch
import java.lang.Exception

class MetaViewModel : ViewModel() {
    private val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title
    private val _imgSrcUrl = MutableLiveData<String>()
    val imgSrcUrl: LiveData<String> = _imgSrcUrl
    private val _listField = MutableLiveData<List<Fields>>()
    val listField: LiveData<List<Fields>> = _listField
    private val _sendMeta = MutableLiveData<String>()
    val sendMeta: LiveData<String> = _sendMeta
    val sendForm = MutableLiveData<Form>()

    init {
        getClevertecMeta()
    }

    private fun getClevertecMeta() {
        viewModelScope.launch {
            try {
                val listResult = ClevertecApi.retrofitService.getMeta()
                _title.value = listResult.title
                _imgSrcUrl.value = listResult.image
                _listField.value = listResult.fields
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun sendClevertecMeta() {
        viewModelScope.launch {
            try {
                val needList = ClevertecApi.retrofitService.getResult(sendForm.value)
                _sendMeta.value = needList?.result
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}