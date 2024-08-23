package com.example.hakatonviews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.hakatonviews.databinding.FragmentDirectorauthBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DirectorAuthFragment : Fragment() {
    private var _binding: FragmentDirectorauthBinding? = null
    val binding get() = _binding!!
    lateinit var viewModel: DirectorAuthViewModel

    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val db: FirebaseDatabase = FirebaseDatabase.getInstance()
    val directrors: DatabaseReference = db.getReference("directors")



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDirectorauthBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(this).get(DirectorAuthViewModel::class.java)

        binding.directorAuthViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.btnRegWorker.setOnClickListener{
            view.findNavController().navigate(R.id.action_directorAuthFragment_to_directorRegFragment)
        }
        binding.btnAuthWorker.setOnClickListener {
            auth.signInWithEmailAndPassword(binding.etAuthWorkerEmail.text.toString(), binding.etAuthWorkerPassword.text.toString())
                .addOnSuccessListener {
                    view.findNavController().navigate(R.id.action_directorAuthFragment_to_directorFragment)
                }.addOnFailureListener {
                Snackbar.make(binding.AuthLayout, "Ошибка авторизации!", Snackbar.LENGTH_SHORT).show()
            }
        }


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}