package com.wesam.numappx.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.wesam.numappx.*
import kotlinx.android.synthetic.main.home_fragments.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class home_fragments : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    var mRecyclerView: RecyclerView? = null
    var mRecyclerAdapter: Custom_Adapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

        }
    }

    val database = Firebase.database
    var myRef = database.getReference("Items")
    var dataList = ArrayList<ITEM>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.home_fragments, container, false)



        myRef.addValueEventListener(
            object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {

                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    dataList.clear()
//                    println(19999)
//                    println(snapshot.children)
                    for (n in snapshot.children) {
                        val item = n.getValue(ITEM::class.java)
                        dataList.add(0, item!!)
                    }
                    println(dataList)
                    my_recycler?.adapter?.notifyDataSetChanged()
                }


            })

        // Inflate the layout for this fragment
        return v
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

////////////////////////////////////////////////////////////
        val myrecycler = view.findViewById<RecyclerView>(R.id.my_recycler)
        myrecycler.layoutManager =
            LinearLayoutManager(this.activity, RecyclerView.VERTICAL, false)
        myrecycler.adapter = Custom_Adapter(requireActivity(), dataList, communication)

////////////////////////////////////////////////////////////
//        mRecyclerView = view.findViewById<View>(R.id.my_recycler) as RecyclerView
//        mRecyclerAdapter = Custom_Adapter(activity, dataList, communication)
//        mRecyclerView!!.adapter = mRecyclerAdapter
//        mRecyclerView!!.layoutManager = LinearLayoutManager(activity)
   }
   var communication: Communicator = object : Communicator {

        override fun passDataCom(position: Int, name: String?, number: String?) {
            val fragmentB = CustomBottomSheetDialogFragment()
            val bundle = Bundle()
            bundle.putString("NAME", name)
            bundle.putString("NUMBER", number)
            fragmentB.arguments = bundle
            val manager: FragmentManager? = fragmentManager
            val transaction: FragmentTransaction? = manager?.beginTransaction()
            transaction?.replace(R.id.fragment_container_view_tag, fragmentB)?.commit()
         }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            home_fragments().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

