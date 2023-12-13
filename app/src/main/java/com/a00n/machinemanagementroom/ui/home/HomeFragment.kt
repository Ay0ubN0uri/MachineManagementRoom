package com.a00n.machinemanagementroom.ui.home

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.a00n.machinemanagementroom.data.local.entities.Machine
import com.a00n.machinemanagementroom.data.local.entities.Marque
import com.a00n.machinemanagementroom.databinding.FragmentHomeBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        homeViewModel.marquesWithMachineCount.observe(viewLifecycleOwner) {marquesWithMachineCount->
            Log.i("info", "onCreateView: $marquesWithMachineCount")
            val barEntries = marquesWithMachineCount.mapIndexed { index, item ->
                BarEntry(index.toFloat(), item.machineCount.toFloat())
            }
            val barDataSet = BarDataSet(barEntries, "Machines per Marque")
            barDataSet.colors = ColorTemplate.JOYFUL_COLORS.toList()

            val barData = BarData(barDataSet)

            barDataSet.valueTextSize = 15f

            binding.barChart.data = barData

            val xAxis = binding.barChart.xAxis
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)

            val marqueNames = marquesWithMachineCount.map { it.marque.name }.toTypedArray()
            xAxis.valueFormatter = IndexAxisValueFormatter(marqueNames)

            binding.barChart.invalidate()
        }
        homeViewModel.marquesWithMachines.observe(viewLifecycleOwner) {
            Log.i("info", "onCreateView: $it")
        }
        return binding.root
    }

    fun sampleData() {
        val marque1 = Marque(0, "ABC", "Marque1")
        val marque2 = Marque(0, "XYZ", "Marque2")
        homeViewModel.insertMarque(marque1) { result ->
            when (result) {
                is InsertResult.Success -> {
                    val machine1 = Machine(0, "machine 1", "1999.9", result.insertedMarqueId)
                    val machine2 = Machine(0, "machine 2", "123.9", result.insertedMarqueId)
                    val machine3 = Machine(0, "machine 3", "1.9", result.insertedMarqueId)
                    homeViewModel.insertMachine(machine1)
                    homeViewModel.insertMachine(machine2)
                    homeViewModel.insertMachine(machine3)
                }

                InsertResult.Error -> {
                    Log.i("info", "onCreateView: Error")
                }
            }
        }
        homeViewModel.insertMarque(marque2) { result ->
            when (result) {
                is InsertResult.Success -> {
                    val machine1 = Machine(0, "machine 10", "34.9", result.insertedMarqueId)
                    homeViewModel.insertMachine(machine1)
                }

                InsertResult.Error -> {
                    Log.i("info", "onCreateView: Error")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
