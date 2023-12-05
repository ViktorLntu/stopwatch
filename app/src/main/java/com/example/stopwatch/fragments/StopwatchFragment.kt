package com.example.stopwatch.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.stopwatch.R
import com.example.stopwatch.databinding.FragmentStopwatchBinding
import com.example.stopwatch.viewmodels.StopwatchViewModel

class StopwatchFragment : Fragment() {
    private lateinit var binding: FragmentStopwatchBinding
    private lateinit var viewModel: StopwatchViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStopwatchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[StopwatchViewModel::class.java]

        viewModel.elapsedTime.observe(viewLifecycleOwner) {
            binding.elapsedTime.text = formatTime(it)
        }

        viewModel.isTimerRunning.observe(viewLifecycleOwner) {
            // Handle UI updates based on isTimerRunning changes if needed
            updateButtonUI()
        }

        // Set up button click listener
        binding.startStopButton.setOnClickListener {
            if (viewModel.isTimerRunning.value == true) {
                viewModel.stopTimer()
            } else {
                viewModel.startTimer()
            }
        }

        binding.resetButton.setOnClickListener {
            viewModel.resetTimer()
        }
    }

    private fun formatTime(milliseconds: Long): String {
        val totalSeconds = milliseconds / 1000
        val seconds = totalSeconds % 60
        val minutes = (totalSeconds / 60) % 60
        val hours = totalSeconds / 3600

        val millisecondsStr = String.format("%02d", (milliseconds % 1000) / 10)
        Log.e("formatTime", milliseconds.toString())

        return String.format("%02d:%02d:%02d:%s", hours, minutes, seconds, millisecondsStr)
    }

    private fun updateButtonUI() {
        val buttonTextResId = if (viewModel.isTimerRunning.value == true) R.string.stop else R.string.start
        val buttonIconResId = if (viewModel.isTimerRunning.value == true) R.drawable.ic_baseline_pause_24 else R.drawable.ic_baseline_play_arrow_24

        binding.startStopButton.setText(buttonTextResId)
        binding.startStopButton.icon =
            AppCompatResources.getDrawable(requireContext(), buttonIconResId)
    }
}