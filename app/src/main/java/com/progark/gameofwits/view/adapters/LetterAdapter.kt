package com.progark.gameofwits.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import com.progark.gameofwits.R
import model.User

class LetterItem(val letter: Char, var clicked: Boolean) {}

class LetterAdapter(private val mContext: Context, private val dataSource: List<LetterItem>) :
    ArrayAdapter<LetterItem?>(mContext, R.layout.lobby_row, dataSource) {
    private lateinit var callback: (view: View, position: Int, letter: Char) -> Unit

    private class ViewHolder {
        lateinit var button: Button
    }

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): LetterItem {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val viewHolder: ViewHolder
        val result: View
        if (convertView == null) {
            viewHolder = ViewHolder()
            convertView =
                LayoutInflater.from(parent.context).inflate(R.layout.letter_item, parent, false)
            viewHolder.button = convertView.findViewById(R.id.game_letter)
            result = convertView
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
            result = convertView
        }

        val item: LetterItem = getItem(position)
        viewHolder.button.text = item.letter.toString()
        viewHolder.button.setOnClickListener { view -> callback(view, position, item.letter) }
        viewHolder.button.isClickable = !item.clicked
        viewHolder.button.isEnabled = !item.clicked
        return result
    }

    fun setButtonOnclick(callback: (view: View, position: Int, letter: Char) -> Unit) {
        this.callback = callback
    }
}