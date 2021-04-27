package com.wesam.numappx

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.wesam.numappx.fragments.card_bottom_sheet
import kotlinx.android.synthetic.main.row_item.view.*


//
//class Custom_Adapter(val numList: ArrayList<ITEM>, internal var communicator: Communicator) :
//    RecyclerView.Adapter<Custom_Adapter.ViewHolder>() {
//
//
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Custom_Adapter.ViewHolder {
//
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
//        return ViewHolder(view, communicator)
//    }
//
//    override fun getItemCount(): Int {
//        return numList.size
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val datax: ITEM = numList[position]
////        holder.text_name.text = datax.itemName
//
//
//    }
//
//    class ViewHolder(itemView: View, communicator: Communicator) :
//        RecyclerView.ViewHolder(itemView), View.OnClickListener {
//        override fun onClick(v: View?) {
//            TODO("Not yet implemented")
//        }
//
//        init {
//            itemView.setOnClickListener(this)
//        }
//
////        val text_name = itemView.tv_name as TextView
//
//    }
//
//}
class Custom_Adapter(
    context: Context?,
    numList: ArrayList<ITEM>,
    communication: Communicator
) :
    RecyclerView.Adapter<Custom_Adapter.MyViewHolder>() {

    class ViewHolder(appView: View) : RecyclerView.ViewHolder(appView), View.OnClickListener,
        View.OnLongClickListener {

        init {
            appView.setOnClickListener(this)
            appView.setOnLongClickListener(this)
        }
        override fun onClick(v: View?) {

            val bottomSheetDialog = CustomBottomSheetDialogFragment()
            CustomBottomSheetDialogFragment().show(
                (v?.context as FragmentActivity).supportFragmentManager, bottomSheetDialog.tag
            )
        }
        override fun onLongClick(v: View?): Boolean {
            Toast.makeText(v?.context, "OnClick", Toast.LENGTH_SHORT).show()
            return true
        }
    }

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var mList = numList
    private var mCommunicator = communication


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = inflater.inflate(R.layout.row_item, parent, false)
        return MyViewHolder(view, mCommunicator)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val current: ITEM = mList[position]

        holder.name.text = current.itemName

//        val fragmentB = CustomBottomSheetDialogFragment()
//        val bundle = Bundle()
//        bundle.putString("NAME", current.itemName)
//        bundle.putString("NUMBER", current.itemNumber)
//        fragmentB.arguments = bundle
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class MyViewHolder(
        itemView: View,
        Communication: Communicator
    ) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener,View.OnLongClickListener {
        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

         var mComminication = Communication
        override fun onClick(v: View) {

            mComminication.passDataCom(
                adapterPosition,
                mList[adapterPosition].itemName,
                mList[adapterPosition].itemNumber
            )

                val bottomSheetDialog = CustomBottomSheetDialogFragment()
                CustomBottomSheetDialogFragment().show(
                    (v.context as FragmentActivity).supportFragmentManager, bottomSheetDialog.tag
                )

            itemView.setOnClickListener(this)
        }
        val name: TextView = itemView.tv_name

        override fun onLongClick(v: View?): Boolean {
             Toast.makeText(v?.context, "OnClick", Toast.LENGTH_SHORT).show()
            return true
        }
    }

    init {
        mList = numList
        mCommunicator = communication
    }
}