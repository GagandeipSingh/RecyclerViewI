package com.example.recyclerviewi

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewi.databinding.ActivityMainBinding
import com.example.recyclerviewi.databinding.CustomdialogBinding

class MainActivity : AppCompatActivity(), ClickInterface {
    private lateinit var binding : ActivityMainBinding
//    private lateinit var list : ArrayList<Student>
    private var dataList : ArrayList<DataEntity> = arrayListOf()
    private lateinit var adapter : CustomAdapter
    private lateinit var customBinding : CustomdialogBinding
    private lateinit var dataDatabase: DataDatabase
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "\u00A0\u00A0\u00A0Students List"
        supportActionBar?.setBackgroundDrawable(getDrawable(R.drawable.customac))
        dataDatabase = DataDatabase.getInstance(this)
        adapter = CustomAdapter(dataList,this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.recyclerView.adapter = adapter
        binding.Add.setOnClickListener {
            val customDialog = Dialog(this)
            customBinding = CustomdialogBinding.inflate(layoutInflater)
            customDialog.setContentView(customBinding.root)
            customDialog.setCancelable(false)
            customDialog.show()
            customBinding.positiveButton.setOnClickListener {
                if(customBinding.etName.text?.trim()?.isEmpty()!!){
                    customBinding.textInputLayout1.error = "Enter Name.."
                }
                else if(customBinding.etRoll.text?.trim()?.isEmpty()!!){
                    customBinding.textInputLayout2.error = "Enter Roll Number.."
                }
                else{
                    val dataEntity = DataEntity(name=customBinding.etName.text?.trim().toString(),
                        roll=customBinding.etRoll.text?.trim().toString())
                    val insertedId = dataDatabase.dataInterface().insertData(dataEntity)
                    val lastAddedEntity = dataDatabase.dataInterface().getEntity(insertedId)
                    dataList.add(lastAddedEntity)
                    adapter.notifyItemInserted(dataList.size - 1)
                    Toast.makeText(this@MainActivity,"New Item Added..", Toast.LENGTH_SHORT).show()
                    customDialog.dismiss()
                }
            }
            customBinding.negativeButton.setOnClickListener {
                customDialog.dismiss()
            }
            customBinding.etName.doOnTextChanged { _, _, _, _ ->
                customBinding.textInputLayout1.isErrorEnabled = false
            }
            customBinding.etRoll.doOnTextChanged { _, _, _, _ ->
                customBinding.textInputLayout2.isErrorEnabled = false
            }
        }
        getData()
    }

    override fun clickUpdate(pos:Int,holder: CustomAdapter.CustomViewHolder) {
        val customDialog = Dialog(this)
        customBinding = CustomdialogBinding.inflate(layoutInflater)
        customDialog.setContentView(customBinding.root)
        customDialog.setCancelable(false)
        customDialog.show()
        customBinding.title.text = getString(R.string.update)
        customBinding.positiveButton.text = getString(R.string.update)
        customBinding.etName.setText(holder.binding.name.text)
        customBinding.etRoll.setText(holder.binding.roll.text)
        customBinding.positiveButton.setOnClickListener {
            if(customBinding.etName.text?.trim()?.isEmpty()!!){
                customBinding.textInputLayout1.error = "Enter Name.."
            }
            else if(customBinding.etRoll.text?.trim()?.isEmpty()!!){
                customBinding.textInputLayout2.error = "Enter Roll Number.."
            }
            else{
                val dataEntity = DataEntity(dataList[pos].id,
                    customBinding.etName.text?.trim().toString(),
                    roll = customBinding.etRoll.text?.trim().toString())
                dataDatabase.dataInterface().updateData(dataEntity)
                dataList[pos] = dataEntity
                adapter.notifyItemChanged(pos)
                Toast.makeText(this@MainActivity,"Item Updated..", Toast.LENGTH_SHORT).show()
                customDialog.dismiss()
            }
        }
        customBinding.negativeButton.setOnClickListener {
            customDialog.dismiss()
        }
        customBinding.etName.doOnTextChanged { _, _, _, _ ->
            customBinding.textInputLayout1.isErrorEnabled = false
        }
        customBinding.etRoll.doOnTextChanged { _, _, _, _ ->
            customBinding.textInputLayout2.isErrorEnabled = false
        }
    }

    override fun clickDelete(pos:Int) {
        val alertDialog = AlertDialog.Builder(this@MainActivity)
        alertDialog.setTitle("Confirmation!")
        alertDialog.setMessage("Do you want to Delete..")
        alertDialog.setCancelable(false)
        alertDialog.setNegativeButton("No"){_,_ ->
        }
        alertDialog.setPositiveButton("Yes") { _, _ ->
            dataDatabase.dataInterface().deleteData(dataList[pos])
            dataList.removeAt(pos)
            adapter.notifyItemRemoved(pos)
            adapter.notifyItemRangeChanged(0, dataList.size)
        }
        alertDialog.show()
    }
    private fun getData() {
        dataList.clear()
        dataList.addAll(dataDatabase.dataInterface().getList())
        adapter.notifyItemRangeInserted(0,dataList.size)
    }
}