package com.example.newsapi

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.example.newsapi.databinding.ActivityMainBinding
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binder: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binder.root)

        val retrofitService = RetrofitInstanc.getRetrofitInstance().create(AlbumServiceInterface::class.java)

        val responseLiveData: LiveData<Response<AlbumItemArray>> =
            liveData {
                val response = retrofitService.getAlbums()
                emit(response)
            }

        responseLiveData.observe(this, Observer { response ->
            if (response.isSuccessful) {
                val albumitem = response.body()?.listIterator()
                if (albumitem != null) {
                    while (albumitem.hasNext()) {
                        val albumItem = albumitem.next()
                        val result = "Album UserID: ${albumItem.userid} \nAlbum ID: ${albumItem.id} \nAlbum Title : ${albumItem.title} \n\n"
                        binder.textData.append(result)
                    }
                }
            }
        })
    }
}
