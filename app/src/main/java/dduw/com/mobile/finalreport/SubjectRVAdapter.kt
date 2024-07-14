package dduw.com.mobile.finalreport

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dduw.com.mobile.finalreport.data.Subject
import dduw.com.mobile.finalreport.databinding.ItemSubjectBinding

class SubjectRVAdapter(val subjects: ArrayList<Subject>) : RecyclerView.Adapter<SubjectRVAdapter.ViewHolder>() {
    val TAG = "SubjectAdapter"
    private var filteredSubjects = ArrayList<Subject>(subjects)
    private var queryText = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemSubjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemBinding.mainRvSubname.text = filteredSubjects[position].name
        holder.itemBinding.mainRvProfessor.text = filteredSubjects[position].professor
        holder.itemBinding.itemRvImgIv.setImageResource(filteredSubjects[position].coverImg)
    }

    override fun getItemCount(): Int = filteredSubjects.size

    inner class ViewHolder(val itemBinding: ItemSubjectBinding)
        : RecyclerView.ViewHolder(itemBinding.root) {
        init {
            itemBinding.root.setOnClickListener {
                listener?.onItemClick(it, adapterPosition)
                Log.d(TAG, "Click!")
            }
            itemBinding.root.setOnLongClickListener {
                longClickListener?.onItemLongClick(it, adapterPosition)
                true
            }
        }
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(view: View, position: Int): Boolean
    }
    var longClickListener: OnItemLongClickListener? = null

    fun setOnItemLongClickListener(listener: OnItemLongClickListener?) {
        this.longClickListener = listener
    }

    var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    fun filter(query: String) {
        queryText = query
        filteredSubjects = if (query.isEmpty()) {
            ArrayList(subjects)
        } else {
            val filteredList = subjects.filter {
                it.name.contains(query, true) || it.professor.contains(query, true)
            }
            ArrayList(filteredList)
        }
        notifyDataSetChanged()
    }
}