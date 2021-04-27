package com.wesam.numappx

import android.app.Dialog
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_card_bottom_sheet.*
import kotlinx.android.synthetic.main.fragment_card_bottom_sheet.view.*

class CustomBottomSheetDialogFragment : BottomSheetDialogFragment(), Communicator {
    var root: View? = null

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

        val rootView = inflater.inflate(R.layout.fragment_card_bottom_sheet, container, false)

//        val bundle = Bundle()
//        val text_name = view?.textView4 as TextView
//        val text_number = view?.textView6 as TextView
//        text_name.text = bundle.getString("NAME")
//        text_number.text = bundle.getString("NUMBER")

        root = rootView
        return rootView
    }

    override fun onStart() {
        super.onStart()

        val bottomSheet: View? = dialog?.findViewById(R.id.design_bottom_sheet)
        BottomSheetBehavior.from(bottomSheet!!).peekHeight = 200000000

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (view?.parent as View).setBackgroundColor(Color.TRANSPARENT)

        val resources = resources

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            assert(view != null)
            val parent = view?.parent as View
            val layoutParams = parent.layoutParams as CoordinatorLayout.LayoutParams
            layoutParams.setMargins(
                resources.getDimensionPixelSize(R.dimen.screen_margin), // LEFT 16dp
                0,
                resources.getDimensionPixelSize(R.dimen.screen_margin), // RIGHT 16dp
                0
            )
            parent.layoutParams = layoutParams
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.setOnShowListener {
            val dialog = it as BottomSheetDialog
            val bottomSheet =
                dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

            BottomSheetBehavior.from(bottomSheet!!).state = BottomSheetBehavior.STATE_EXPANDED

        }
        return bottomSheetDialog
    }

    override fun passDataCom(position: Int, name: String?, number: String?) {
        val bundle = Bundle()
        val text_name = view?.textView4 as TextView
        val text_number = view?.textView6 as TextView
        text_name.text = bundle.getString("NAME")
        text_number.text = bundle.getString("NUMBER")
        text_name.text = name
        text_number.text = number

    }
}