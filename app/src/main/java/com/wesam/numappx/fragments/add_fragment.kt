package com.wesam.numappx.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.wesam.numappx.R
import com.wesam.numappx.ITEM
import kotlinx.android.synthetic.main.add_fragment.*
import kotlinx.android.synthetic.main.add_fragment.view.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class add_fragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    var database = Firebase.database
    val myRef: DatabaseReference = database.reference
    val countriesArray: ArrayList<String> = ArrayList()
    val cityArray: ArrayList<String> = ArrayList()
    val districtArray: ArrayList<String> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.add_fragment, container, false)

        val spinnerCo = v.findViewById(R.id.spinCountry) as Spinner
        val spinnerD = v.findViewById(R.id.spinDist) as Spinner
        val spinnerCi = v.findViewById(R.id.spinCity) as Spinner

        myRef.child("Country").addValueEventListener(
            object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        requireContext(),
                        "title or note is EMPTY!!",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }

                override fun onDataChange(snapshot: DataSnapshot) {

                    countriesArray.clear()
                    for (item in snapshot.children) {
                        val country = item.value.toString()
                        countriesArray.add(country)
                        println(country)
                    }
                    val adapter = ArrayAdapter<String>(
                        requireActivity(),
                        android.R.layout.simple_spinner_item,
                        countriesArray
                    )
                    spinnerCo.adapter = adapter

                }
            }

        )
        spinnerCo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {

                if (position == 0) {
                    myRef.child("Cities").addValueEventListener(
                        object : ValueEventListener {
                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }

                            override fun onDataChange(snapshot: DataSnapshot) {

                                cityArray.clear()
                                for (item in snapshot.children) {
                                    val city = item.value.toString()
                                    cityArray.add(city)
                                    println(city)
                                }
                                val spinner = v.findViewById(R.id.spinCity) as Spinner
                                val adapter = ArrayAdapter<String>(
                                    requireContext(),
                                    android.R.layout.simple_spinner_item,
                                    cityArray
                                )
                                spinnerCi.adapter = adapter
                            }

                        }
                    )

                    print("onItemSelected position = $position id = $id")
                } else {
                    districtArray.clear()
                    spinnerD.adapter = null
                    cityArray.clear()
                    spinnerCi.adapter = null
                }
            }
        }

        spinnerCi.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                if (position == 0) {
                    myRef.child("DistrictMakkah").addValueEventListener(
                        object : ValueEventListener {
                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }

                            override fun onDataChange(snapshot: DataSnapshot) {
                                districtArray.clear()
                                for (item in snapshot.children) {
                                    val district = item.value.toString()
                                    districtArray.add(district)
                                    println(district)
                                }

                                val adapter = ArrayAdapter<String>(
                                    requireContext(),
                                    android.R.layout.simple_spinner_item,
                                    districtArray
                                )
                                spinnerD.adapter = adapter
                            }

                        }
                    )

                    print("onItemSelected position = $position id = $id")
                } else {
                    districtArray.clear()
                    spinnerD.adapter = null
                }
            }
        }





        v.btnAddItem.setOnClickListener {
            val userNumber = "0550656666"
            val idRadio = radioGroup.checkedRadioButtonId
            val id = myRef.push().key.toString()
            val country = spinCountry.selectedItem.toString()
            val city = spinCity.selectedItem.toString()
            val district = spinDist.selectedItem.toString()
            val itemNa = et_NAME.text.toString()
            val itemNu = et_NUMBER.text.toString()
            var status: String = "null"


            if (radioPrivate.isChecked) {
                status = "0_private"
            } else if (radioPublic.isChecked) {
                status = "1_public"
            }

            if (itemNa.isNotEmpty() && itemNu.isNotEmpty() && (radioPrivate.isChecked || radioPublic.isChecked)) {

                val mydata = ITEM(id, userNumber, country, city, district, itemNa, itemNu, status)
                myRef.child("Items").child(id).setValue(mydata)
                Toast.makeText(requireActivity(), "New Item was added", Toast.LENGTH_LONG).show()

            } else {

                Toast.makeText(requireActivity(), "Empty spaces!!", Toast.LENGTH_LONG).show()

            }
        }

        return v
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            add_fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
