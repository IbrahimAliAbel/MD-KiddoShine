package com.example.kiddoshine.ui.ListAnak

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiddoshine.Adapter.AnakAdapter
import com.example.kiddoshine.dataanak.AkunAnak
import com.example.kiddoshine.dataanak.AnakViewModel
import com.example.kiddoshine.dataanak.InputAkunAnak
import com.example.kiddoshine.database.Anak
import com.example.kiddoshine.databinding.FragmentListAnakBinding
import com.example.kiddoshine.repository.AnakViewModelFactory

class ListAnakFragment : Fragment() {

    private var _binding: FragmentListAnakBinding? = null
    private val binding get() = _binding!!
    private lateinit var anakViewModel: AnakViewModel
    private lateinit var adapter: AnakAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListAnakBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi ViewModel
        val application = requireActivity().application
        val factory = AnakViewModelFactory(application)
        anakViewModel = ViewModelProvider(this, factory).get(AnakViewModel::class.java)

        // Setup RecyclerView
        adapter = AnakAdapter(
            anakList = emptyList(),
            context = requireContext(),
            onItemClicked = { anak ->
            val intent = Intent(requireContext(), AkunAnak::class.java)
            intent.putExtra("ANAK_ID", anak.id) // Kirim ID anak
            startActivity(intent)
        },
        onItemLongClicked = { anak ->
            // Tampilkan dialog konfirmasi hapus
            showDeleteConfirmationDialog(anak)
        },
            onAddClicked = {
                // Navigasi ke halaman InputAkunAnak
                startActivity(Intent(requireContext(), InputAkunAnak::class.java))
            })


        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        // Observasi data dari ViewModel
        anakViewModel.allAnak.observe(viewLifecycleOwner) { anakList ->
            adapter.updateData(anakList)
        }

        // Tombol Add untuk menambah data baru

    }

    private fun showDeleteConfirmationDialog(anak: Anak) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Hapus Data")
        builder.setMessage("Apakah Anda yakin ingin menghapus data anak ${anak.nama}?")
        builder.setPositiveButton("Ya") { _, _ ->
            // Hapus data dari database
            anakViewModel.deleteAnak(anak)
            Toast.makeText(
                requireContext(),
                "Data anak ${anak.nama} telah dihapus",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("Tidak", null) // Tutup dialog tanpa aksi
        builder.show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}