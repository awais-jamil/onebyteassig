package com.example.onebyteassign.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.onebyteassign.R
import com.example.onebyteassign.models.Categories
import com.example.onebyteassign.models.Movie
import com.squareup.picasso.Picasso

class HorizontalView : LinearLayout {

    lateinit var textView: TextView
    lateinit var recyclerView: RecyclerView
    lateinit var categoriesData: ArrayList<Categories>
    lateinit var moviesData: ArrayList<Movie>

    constructor(context: Context) : super(context){
        init()
    }

    constructor(context: Context, attrs: AttributeSet):    super(context, attrs){
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {

        inflate(context, R.layout.horizontal_view, this)

        textView = findViewById(R.id.header)
        recyclerView = findViewById(R.id.recyler_View)

    }

    fun loadMoviesData(data: ArrayList<Movie>){
        moviesData = data
        var linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL ,false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = MovieRecyclerAdapter()
    }

    fun loadCategoriesData(data: ArrayList<Categories>){

        categoriesData = data

        var linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL ,false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = CateRecyclerAdapter()
    }

    inner class MovieRecyclerAdapter : RecyclerView.Adapter<MovieRecyclerAdapter.ViewHolder>()  {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            val inflatedView = LayoutInflater.from(context).inflate(R.layout.movie_layout, parent, false)
            return ViewHolder(inflatedView)

        }

        override fun getItemCount(): Int {

            return moviesData.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bindView(position)
        }

        inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

            private var view: View = v
            lateinit var imageView: ImageView
            lateinit var nameTextView: TextView
            lateinit var ratingTextView: TextView

            fun bindView(position: Int) {

                imageView = view.findViewById(R.id.image)
                ratingTextView = view.findViewById(R.id.rating)
                nameTextView = view.findViewById(R.id.title)

                Picasso.get()
                    .load(moviesData.get(position).image)
                    .placeholder(R.drawable.movie_placeholder)
                    .fit()
                    .into(imageView)
                nameTextView.text = moviesData.get(position).name
                ratingTextView.text = moviesData.get(position).rating.toString()

                imageView.tag = position
                imageView.setOnClickListener(this)
            }

            override fun onClick(v: View) {

            }

        }
    }

    inner class CateRecyclerAdapter : RecyclerView.Adapter<CateRecyclerAdapter.ViewHolder>()  {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            val inflatedView = LayoutInflater.from(context).inflate(R.layout.categories_layout, parent, false)
            return ViewHolder(inflatedView)

        }

        override fun getItemCount(): Int {
            return categoriesData.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bindView(position)
        }

        inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

            private var view: View = v
            lateinit var nameTextView: TextView
            fun bindView(position: Int) {

                nameTextView = view.findViewById(R.id.name)
                nameTextView.text = categoriesData.get(position).title
                nameTextView.tag = position
                nameTextView.setOnClickListener(this)
            }

            override fun onClick(v: View) {

            }

        }
    }

}