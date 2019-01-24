package com.example.arturpoplawski.xml_footballers

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import com.example.arturpoplawski.xmlparser.Footballer
import com.example.arturpoplawski.xmlparser.XmlParser
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.recycler_view.*

class MainActivity : AppCompatActivity() {

    lateinit var adapter : CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Open the xml file
        var inputStream = assets.open("data.xml")
        //Fill the list with data parsed from xml file
        var ballersList = XmlParser().parse(inputStream)

        //RecyclerView stuff
        recycler_view_id.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        adapter = CustomAdapter(ballersList)
        recycler_view_id.adapter = adapter
        adapter.refresh()

    }

}
