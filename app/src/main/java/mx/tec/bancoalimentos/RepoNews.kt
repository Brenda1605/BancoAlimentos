package mx.tec.bancoalimentos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore

class RepoNews {

    //Traer datos desde firebase

    fun getNewsData(): LiveData<MutableList<News>> {
        val mutableData = MutableLiveData<MutableList<News>>()
        //Crear instancia de base de datos y leer todos los documentos de la colección
        //Guardar datos objetos y objetos en una lista
        FirebaseFirestore.getInstance().collection("News").get().addOnSuccessListener{ result ->
            val listData: MutableList<News> = mutableListOf<News>()
            for (document in result){
                var date = document.getString("date")
                var img = document.getString("img")
                var title = document.getString("title")
                var body = document.getString("body")

                var news = News(date!!, img!!, title!!, body!!)
                listData.add(news)
            }
            mutableData.value = listData
        }
        return mutableData
    }
}