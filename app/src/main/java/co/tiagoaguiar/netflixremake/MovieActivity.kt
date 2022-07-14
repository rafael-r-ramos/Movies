package co.tiagoaguiar.netflixremake

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.LayerDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.tiagoaguiar.netflixremake.model.Movie
import co.tiagoaguiar.netflixremake.model.MovieDetail
import co.tiagoaguiar.netflixremake.util.DownloadImageTask
import co.tiagoaguiar.netflixremake.util.MovieTask
import java.lang.ClassCastException

class MovieActivity : AppCompatActivity() , MovieTask.Callback {

    private lateinit var txtTitle: TextView
    private lateinit var txtDesc:TextView
    private lateinit var txtCast: TextView
    private lateinit var adapter: MovieAdapter

   private  val  movies = mutableListOf<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        txtTitle= findViewById(R.id.movie_txt_title)
        txtDesc= findViewById(R.id.movie_txt_desc)
        txtCast = findViewById(R.id.movie_cast)
       val rv: RecyclerView  = findViewById(R.id.movie_rv_similar)

        val id = intent?.getIntExtra("id",0) ?: throw IllegalAccessException("ID não foi encontrado")

        val url ="https://api.tiagoaguiar.co/netflixapp/movie/$id?apiKey=e774bf3e-7afb-4704-9f3e-f7c07251c903"

        MovieTask(this).execute(url)





        adapter = MovieAdapter(movies, R.layout.movie_item_similar)
        rv.layoutManager = GridLayoutManager(this,3)
        rv.adapter = adapter


        val  toolbar: Toolbar = findViewById(R.id.movie_toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title= null


    }

    override fun onFailure(message: String) {

    }

    override fun onResult(movieDetail: MovieDetail) {

        txtTitle.text = movieDetail.movie.title
        txtDesc.text = movieDetail.movie.desc
        txtCast.text=getString(R.string.cast,movieDetail.movie.cast)

        movies.clear()
        movies.addAll(movieDetail.similar)
        adapter.notifyDataSetChanged()

        DownloadImageTask(object : DownloadImageTask.Callback{
            override fun onResult(bitmap: Bitmap) {
                val layerDrawable: LayerDrawable = ContextCompat.getDrawable(this@MovieActivity,R.drawable.shadows) as LayerDrawable
                val movieCover = BitmapDrawable(resources,bitmap)
                layerDrawable.setDrawableByLayerId(R.id.cover_drawable,movieCover)
                val coverImg: ImageView = findViewById(R.id.movie)
                coverImg.setImageDrawable(layerDrawable)
            }
        }).execute(movieDetail.movie.coverUrl)

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}