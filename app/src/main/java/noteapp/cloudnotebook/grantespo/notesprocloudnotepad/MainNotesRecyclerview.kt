package noteapp.cloudnotebook.grantespo.notesprocloudnotepad

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.util.ArrayList
import java.util.HashMap

class MainNotesRecyclerview// data is passed into the constructor
internal constructor(context: Context, private val mData: ArrayList<HashMap<String, String>>) : RecyclerView.Adapter<MainNotesRecyclerview.ViewHolder>() {
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mClickListener: ItemClickListener? = null

    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.noterow, parent, false)
        return ViewHolder(view)
    }

    // binds the data to the TextView in each row
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val stringTitle = mData[position]["titleFormatted"]
        val stringBody = mData[position]["bodyFormatted"]
        val stringUpdatedAt = mData[position]["date"]

        holder.rowtitle.text = stringTitle
        holder.rowBody.text = stringBody
        holder.rowUpdatedAt.text = stringUpdatedAt
    }

    // total number of rows
    override fun getItemCount(): Int {
        return mData.size
    }


    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        internal var rowtitle: TextView = itemView.findViewById(R.id.rowTitle)
        internal var rowBody: TextView = itemView.findViewById(R.id.rowBody)
        internal var rowUpdatedAt: TextView = itemView.findViewById(R.id.rowUpdatedAt)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            if (mClickListener != null) mClickListener!!.onItemClick(view, adapterPosition)
        }
    }

    // allows clicks events to be caught
    internal fun setClickListener(itemClickListener: ItemClickListener) {
        this.mClickListener = itemClickListener
    }

    // parent activity will implement this method to respond to click events
    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

}