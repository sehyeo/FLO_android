package com.example.flo_android.ui.main.locker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.flo_android.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment : BottomSheetDialogFragment() {

    lateinit var binding: FragmentBottomSheetBinding

    interface BottomSheetListener {
        fun onDeleteSelected()
    }

    private var listener: BottomSheetListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomSheetBinding.inflate(inflater, container, false)

        binding.btnDeleteSelectedIv.setOnClickListener {
            listener?.onDeleteSelected()
            dismiss()
        }

        return binding.root
    }

    fun setBottomSheetListener(listener: BottomSheetListener) {
        this.listener = listener
    }
}
