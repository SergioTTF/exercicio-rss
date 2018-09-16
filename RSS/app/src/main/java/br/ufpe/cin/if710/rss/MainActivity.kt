package br.ufpe.cin.if710.rss

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.IOException

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Setando o layout manager para o RecyclerView
        conteudoRSS.layoutManager = LinearLayoutManager(this)
    }

    override fun onStart() {
        super.onStart()
        doAsync {
            try {
                // Fazendo um request usando a biblioteca OkHttp
                val client = OkHttpClient()
                val request = Request.Builder().url(getString(R.string.rssfeed)).build()
                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        e.printStackTrace()
                    }

                    @Throws(IOException::class)
                    override fun onResponse(call: Call, response: Response) {
                        if (!response.isSuccessful) {
                            throw IOException("Unexpected code $response")
                        } else {
                            // Dando parsing no rss e populando a RecyclerView
                            val parsedRSS = ParserRSS.parse(response.body()!!.string())
                            uiThread {
                                conteudoRSS.adapter = Adapter(this@MainActivity, parsedRSS)
                            }
                        }
                    }
                })
            } catch (e: Exception) {
                print(e.stackTrace)
            }
        }
    }
}
