package com.wesam.numappx.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.wesam.numappx.R
import kotlinx.android.synthetic.main.fragment_card_bottom_sheet.view.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class card_bottom_sheet : BottomSheetDialogFragment() {

    companion object {

        const val TAG = "CustomBottomSheetDialogFragment"

    }

    var name: String? = null
    var number: String? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val text_name = view?.textView4 as TextView
        val text_number = view?.textView6 as TextView
        text_name.text = name
        text_number.text = number

        val view: View = (context as FragmentActivity).layoutInflater
            .inflate(R.layout.fragment_card_bottom_sheet, null)
        val dialog = BottomSheetDialog(context as FragmentActivity)
        dialog.setContentView(view)
        dialog.show()


        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        }

    }






