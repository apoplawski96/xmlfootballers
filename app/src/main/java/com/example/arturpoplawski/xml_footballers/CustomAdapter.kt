package com.example.arturpoplawski.xml_footballers

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.arturpoplawski.xmlparser.Footballer
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.textColor
import org.jetbrains.anko.uiThread
import java.time.temporal.TemporalAmount

class CustomAdapter (val itemsList : List<Footballer>) : RecyclerView.Adapter<CustomAdapter.ViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context).inflate(R.layout.item, parent, false)
        return ViewHolder(layoutInflater)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: Footballer = itemsList[position]

        holder?.footballerName?.text = item.firstName + " " +item.lastName
        //holder?.footballClub?.text = item.footballClub
        holder?.footballerNationality?.text = item.nationality
        holder?.footballerPreferredPosition?.text = item.preferredPosition
        holder?.teamNumber?.text = item.teamNumber
        var fl = holder?.frameLayout

        if (item.isBenched.equals("yes")){
            var tv = holder?.isBenchedTextView
            tv.text = "B7"
            tv.setTextColor("#d9d9d9".toColor())
            fl.setBackgroundColor("#cccccc".toColor())
        }
        /*if (item.preferredPosition.equals("GK")){
            fl.setBackgroundColor("#862d86".toColor())
        }*/

        loadImageInBackground(item, holder)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val footballerName = itemView.findViewById(R.id.footballerName) as TextView
        //val footballClub = itemView.findViewById(R.id.footballClub) as TextView
        val footballerNationality = itemView.findViewById(R.id.textView3) as TextView
        val footballerPreferredPosition= itemView.findViewById(R.id.textView4) as TextView
        val footballerImage = itemView.findViewById(R.id.footballerImage) as CircleImageView
        val flagImage = itemView.findViewById(R.id.flagImage) as CircleImageView
        val redCircle = itemView.findViewById(R.id.redCircle) as CircleImageView
        val liverBird = itemView.findViewById(R.id.liverBird) as ImageView
        val teamNumber = itemView.findViewById(R.id.teamNumber) as TextView
        var isBenchedTextView = itemView.findViewById(R.id.isBenchedTextView) as TextView
        var frameLayout = itemView.findViewById(R.id.frameLayout) as FrameLayout
    }

    fun refresh() {
        notifyDataSetChanged()
    }

    fun loadImageInBackground(item : Footballer,  holder : ViewHolder){
        var loadImage = Picasso.get().load(item.footballerImageUrl)
        var loadFlagImage = Picasso.get().load(item.flagImageUrl)
        var loadRedCircle = Picasso.get().load(R.mipmap.lfc_red)
        var loadLiverBird = Picasso.get().load(R.mipmap.liverbird)
        loadRedCircle.into(holder?.redCircle)
        loadImage.into(holder?.footballerImage)
        loadFlagImage.into(holder?.flagImage)
        loadLiverBird.into(holder?.liverBird)
    }

    fun String.toColor(): Int = Color.parseColor(this)

}