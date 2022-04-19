package com.modulesoftware.viewpagerwithlistadaptercurrentpagechangeonsubmitbug

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val SIMPLE_DATA_LIST_1 = listOf(
    SimpleData(1, "SimpleData 1"),
    SimpleData(2, "SimpleData 2"),
    SimpleData(3, "SimpleData 3")
)

val SIMPLE_DATA_LIST_2 = listOf(
    SimpleData(51, "SimpleData 51"),
    SimpleData(52, "SimpleData 52"),
    // This one intentionally the same and the only one which is the same from the list 1
    SimpleData(3, "SimpleData3")
)


class MainActivityViewModel : ViewModel() {

    private val _simpleDatas = MutableLiveData<List<SimpleData>>()
    val simpleData: LiveData<List<SimpleData>>
        get() = _simpleDatas

    init {

    }

    fun fillLists() {

        viewModelScope.launch {
            // Imagine we get emit from local cache with LIST_1 dataset
            _simpleDatas.value = SIMPLE_DATA_LIST_1

            // Making API response for fresh data
            delay(3000)
            // New data set contains LIST_2
            _simpleDatas.value = SIMPLE_DATA_LIST_2
        }
    }
}