package mx.tec.bancoalimentos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore

class Repo {

    //Traer datos desde firebase

    fun getReportData(): LiveData<MutableList<Report>> {
        val mutableData = MutableLiveData<MutableList<Report>>()
        //Crear instancia de base de datos y leer todos los documentos de la colección
        //Guardar datos objetos y objetos en una lista
        FirebaseFirestore.getInstance().collection("Reportes").get().addOnSuccessListener{ result ->
            val listData: MutableList<Report> = mutableListOf<Report>()
            for (document in result){
                var date = document.getString("date")
                var report = Report(date!!)
                listData.add(report)
            }
            mutableData.value = listData
        }
        return mutableData
    }
}