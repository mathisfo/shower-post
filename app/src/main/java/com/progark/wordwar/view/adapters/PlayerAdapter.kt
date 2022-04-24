package com.progark.wordwar.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.progark.wordwar.R
import model.User

class PlayerAdapter(private val mContext: Context, private val dataSource: List<User>) :
    ArrayAdapter<User?>(mContext, R.layout.lobby_row, dataSource) {

    private class ViewHolder {
        lateinit var textView: TextView
        lateinit var checkBox: CheckBox
    }

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): User {
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
                LayoutInflater.from(parent.context).inflate(R.layout.lobby_row, parent, false)
            viewHolder.textView = convertView.findViewById(R.id.row_text)
            viewHolder.checkBox = convertView.findViewById(R.id.player_row_checkbox)
            result = convertView
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
            result = convertView
        }

        val item: User = getItem(position)
        viewHolder.textView.text = item.name
        viewHolder.checkBox.isChecked = false
        return result
    }
}