package mx.tec.bancoalimentos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReportViewModel: ViewModel() {

    //Instancia del repo
    private val repo = Repo()

    //Cuando mutable liveData se setea
    //Regresa una lista
    fun fetchReportData(): LiveData<MutableList<Report>> {
        //Crear mutableData y setear datos cuando se buscan de firebase
        val mutableData = MutableLiveData<MutableList<Report>>()
        //Hacer query para traer datos de firebase
        //Este mtodo escucha hasta que se sete la lista de reportes en ViewModel
        //hata que el funci'on getRepotData regrese una lista
        repo.getReportData().observeForever{ reportList ->
            mutableData.value = reportList
        }
        return mutableData
    }
}