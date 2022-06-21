package com.example.seventh_home_task.presentation

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.seventh_home_task.R
import com.example.seventh_home_task.data.network.model.MetaViewModel
import com.example.seventh_home_task.databinding.FragmentBlankFormBinding
import com.example.seventh_home_task.domain.models.Form
import com.example.seventh_home_task.presentation.adapter.TableAdapter


class FormBlankFragment : Fragment(), TableAdapter.Listener {
    private lateinit var binding: FragmentBlankFormBinding
    private val adapter = TableAdapter(this)
    private val viewModel: MetaViewModel by viewModels()
    lateinit var editText: EditText

    var listInput = mutableListOf<String>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentBlankFormBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val editTextNew: EditText = view.findViewById(R.id.et_input_value)
        Log.d("TAG87", "${listInput.joinToString()}")
        val myTestMap = mutableMapOf<String, String>()
        binding.apply {
            rvTable.layoutManager = LinearLayoutManager(activity)
            rvTable.adapter = adapter
        }
        viewModel.listField.observe(this) {
            for (i in it) {
                adapter.addLineTable(i)
            }
            binding.apply {
                tbTitle.visibility = View.VISIBLE
                pbTable.visibility = View.INVISIBLE
                bSendingValues.visibility = View.VISIBLE
            }
//            var count = 0
//            for (i in it) {
//                myTestMap[i.inputField] = listInput[count]
//
//                Log.d("TAG7", "${listInput[count]}")
//                count++
//            }
            Log.d("TAG88", "${listInput.joinToString()}")
//            myTestMap.put(it[0].inputField, listInput[0])
//            myTestMap.put(it[0].inputField, onClick(editTextNew))
//            myTestMap.put(it[0].inputField, onClick(editTextNew))
            Log.d("TAG9", "${listInput[0]}")
        }
        val formSendList = Form(myTestMap)
//        viewModel.sendForm.value = Form(mapOf("text" to "кря"))
        viewModel.sendForm.value = formSendList
        Log.d("TAG4","${viewModel.sendForm.value}")
//        sendData()
        binding.bSendingValues.setOnClickListener {
            binding.pbTable.visibility = View.VISIBLE
            sendData()
            createResultDialog()
            binding.pbTable.visibility = View.INVISIBLE
        }

    }

    private fun sendData() {
        viewModel.sendClevertecMeta()
    }

    private fun createResultDialog() {
        val builder = AlertDialog.Builder(context)
        Log.d("TAG3", "${viewModel.sendMeta.value}")
        builder.apply {
            setTitle(R.string.result_sending_values)
            setMessage(viewModel.sendMeta.value)
            setNeutralButton(R.string.ok) { _, _ ->
            }
            show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FormBlankFragment()
    }

    override fun onClick(item: View) {
//        editText = item.findViewById(R.id.et_input_value)
        val et = item as EditText
        val myText = item.getText().toString()
        listInput.add(myText)
    }
}