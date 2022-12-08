package com.example.moviestime.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.moviestime.core.Resource
import com.example.moviestime.repository.MovieRepository
import kotlinx.coroutines.Dispatchers

//Abstraemos lo que se usa en implements y solo traemos la interfaz
class MovieViewModel(private val repo: MovieRepository): ViewModel() {

    //Necesitamos asignarle un HILO DE EJECUCIÓN
    fun fetchMainScreenMovies() = liveData(Dispatchers.IO){
        //3 tipos de estado carga, exito, error
        emit(Resource.Loading())

        try {
            emit(Resource.Success(Triple(
                repo.getUpcomingMovies(),
                repo.getTopRatedMovies(),
                repo.getPopularMovies()
            )))
        }catch (e :Exception){
            emit(Resource.Failure(e))
        }
    }
}

//CREAMOS NUEVA INSTANCIA DE REPO DE TIPO MOVIEREPOSITORY PORQUE NO ESTÁ BIEN PASARLO POR CONSTRUCTOR DE VM
class MovieViewModelFactory(private val repo: MovieRepository): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MovieRepository::class.java).newInstance(repo)
    }

}