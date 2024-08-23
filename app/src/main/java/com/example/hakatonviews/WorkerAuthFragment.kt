package com.example.hakatonviews

import android.app.Activity.RESULT_OK
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.hakatonviews.databinding.FragmentWorkerauthBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.firebase.ui.auth.data.model.User
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue


class WorkerAuthFragment : Fragment() {
    private var _binding: FragmentWorkerauthBinding? = null
    val binding get() = _binding!!
    lateinit var viewModel: WorkerAutnViewModel

    companion object{
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        val db: FirebaseDatabase = FirebaseDatabase.getInstance()
        val users: DatabaseReference = db.getReference("Users")
        val userId = FirebaseAuth.getInstance().getCurrentUser()?.getUid()
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWorkerauthBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this).get(WorkerAutnViewModel::class.java)
        binding.workerAuthViewModel = viewModel

        binding.btnRegWorker.setOnClickListener{
            view.findNavController().navigate(R.id.action_workerAuthFragment_to_workerRegFragment)
        }

        binding.btnAuthWorker.setOnClickListener {
            auth.signInWithEmailAndPassword(binding.etAuthWorkerEmail.text.toString(),binding.etAuthWorkerPassword.text.toString())
                .addOnSuccessListener {
                    view.findNavController().navigate(R.id.action_workerAuthFragment_to_workerFragment)
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