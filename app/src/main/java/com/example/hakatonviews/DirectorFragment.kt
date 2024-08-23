package com.example.hakatonviews

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.hakatonviews.WorkerAuthFragment.Companion.users
import com.example.hakatonviews.databinding.FragmentDirectorBinding
import com.firebase.ui.auth.data.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

class DirectorFragment : Fragment() {

    private var _binding: FragmentDirectorBinding? = null
    val binding get() = _binding!!

    lateinit var viewModel: DirectorViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =  FragmentDirectorBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(this).get(DirectorViewModel::class.java)
        binding.directorViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        val adapter = UsersItemAdapter()
        binding.usersList.adapter = adapter

        viewModel.users.observe(viewLifecycleOwner, Observer { users ->
                adapter.user = users
        })

        return view
    }


}