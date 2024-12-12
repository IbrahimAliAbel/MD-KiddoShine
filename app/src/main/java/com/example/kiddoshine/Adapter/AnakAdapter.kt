package com.example.kiddoshine.Adapter

import java.io.File
import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kiddoshine.R
import com.example.kiddoshine.database.Anak
import com.example.kiddoshine.databinding.ItemAddButtonBinding
import com.example.kiddoshine.databinding.ItemAnakBinding

class AnakAdapter(
    private var anakList: List<Anak>,
    private val context: Context,
    private val onItemClicked: (Anak) -> Unit,
    private val onItemLongClicked: (Anak) -> Unit,
    private val onAddClicked: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_ANAK = 0
        private const val VIEW_TYPE_ADD = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == anakList.size) VIEW_TYPE_ADD else VIEW_TYPE_ANAK
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ANAK) {
            val binding = ItemAnakBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            AnakViewHolder(binding)
        } else {
            val binding = ItemAddButtonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            AddViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AnakViewHolder) {
            val anak = anakList[position]
            holder.bind(anak, context)
            holder.itemView.setOnClickListener { onItemClicked(anak) }
            holder.itemView.setOnLongClickListener {
                onItemLongClicked(anak)
                true
            }
        } else if (holder is AddViewHolder) {
            holder.bind(onAddClicked)
        }
    }

    override fun getItemCount() = anakList.size + 1

    fun updateData(newAnakList: List<Anak>) {
        anakList = newAnakList
        notifyDataSetChanged()
    }

    class AnakViewHolder(private val binding: ItemAnakBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(anak: Anak, context: Context) {
            binding.tvItemName.text = anak.nama
            binding.tvItemDescription.text = "Tinggi Badan: ${anak.tinggiBadan} cm\nBerat Badan: ${anak.beratBadan} kg"
            // Load gambar
                    if (!anak.photoPath.isNullOrEmpty()) {
                        val file = File(anak.photoPath)
                        if (file.exists()) {
                            val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                            binding.imgItemPhoto.setImageBitmap(bitmap)
                        } else {
                            Log.d("AnakAdapter", "File not found: ${anak.photoPath}")
                            binding.imgItemPhoto.setImageResource(R.drawable.rb_174367) // Gambar default
                        }
                    } else {
                        binding.imgItemPhoto.setImageResource(R.drawable.rb_174367) // Gambar default
                    }
        }
    }
    class AddViewHolder(private val binding: ItemAddButtonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(onAddClicked: () -> Unit) {
            binding.btnAdd.setOnClickListener { onAddClicked() }
        }
    }
}