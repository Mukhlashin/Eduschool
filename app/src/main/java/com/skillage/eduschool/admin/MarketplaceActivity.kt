package com.skillage.eduschool.admin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skillage.eduschool.R
import com.skillage.eduschool.adapter.MarketplaceAdapter
import com.skillage.eduschool.model.Koperasi


class MarketplaceActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var marketplaceAdapter: MarketplaceAdapter
    private var dataList = mutableListOf<Koperasi>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marketplace)

        recyclerView = findViewById(R.id.rv)
        recyclerView.layoutManager = GridLayoutManager(applicationContext, 5)
        marketplaceAdapter = MarketplaceAdapter(applicationContext)
        recyclerView.adapter = marketplaceAdapter

        dataList.add(Koperasi("Baru Seragam SD Lengan Pendek", 40000, 33, R.drawable.gambar1))
        dataList.add(Koperasi("Celana Panjang SD Karet", 55000, 19, R.drawable.gambar2))
        dataList.add(Koperasi("Celana Panjang Pramuka SD", 55000, 12, R.drawable.gambar3))
        dataList.add(Koperasi("Seragam Sekolah CELANA", 40000, 9, R.drawable.gambar4))
        dataList.add(Koperasi("SERAGAM SEKOLAH", 75000, 14, R.drawable.gambar5))
        dataList.add(Koperasi("Baru Seragam SD Lengan Pendek", 40000, 33, R.drawable.gambar1))
        dataList.add(Koperasi("Celana Panjang SD Karet", 55000, 19, R.drawable.gambar2))
        dataList.add(Koperasi("Celana Panjang Pramuka SD", 55000, 12, R.drawable.gambar3))
        dataList.add(Koperasi("Seragam Sekolah CELANA", 40000, 9, R.drawable.gambar4))
        dataList.add(Koperasi("SERAGAM SEKOLAH", 75000, 14, R.drawable.gambar5))

        marketplaceAdapter.setDataList(dataList)
    }
}