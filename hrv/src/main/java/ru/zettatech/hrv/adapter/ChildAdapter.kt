package ru.zettatech.hrv.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.zettatech.hrv.R
import ru.zettatech.hrv.item.ChildItem

class ChildAdapter (private val childItemList: List<ChildItem>, private val onClickListener: OnChildItemClickListener) :
RecyclerView.Adapter<ChildAdapter.ViewHolder>() {

    interface OnChildItemClickListener {
        fun onChildItemClick(childItem: ChildItem, position: Int)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView
        val title: TextView

        init {
            image = view.findViewById(R.id.image)
            title = view.findViewById(R.id.title)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_child, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val childItem: ChildItem = childItemList.get(position)

        viewHolder.title.text = childItem.getTitle()
        viewHolder.image.setImageResource(childItem.getResourceId())

        // TODO Align items to screen width
        // val displayMetrics: DisplayMetrics = viewHolder.itemView.context.getResources().getDisplayMetrics()
        // val width = displayMetrics.widthPixels / 2 // Half the screen width
        // val layoutParams: ViewGroup.LayoutParams = viewHolder.itemView.getLayoutParams()
        // layoutParams.width = width
        // viewHolder.itemView.setLayoutParams(layoutParams)

        viewHolder.itemView.setOnClickListener {
            onClickListener.onChildItemClick(childItem, position)
        }
    }

    override fun getItemCount() = childItemList.size
}