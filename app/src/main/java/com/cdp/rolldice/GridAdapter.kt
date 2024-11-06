package com.cdp.rolldice

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView

class GridAdapter(private val context: Context, private val diceList: List<Pair<Int, Int>>) :
    BaseAdapter() {
    override fun getCount(): Int {
        return diceList.size
    }

    override fun getItem(position: Int): Any {
        return diceList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View =
            convertView ?: LayoutInflater.from(context).inflate(R.layout.item_dice, parent, false)
        val diceImage: ImageView = view.findViewById(R.id.diceImage)

        val (imageResId, _) = diceList[position]
        diceImage.setImageResource(imageResId)

        return view
    }
}