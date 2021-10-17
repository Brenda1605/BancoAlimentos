package mx.tec.bancoalimentos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewsViewModel : ViewModel() {

    //Instancia del repo
    private val repoNews = RepoNews()

    //Cuando mutable liveData se setea
    //Regresa una lista
    fun fetchNewsData(): LiveData<MutableList<News>> {
        //Crear mutableData y setear datos cuando se buscan de firebase
        val mutableData = MutableLiveData<MutableList<News>>()
        //Hacer query para traer datos de firebase
        //Este mtodo escucha hasta que se sete la lista de reportes en ViewModel
        //hata que el funci'on getRepotData regrese una lista
        repoNews.getNewsData().observeForever{ newsList ->
            mutableData.value = newsList
        }
        return mutableData
    }
}