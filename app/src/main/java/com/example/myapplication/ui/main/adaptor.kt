package com.example.myapplication.ui.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation

class adaptor: RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private var webinfo: Array<String> = Array(20) {""}
    private var weburl: Array<String> = Array(20) {""}
    private lateinit var context: Context

    fun init(context: Context, info: Array<String>, url: Array<String>) {
        webinfo = info
        weburl=url
        this.context = context
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(holder) {
            is ItemViewHolder -> {
                holder.bind(webinfo[position],weburl[position])
                holder.itemView.setOnClickListener {

                    val intent = Intent(context,dialog_fragment::class.java).apply {
                        putExtra("weburl",weburl[position])
                        putExtra("webinfo",webinfo[position])
                    }
                    context.startActivity(intent)
                }
            }

        }

    }


    override fun getItemCount(): Int {
        return webinfo.size
    }

    class ItemViewHolder constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView) {
        val webimage = itemView.findViewById<ImageView>(R.id.imageView)
        val webtext = itemView.findViewById<TextView>(R.id.textView)


            fun bind (webinfo: String,weburl: String) {
                Picasso.get().load(weburl).resize(500,500).transform(RoundedCornersTransformation(10, 0)).into(webimage)
                webtext.setText(webinfo)

        }


    }

}


