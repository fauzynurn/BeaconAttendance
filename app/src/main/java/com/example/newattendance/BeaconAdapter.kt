package com.example.newattendance

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eyro.cubeacon.CBBeacon
import kotlinx.android.synthetic.main.item.view.*

class BeaconAdapter(val items: MutableList<CBBeacon>?, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.minor?.text = items?.get(position)?.minor?.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item, parent, false))

    }

    override fun getItemCount(): Int {
        return items!!.size
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view){
    val minor = view.minor!!
}