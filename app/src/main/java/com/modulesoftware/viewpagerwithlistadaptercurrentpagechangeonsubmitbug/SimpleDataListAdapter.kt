package com.modulesoftware.viewpagerwithlistadaptercurrentpagechangeonsubmitbug

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.modulesoftware.viewpagerwithlistadaptercurrentpagechangeonsubmitbug.databinding.ItemSimpleDataBinding


class SimpleDataListAdapter() : ListAdapter<SimpleData, SimpleDataListAdapter.SimpleDataViewHolder>(SimpleDataDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleDataViewHolder {
        return SimpleDataViewHolder.createFrom(parent)
    }

    override fun onBindViewHolder(holder: SimpleDataViewHolder, position: Int) {

        val simpleData = getItem(position)

        holder.bind(simpleData)
    }

    override fun getItemId(position: Int): Long {

        val simpleData = getItem(position)

        return simpleData.id
    }

    class SimpleDataViewHolder private constructor(
        private val binding: ItemSimpleDataBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(simpleData: SimpleData) {

            binding.title.text = simpleData.toString()
        }

        companion object {

            fun createFrom(parent: ViewGroup): SimpleDataViewHolder {

                val binding = ItemSimpleDataBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )

                return SimpleDataViewHolder(binding)
            }
        }
    }
}

class SimpleDataDiffCallback : DiffUtil.ItemCallback<SimpleData>() {

    override fun areItemsTheSame(oldItem: SimpleData, newItem: SimpleData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SimpleData, newItem: SimpleData): Boolean {
        return oldItem == newItem
    }
}