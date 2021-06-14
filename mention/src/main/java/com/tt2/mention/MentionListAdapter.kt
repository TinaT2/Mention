package com.tt2.mention

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import java.util.*

internal class MentionListAdapter(private val clickListener: (IMention) -> Unit, @DrawableRes private val placeHolder:Int) :
    RecyclerView.Adapter<MentionViewHolder>() {
    private var memberList = mutableListOf<IMention>()
    private var allMemberList = mutableListOf<IMention>()

    override fun onBindViewHolder(holder: MentionViewHolder, position: Int) {
        try {
            val assignedItem = memberList[position]
            holder.initUiViews()
            holder.bind(assignedItem)
        } catch (indexOutOfBoundException: IndexOutOfBoundsException) {
            Log.e(::MentionListAdapter.name, indexOutOfBoundException.message ?: "")
        }
    }

    fun setData(memberList: List<IMention>) {
        this.memberList = memberList.toMutableList()
        this.memberList.sortBy { it.name.toLowerCase(Locale.ROOT) }
        notifyDataSetChanged()
    }

    fun setAllData(memberPresenterList: List<IMention>) {
        allMemberList = memberPresenterList.toMutableList()
    }

    fun getData() = memberList

    fun getAllData() = allMemberList

    override fun getItemCount(): Int = memberList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MentionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mention, parent, false)
        return MentionViewHolder(view, clickListener,placeHolder)
    }
}