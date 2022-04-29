package cg.p.cgassignment2.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cg.p.cgassignment2.R

import cg.p.cgassignment2.databinding.CardListBinding
import cg.p.cgassignment2.models.ScoutGroupModels


interface AddClickListener {
    fun onAddGroupClick(groupModels: ScoutGroupModels)
}

class AddgroupAdapter constructor(private var listGroups: List<ScoutGroupModels>,
                                  private val listener: AddClickListener)
    : RecyclerView.Adapter<AddgroupAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardListBinding.inflate(LayoutInflater.from(parent.context), parent, false)


        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val addedgroup = listGroups[holder.adapterPosition]
        holder.bind(addedgroup,listener)
    }

    override fun getItemCount(): Int = listGroups.size

    inner class MainHolder(val binding : CardListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(groupModels: ScoutGroupModels, listener: AddClickListener) {
            //binding.listgroup
            binding.listgroup = groupModels
            //binding.imageIcon.setImageResource(R.mipmap.ic_launcher_round)
            binding.root.setOnClickListener { listener.onAddGroupClick(groupModels) }

        }
    }
}