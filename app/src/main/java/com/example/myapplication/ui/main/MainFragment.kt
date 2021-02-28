package com.example.myapplication.ui.main

import android.app.AlertDialog
import android.app.Dialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.R
import org.json.JSONArray
import org.json.JSONObject

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var mrecyclerview: RecyclerView
    private lateinit var button: Button
    private lateinit var editText: EditText
    private lateinit var jsonArray: JSONArray
    private lateinit var mlayout: LinearLayoutManager
    private lateinit var itemadaptor: adaptor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val root: View = inflater.inflate(R.layout.main_fragment, container, false)
        mrecyclerview = root.findViewById(R.id.recyclerview)
        button = root.findViewById(R.id.btn1)
        editText = root.findViewById(R.id.edittext)
        mlayout = LinearLayoutManager(context)
        mrecyclerview.layoutManager = mlayout

        checkInternetPermission()
        checkStorageReadPermission()
        checkStorageWtitePermission()

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        button.setOnClickListener {
            viewModel.getbaseurl().apply {
                value =
                    "https://pixabay.com/api/?key=5303976-fd6581ad4ac165d1b75cc15b3&q=" + editText.text + "&image_type=photo&pretty=true"
            }
            val queue: RequestQueue = Volley.newRequestQueue(context)
            val jsonobj =
                JsonObjectRequest(Request.Method.GET, viewModel.getbaseurl().value, null,
                    { response ->
                        jsonArray = response.getJSONArray("hits")
                        viewModel.initweb()
                        for (i in 0..jsonArray.length() - 1) {
                            val hits: JSONObject = jsonArray.getJSONObject(i)
                            viewModel.getwebinfo().set(
                                i,
                                "User : " + hits.getString("user") + "\n" + "Likes : " + hits.getString(
                                    "likes"
                                )
                            )
                            viewModel.getweburl().set(i, hits.getString("webformatURL"))

                        }

                        itemadaptor = adaptor()
                        for (i in 0..19) {
                            itemadaptor.init(
                                context!!,
                                viewModel.getwebinfo().clone(),
                                viewModel.getweburl().clone()
                            )
                        }
                        mrecyclerview.adapter = itemadaptor

                    },
                    {
                        Log.d("Network Error : ", "Failed")
                    })
            queue.add(jsonobj)
        }
    }
    private fun checkInternetPermission() {
        if (ContextCompat.checkSelfPermission(context!!,android.Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
            Log.d("Permission", "checkInternetPermission: Granted")
        }
        else if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!,android.Manifest.permission.INTERNET)) {
            val dialog: Dialog = AlertDialog.Builder(context)
                .setTitle("Permission Required")
                .setMessage("You Cant Use Apps Full Functionality Until You Allow The Required Permission")
                .setPositiveButton("Ok",
                    { dialog, which -> ActivityCompat.requestPermissions(activity!!,arrayOf(android.Manifest.permission.INTERNET),1) })
                .create()
                dialog.show()

        }
        else {
            ActivityCompat.requestPermissions(activity!!,arrayOf(android.Manifest.permission.INTERNET),1)        }
    }
    private fun checkStorageReadPermission() {
        if (ContextCompat.checkSelfPermission(context!!,android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.d("Permission", "checkStorageReadPermission: Granted")
        }
        else if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!,android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            val dialog: Dialog = AlertDialog.Builder(context)
                .setTitle("Permission Required")
                .setMessage("You Cant Use Apps Full Functionality Until You Allow The Required Permission")
                .setPositiveButton("Ok",
                    { dialog, which -> ActivityCompat.requestPermissions(activity!!,arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1) })
                .create()
                dialog.show()

        }
        else {
            ActivityCompat.requestPermissions(activity!!,arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1)        }
    }
    private fun checkStorageWtitePermission() {
        if (ContextCompat.checkSelfPermission(context!!,android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.d("Permission", "checkStorageWritePermission: Granted")
        }
        else if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            val dialog: Dialog = AlertDialog.Builder(context!!)
                .setTitle("Permission Required")
                .setMessage("You Cant Use Apps Full Functionality Until You Allow The Required Permission")
                .setPositiveButton("Ok",
                    { dialog, which -> ActivityCompat.requestPermissions(activity!!,arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),1) })
                .create()
                dialog.show()

        }
        else {
            ActivityCompat.requestPermissions(activity!!,arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),1)        }
    }
}


