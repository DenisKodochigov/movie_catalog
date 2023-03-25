package com.example.movie_catalog.entity

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.example.movie_catalog.R
import com.example.movie_catalog.data.DataRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ErrorApp @Inject constructor(private var dataRepository: DataRepository,
                                   @ApplicationContext val cont: Context) {

    fun errorApi (errorMessage:String){
        val toastMessage: String
        when(errorMessage){
            "HTTP 401 " -> {
                toastMessage = cont.getString(R.string.error401)
            }
            "HTTP 402 " -> {
                toastMessage = cont.getString(R.string.error402)
                setNewKeyApi()
            }
            "HTTP 404 " -> {
                toastMessage = cont.getString(R.string.error404)
            }
            "HTTP 429 " -> {
                toastMessage = cont.getString(R.string.error429)
            }
            else -> {
                toastMessage = cont.getString(R.string.errorUser)
                Log.d("KDS", "Error $errorMessage")
            }

        }
        if (toastMessage.isNotEmpty()) {
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(cont, toastMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun setNewKeyApi(){
//        val dataRepository = DataRepository()
        dataRepository.setApiKey()
    }
}

