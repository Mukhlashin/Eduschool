package com.skillage.eduschool.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.skillage.eduschool.R
import com.skillage.eduschool.model.Koperasi

class MarketplaceAdapter(private val context: Context) : RecyclerView.Adapter<MarketplaceAdapter.ViewHolder>() {

    private var dataList = emptyList<Koperasi>()

    internal fun setDataList(dataList : List<Koperasi>) {
        this.dataList = dataList
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.namaBarang.text = data.namaBarang
        holder.hargaBarang.text = "Rp." + data.hargaBarang.toString()
        holder.soldBarang.text = "Terjual " + data.soldBarang.toString()
        holder.fotoBarang.setImageResource(data.fotoBarang)
    }

    override fun getItemCount() = dataList.size

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var namaBarang : TextView
        var hargaBarang : TextView
        var soldBarang : TextView
        var fotoBarang : ImageView

        init {
            namaBarang = itemView.findViewById(R.id.nama_barang)
            hargaBarang = itemView.findViewById(R.id.harga_barang)
            soldBarang = itemView.findViewById(R.id.sold_barang)
            fotoBarang = itemView.findViewById(R.id.foto_barang)
        }
    }
}