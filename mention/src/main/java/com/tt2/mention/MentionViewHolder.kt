package com.tt2.mention

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.tt2.mention.util.GlideUtil

internal class MentionViewHolder(
    itemView: View,
    private val clickListener: (target: IMention) -> Unit,
    @DrawableRes private val placeHolder:Int
) : RecyclerView.ViewHolder(itemView) {

    private lateinit var textViewMentionList: TextView
    private lateinit var imageViewAvatar: ImageView
    fun bind(memberPresenter: IMention) {
        initUiViews()
        textViewMentionList.text = memberPresenter.name
        GlideUtil.loadAvatarImage(
            memberPresenter.avatar,
            imageViewAvatar,
            placeHolder,
            context = itemView.context
        )
        itemView.setOnClickListener {
            onClickCallBack(memberPresenter)
        }
    }

    private fun onClickCallBack(target: IMention) {
        clickListener.invoke(target)
    }

    fun initUiViews() {
        textViewMentionList = itemView.findViewById(R.id.name)
        imageViewAvatar = itemView.findViewById(R.id.avatar)
    }


}