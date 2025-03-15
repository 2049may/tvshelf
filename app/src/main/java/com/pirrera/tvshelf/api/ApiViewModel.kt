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
                val apiKey = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyMzJjMjI5NGQzMDI2ZGFhOWE4MjczZDljNjI0YzRkOCIsIm5iZiI6MTc0MTM0MjAyMC43MDU5OTk5LCJzdWIiOiI2N2NhYzU0NDMwZjQ0NDRjNmIyYjUyYmUiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.f9qcNl_3s4m01OIHGIMBrAosiboTjUDYx_Z6sJlhzhI"
                val response = RetrofitInstance.api.getSeries()
                _series.value = response.results
            }catch (e:Exception){
                Log.e("Api error", e.localizedMessage ?: "")
            }
        }
    }
}