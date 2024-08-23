package com.example.hakatonviews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hakatonviews.databinding.FragmentChooseBinding
import androidx.navigation.findNavController


class ChooseFragment : Fragment() {
    private var _binding: FragmentChooseBinding? = null
    val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChooseBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.btnWorkerStart.setOnClickListener{
            view.findNavController().navigate(R.id.action_chooseFragment_to_workerAuthFragment)
        }

        binding.btnDirectorStart.setOnClickListener{
            view.findNavController().navigate(R.id.action_chooseFragment_to_directorAuthFragment)
        }


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
