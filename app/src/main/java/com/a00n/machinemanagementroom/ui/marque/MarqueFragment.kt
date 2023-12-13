package com.a00n.machinemanagementroom.ui.marque

import android.app.AlertDialog
import android.content.DialogInterface
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
import androidx.viewbinding.ViewBinding
import com.a00n.machinemanagementroom.R
import com.a00n.machinemanagementroom.data.local.entities.Marque
import com.a00n.machinemanagementroom.databinding.FragmentMarqueBinding
import com.a00n.machinemanagementroom.databinding.MarqueSaveEditBinding
import com.a00n.machinemanagementroom.ui.adapters.MarqueAdapter
import com.a00n.machinemanagementroom.ui.home.HomeViewModel
import com.a00n.machinemanagementroom.utils.SwipeGesture
import com.google.android.material.snackbar.Snackbar

class MarqueFragment : Fragment() {

    private lateinit var viewModel: MarqueViewModel
    private lateinit var homeViewModel: HomeViewModel
    private val adapter: MarqueAdapter by lazy {
        MarqueAdapter(
            onClickListener = { _, marque ->
                Log.i("info", "hello: $marque")
                showDialog(
                    layoutToShow = R.layout.marque_save_edit,
                    dialogTitle = "Update Marque",
                    positiveButtonText = "Update",
                    onCreateDialog = { dialogBinding ->
                        val popupBinding = dialogBinding as MarqueSaveEditBinding
                        popupBinding.marqueCodeEditText.setText(marque.code)
                        popupBinding.marqueLibelleEditText.setText(marque.name)
                    },
                    onPositiveButton = { dialogBinding, _, _ ->
                        val popupBinding = dialogBinding as MarqueSaveEditBinding
                        Log.i(
                            "info",
                            "onCreateView: ${popupBinding.marqueCodeEditText.text}"
                        )
                        Log.i(
                            "a00n",
                            "onCreateView: ${popupBinding.marqueCodeEditText.text}"
                        )
                        val marque = Marque(
                            marque.id,
                            popupBinding.marqueCodeEditText.text.toString(),
                            popupBinding.marqueLibelleEditText.text.toString()
                        )
                        homeViewModel.updateMarque(marque)
                    })
            }
        )
    }

    private var _binding: FragmentMarqueBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMarqueBinding.inflate(inflater, container, false)
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        viewModel = ViewModelProvider(this)[MarqueViewModel::class.java]
        binding.marqueRecycleView.adapter = adapter
        showShimmer()
        binding.fab.setOnClickListener {
            showDialog(
                layoutToShow = R.layout.marque_save_edit,
                dialogTitle = "Add Marque",
                onPositiveButton = { dialogBinding, _, _ ->
                    val popupBinding = dialogBinding as MarqueSaveEditBinding
                    val marque = Marque(
                        0,
                        popupBinding.marqueCodeEditText.text.toString(),
                        popupBinding.marqueLibelleEditText.text.toString()
                    )
                    homeViewModel.insertMarque(marque)
                })
        }
        homeViewModel.marques.observe(viewLifecycleOwner) {
            Log.i("info", "onCreateView: $it")
            Handler(Looper.getMainLooper()).postDelayed({
                hideShimmer()
                adapter.submitList(it)
            }, 1000)
        }
        addSwipeDelete()
        return binding.root
    }

    private fun showDialog(
        layoutToShow: Int,
        dialogTitle: String,
        negativeButtonText: String = "Cancel",
        positiveButtonText: String = "Add",
        onNegativeButton: ((DialogInterface, Int) -> Unit)? = null,
        onPositiveButton: (ViewBinding, DialogInterface, Int) -> Unit,
        onCreateDialog: ((ViewBinding) -> Unit)? = null
    ) {
        val popup = LayoutInflater.from(requireContext())
            .inflate(layoutToShow, null, false)
        val popupBinding = MarqueSaveEditBinding.bind(popup)
        if (onCreateDialog != null) {
            onCreateDialog(popupBinding)
        }
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(dialogTitle)
            .setView(popup)
            .setNegativeButton(negativeButtonText, onNegativeButton)
            .setPositiveButton(positiveButtonText) { arg1, arg2 ->
                onPositiveButton(popupBinding, arg1, arg2)
            }
            .create()
        dialog.show()
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
                                homeViewModel.deleteMarque(diff[0])
                                Log.i("info", "deleting: ${diff[0]}")
                            }
                        }
                    }
                })
                snackbar.show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeGesture)
        itemTouchHelper.attachToRecyclerView(binding.marqueRecycleView)
    }


    private fun showShimmer() = binding.marqueRecycleView.showShimmer()
    private fun hideShimmer() = binding.marqueRecycleView.hideShimmer()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}