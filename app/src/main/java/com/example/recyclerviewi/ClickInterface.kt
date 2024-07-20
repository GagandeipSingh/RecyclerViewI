package com.example.recyclerviewi

interface ClickInterface {
    fun clickUpdate(pos:Int, holder:CustomAdapter.CustomViewHolder)
    fun clickDelete(pos:Int)
}