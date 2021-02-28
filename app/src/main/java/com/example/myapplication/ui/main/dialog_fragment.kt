package com.example.myapplication.ui.main


import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.squareup.picasso.Picasso
import com.tonyodev.fetch2.*
import com.tonyodev.fetch2.NetworkType
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation


class dialog_fragment : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_fragment)

        val weburl : String = intent.getStringExtra("weburl").toString()
        val webinfo : String = intent.getStringExtra("webinfo").toString()


        val textView: TextView = findViewById(R.id.infotext)
        val imageView: ImageView = findViewById(R.id.image)

        textView.setText(webinfo)

        Picasso.get().load(weburl).transform(RoundedCornersTransformation(10, 0)).into(imageView)

        val btndone: Button = findViewById(R.id.done)
        val btndowmlaod: Button = findViewById(R.id.download)

        btndone.setOnClickListener {
            val intent = Intent(baseContext, MainActivity::class.java)
            startActivity(intent)
        }
        btndowmlaod.setOnClickListener {
            val fetchConfiguration = FetchConfiguration.Builder(this).setDownloadConcurrentLimit(3).build()
            val fetch = Fetch.Impl.getInstance(fetchConfiguration)
            val request = Request(
                weburl, Environment.getExternalStorageDirectory().path + "/Download/${
                    weburl.substring(
                        weburl.length - 10,
                        weburl.length
                    )
                }"
            )
            request.priority = Priority.HIGH
            request.networkType = NetworkType.ALL
            request.addHeader("clientKey", "SD78DF93_3947&MVNGHE1WONG")

            fetch.enqueue(request,
                { updatedRequest: Request? ->
                    Toast.makeText(
                        baseContext,
                        "Downloaded Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            ) { error: Error? -> Toast.makeText(baseContext, "Download Failed", Toast.LENGTH_SHORT).show()}
        }
}
}