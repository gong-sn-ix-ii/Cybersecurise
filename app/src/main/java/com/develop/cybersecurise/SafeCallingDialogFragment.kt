package com.develop.cybersecurise

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class SafeCallingDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_safe_calling, null)

        val progressInf = view.findViewById<ProgressBar>(R.id.progress_information_progress_bar) ?: run {
            Log.e("SafeCallingDialogFragment", "ProgressBar not found in layout")
            return AlertDialog.Builder(requireContext()).create()
        }

        progressInf.visibility = View.GONE

        return AlertDialog.Builder(requireContext())
            .setView(view)
            .create()
    }
}
