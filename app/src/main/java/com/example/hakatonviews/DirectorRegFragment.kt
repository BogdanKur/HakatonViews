package com.example.hakatonviews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.hakatonviews.Models.User
import com.example.hakatonviews.databinding.FragmentDirectorRegBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class DirectorRegFragment : Fragment() {

    private var _binding: FragmentDirectorRegBinding? = null
    val binding get() = _binding!!
    lateinit var viewModel: DirectorRegViewModel

    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val db: FirebaseDatabase = FirebaseDatabase.getInstance()
    val directors: DatabaseReference = db.getReference("directors")
    val directorId = FirebaseAuth.getInstance().getCurrentUser()?.getUid()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDirectorRegBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this).get(DirectorRegViewModel::class.java)
        binding.directorRegViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.btnAuthWorkerReg.setOnClickListener {
            view.findNavController().navigate(R.id.action_directorRegFragment_to_directorAuthFragment)
        }

        binding.btnRegWorkerReg.setOnClickListener {
            auth.createUserWithEmailAndPassword(binding.etAuthWorkerEmailReg.text.toString(), binding.etAuthWorkerPasswordReg.text.toString())
                .addOnSuccessListener {
                    val user: User = User(binding.etAuthWorkerEmailReg.text.toString(), binding.etAuthWorkerPasswordReg.text.toString())
                    directors.child(directorId.toString())
                        .setValue(user)
                    view.findNavController().navigate(R.id.action_directorRegFragment_to_directorAuthFragment)
                }
        }



        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}