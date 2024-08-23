package com.example.hakatonviews

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.hakatonviews.databinding.FragmentWorkerBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class WorkerFragment : Fragment() {

    private var _binding: FragmentWorkerBinding? = null
    val binding get() = _binding!!
    lateinit var viewModel: WorkerViewModel
    var isWifiConnection: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWorkerBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(this).get(WorkerViewModel::class.java)

        binding.workerViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                val wifiChecker =
                    context?.let { WifiReceiver(it) }
                if (wifiChecker!!.isWifiConnected()) {
                    val ssid = wifiChecker.getWifiSSID()?.trim('"')
                    if (ssid != null) {
                        ssid.trim('"')
                        Log.i("Tags", ssid.trim('"'))
                        if(ssid.trim('"') == "TP-Link_392C") {
                            isWifiConnection = true
                        }
                        else {
                            isWifiConnection = false
                        }
                    }
                }
                if(isWifiConnection) {
                    viewModel.saveOffset()
                    viewModel.updateChronometer()
                    viewModel.setBasetime()
                    binding.chronometer.base = viewModel.chronometerBase.value!!
                    binding.chronometer.start()
                }
                if(!isWifiConnection){
                    binding.chronometer.stop()
                }
                handler.postDelayed(this, 10000)
            }
        }
        handler.post(runnable)




        if(isWifiConnection) {
            viewModel.setBasetime()
            binding.chronometer.base = viewModel.chronometerBase.value!!
            binding.chronometer.start()
        }
        if(!isWifiConnection){
            binding.chronometer.stop()
        }

        return view
    }

    override fun onPause() {
        super.onPause()
        viewModel.saveOffset()
        viewModel.updateChronometer()

    }


    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.saveOffset()
        viewModel.updateChronometer()
        binding.chronometer.stop()
    }

}