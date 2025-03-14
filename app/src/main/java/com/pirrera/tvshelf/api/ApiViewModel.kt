package com.pirrera.tvshelf.api

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pirrera.tvshelf.data.Series
import com.pirrera.tvshelf.data.SeriesData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ApiViewModel: ViewModel() {
    private val _series: MutableStateFlow<List<Series>> = MutableStateFlow(emptyList())
    val series: StateFlow<List<Series>> = _series.asStateFlow()

    fun fetchSeries(){
        viewModelScope.launch{
            try{
                val response = RetrofitInstance.api.getSeries()

                _series.value = response.results
            }catch (e:Exception){
                Log.e("Api error", e.localizedMessage ?: "")
            }
        }
    }

    private val _seriesAction: MutableStateFlow<List<Series>> = MutableStateFlow(emptyList())
    val seriesAction: StateFlow<List<Series>> = _seriesAction.asStateFlow()

    fun fetchSeriesByAction(){
        viewModelScope.launch{
            try{
                val response = RetrofitInstance.api.getSeriesByAction()

                _seriesAction.value = response.results
            }catch (e:Exception){
                Log.e("Api error", e.localizedMessage ?: "")
            }
        }
    }

    private val _seriesFictionFantasy: MutableStateFlow<List<Series>> = MutableStateFlow(emptyList())
    val seriesFictionFantasy: StateFlow<List<Series>> = _seriesFictionFantasy.asStateFlow()

    fun fetchSeriesByFictionFantasy(){
        viewModelScope.launch{
            try{
                val response = RetrofitInstance.api.getSeriesByFictionFantasy()

                _seriesFictionFantasy.value = response.results
            }catch (e:Exception){
                Log.e("Api error", e.localizedMessage ?: "")
            }
        }
    }

    private val _seriesCrime: MutableStateFlow<List<Series>> = MutableStateFlow(emptyList())
    val seriesCrime: StateFlow<List<Series>> = _seriesCrime.asStateFlow()

    fun fetchSeriesByCrime(){
        viewModelScope.launch{
            try{
                val response = RetrofitInstance.api.getSeriesByCrime()

                _seriesCrime.value = response.results
            }catch (e:Exception){
                Log.e("Api error", e.localizedMessage ?: "")
            }
        }
    }

    private val _seriesComedy: MutableStateFlow<List<Series>> = MutableStateFlow(emptyList())
    val seriesComedy: StateFlow<List<Series>> = _seriesComedy.asStateFlow()

    fun fetchSeriesByComedy() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getSeriesByComedy()

                _seriesComedy.value = response.results
            } catch (e: Exception) {
                Log.e("Api error", e.localizedMessage ?: "")
            }
        }
    }

    private val _seriesDrama: MutableStateFlow<List<Series>> = MutableStateFlow(emptyList())
    val seriesDrama: StateFlow<List<Series>> = _seriesDrama.asStateFlow()

    fun fetchSeriesByDrama() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getSeriesByDrama()

                _seriesDrama.value = response.results
            } catch (e: Exception) {
                Log.e("Api error", e.localizedMessage ?: "")
            }
        }
    }
}