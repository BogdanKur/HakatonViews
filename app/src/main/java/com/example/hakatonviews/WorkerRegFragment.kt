package com.example.hakatonviews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.hakatonviews.Models.User
import com.example.hakatonviews.databinding.FragmentWorkerRegBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase



class WorkerRegFragment : Fragment() {
    private var _binding: FragmentWorkerRegBinding? = null
    val binding get() = _binding!!
    lateinit var viewModel: WorkerRegViewModel





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWorkerRegBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this).get(WorkerRegViewModel::class.java)
        binding.workerRegViewModel = viewModel

        binding.btnAuthWorkerReg.setOnClickListener{
            view.findNavController().navigate(R.id.action_workerRegFragment_to_workerAuthFragment)
        }

        binding.btnRegWorkerReg.setOnClickListener {
            WorkerAuthFragment.auth.createUserWithEmailAndPassword(binding.etAuthWorkerEmailReg.text.toString(), binding.etAuthWorkerPasswordReg.text.toString())
                .addOnSuccessListener {
                    val user: User = User(binding.etAuthWorkerEmailReg.text.toString(), binding.etAuthWorkerPasswordReg.text.toString(), 0)

                    viewModel.addNewUser(user.email!!, user.password!!, user.time!!)
                    view.findNavController().navigate(R.id.action_workerRegFragment_to_workerAuthFragment)

                }
        }



        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}




