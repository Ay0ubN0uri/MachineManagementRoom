package com.a00n.machinemanagementroom.ui.machine

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.a00n.machinemanagementroom.R
import com.a00n.machinemanagementroom.databinding.FragmentMachineBinding
import com.a00n.machinemanagementroom.ui.adapters.MachineAdapter
import com.a00n.machinemanagementroom.ui.home.HomeViewModel
import com.a00n.machinemanagementroom.utils.SwipeGesture
import com.google.android.material.snackbar.Snackbar

class MachineFragment : Fragment() {

    private lateinit var viewModel: MachineViewModel
    private lateinit var homeViewModel: HomeViewModel

    private var _binding: FragmentMachineBinding? = null
    private val binding get() = _binding!!
    private val adapter: MachineAdapter by lazy {
        MachineAdapter(
            onClickListener = { _, machine ->
                Log.i("info", "hello: $machine")
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMachineBinding.inflate(inflater, container, false)
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        viewModel = ViewModelProvider(this)[MachineViewModel::class.java]
        binding.machineRecycleView.adapter = adapter
        showShimmer()
        homeViewModel.machinesWithMarque.observe(viewLifecycleOwner) {
            Log.i("info", "onCreateView: $it")
            Handler(Looper.getMainLooper()).postDelayed({
                hideShimmer()
                adapter.submitList(it)
            }, 1000)

        }
        addSwipeDelete()
        return binding.root
    }

    private fun addSwipeDelete() {
        val swipeGesture = object : SwipeGesture(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val originalList = adapter.currentList
                val list = originalList.toMutableList()
                list.removeAt(viewHolder.adapterPosition)
                adapter.submitList(list)
                val snackbar = Snackbar.make(
                    binding.root,
                    "Marque deleted successfully.",
                    Snackbar.LENGTH_LONG
                )
                snackbar.setAction("Undo") {
                    adapter.submitList(originalList)
                }
                snackbar.addCallback(object : Snackbar.Callback() {
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        super.onDismissed(transientBottomBar, event)
                        Log.i("info", "onDismissed: $event")
                        if (event == DISMISS_EVENT_TIMEOUT || event == DISMISS_EVENT_CONSECUTIVE) {
                            val diff = (originalList.toSet() subtract list.toSet()).toList()
                            if (diff.isNotEmpty()) {
                                homeViewModel.deleteMachine(diff[0].machine)
                                Log.i("info", "deleting: ${diff[0]}")
                            }
                        }
                    }
                })
                snackbar.show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeGesture)
        itemTouchHelper.attachToRecyclerView(binding.machineRecycleView)
    }


    private fun showShimmer() = binding.machineRecycleView.showShimmer()
    private fun hideShimmer() = binding.machineRecycleView.hideShimmer()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}