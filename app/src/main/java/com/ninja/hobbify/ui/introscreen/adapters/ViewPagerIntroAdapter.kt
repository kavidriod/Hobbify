package com.ninja.hobbify.ui.introscreen.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ninja.hobbify.data.models.IntroView
import com.ninja.hobbify.databinding.IntroItemPageBinding

class ViewPagerIntroAdapter(introViews: List<IntroView>)
    : RecyclerView.Adapter<ViewPagerIntroAdapter.IntroViewHolder>() {

    private val introViewList = introViews

    class IntroViewHolder(val binding: IntroItemPageBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroViewHolder {
        val binding = IntroItemPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IntroViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return introViewList.size
    }

    override fun onBindViewHolder(holder: IntroViewHolder, position: Int) {
        var currentIntroView = introViewList[position]
        holder.binding.ivImageIntro.setImageResource(currentIntroView.imageId)
        holder.binding.tvDescriptionIntro.text = currentIntroView.description
    }
}