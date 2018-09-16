package br.ufpe.cin.if710.rss

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.itemlista.view.*
import android.content.Intent
import android.net.Uri
import org.jetbrains.anko.sdk25.coroutines.onClick


class Adapter(private val context: Context, private val list: List<ItemRSS>): RecyclerView.Adapter<Adapter.NewViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.NewViewHolder {
        return Adapter.NewViewHolder(LayoutInflater.from(context).inflate(R.layout.itemlista, parent, false))
    }

    override fun onBindViewHolder(holder: Adapter.NewViewHolder, position: Int) {
        // Dando bind e populando cada item da lista
        holder.title?.text = list[position].title
        holder.data?.text = list[position].pubDate
        holder.title.onClick {
            val openURL = Intent(Intent.ACTION_VIEW, Uri.parse(list[position].link))
            holder.title.context.startActivity(openURL)
        }
    }

    override fun getItemId(index: Int): Long {
        return index.toLong()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class NewViewHolder (view:View) : RecyclerView.ViewHolder(view) {
        val title = view.item_titulo
        val data = view.item_data
    }

}