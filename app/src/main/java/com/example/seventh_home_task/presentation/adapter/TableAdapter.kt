package com.example.seventh_home_task.presentation.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.seventh_home_task.R
import com.example.seventh_home_task.databinding.TableItemBinding
import com.example.seventh_home_task.domain.models.Fields

class TableAdapter(
    private val listener: Listener
) :
    RecyclerView.Adapter<TableAdapter.TableHolder>() {
    private val lineTableList = ArrayList<Fields>()

    class TableHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = TableItemBinding.bind(item)
        private val itemContext = item.context
        lateinit var editText: EditText

        fun bind(fields: Fields, listener: Listener) = with(binding) {

            editText = etInputValue.findViewById(R.id.et_input_value)

            tvFieldTitle.text = fields.nameField
            etInputValue.hint = fields.inputField
            if (fields.typeField == "TEXT") {
                etInputValue.setText("")
            }
            if (fields.typeField == "NUMERIC") {
                etInputValue.inputType =
                    InputType.TYPE_CLASS_NUMBER or
                            InputType.TYPE_NUMBER_FLAG_DECIMAL or
                            InputType.TYPE_NUMBER_FLAG_SIGNED
                etInputValue.setText("")
            }
            if (fields.typeField == "LIST") {
                etInputValue.apply {
                    showSoftInputOnFocus = false
                    val listMeaning = mutableListOf<String>()
                    for (i in fields.valuesList) {
                        listMeaning.add(i.value)
                    }
                    setText(listMeaning[0])
                    onFocusChangeListener =
                        OnFocusChangeListener { v, hasFocus ->
                            if (hasFocus) {
                                val builder = AlertDialog.Builder(itemContext)
                                builder.apply {
                                    setTitle("Выбор значения:")
                                    setItems(listMeaning.toTypedArray()
                                    ) { dialog, which ->
                                        setText(listMeaning[which])
                                    }
                                    show()
                                }
                            }
                        }
                    setOnClickListener {
                        val builder = AlertDialog.Builder(itemContext)
                        builder.apply {
                            setTitle("Выбор значения:")
                            setItems(listMeaning.toTypedArray()
                            ) { dialog, which ->
                                setText(listMeaning[which])
                            }
                            show()
                        }
                    }
                }
            }
            itemView.setOnClickListener {
                listener.onClick(itemView.findViewById(R.id.et_input_value))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.table_item, parent, false)
        return TableHolder(view)
    }

    override fun onBindViewHolder(holder: TableHolder, position: Int) {
        holder.bind(lineTableList[position], listener)
    }

    override fun getItemCount(): Int {
        return lineTableList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addLineTable(fields: Fields) {
        lineTableList.add(fields)
        notifyDataSetChanged()
    }

    interface Listener{
        fun onClick(item: View)
    }
}