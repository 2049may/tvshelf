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

    private val _seriesSearch: MutableStateFlow<List<Series>> = MutableStateFlow(emptyList())
    val seriesSearch: StateFlow<List<Series>> = _seriesSearch.asStateFlow()

    fun fetchSeriesForResearch() {
        viewModelScope.launch {
            try {
                val response12 = RetrofitInstance.api.getSeriesForSearch12()
                val response11 =  RetrofitInstance.api.getSeriesForSearch11()
                val response10 = RetrofitInstance.api.getSeriesForSearch10()
                val response9 =  RetrofitInstance.api.getSeriesForSearch9()
                val response8 = RetrofitInstance.api.getSeriesForSearch8()
                val response7 =  RetrofitInstance.api.getSeriesForSearch7()
                val response6 = RetrofitInstance.api.getSeriesForSearch6()
                val response5 =  RetrofitInstance.api.getSeriesForSearch5()
                val response4 = RetrofitInstance.api.getSeriesForSearch4()
                val response3 = RetrofitInstance.api.getSeriesForSearch3()
                val response2 =  RetrofitInstance.api.getSeriesForSearch2()
                val response1 = RetrofitInstance.api.getSeriesForSearch1()
                val response0 = RetrofitInstance.api.getSeriesForSearch0()
                _seriesSearch.value = (response10.results + response9.results + response8.results + response7.results + response6.results + response5.results + response4.results + response3.results + response2.results + response1.results + response11.results + response12.results + response0.results).distinct()
            } catch (e: Exception) {
                Log.e("Api error", e.localizedMessage ?: "")
            }
        }
    }
}