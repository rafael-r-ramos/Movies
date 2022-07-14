package co.tiagoaguiar.netflixremake

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import co.tiagoaguiar.netflixremake.model.Movie
import co.tiagoaguiar.netflixremake.util.DownloadImageTask
import com.squareup.picasso.Picasso

class MovieAdapter(
    private val movies: List<Movie>,
    @LayoutRes private val layoutId:Int,
    private val onItemClickListener: ((Int)->Unit )? = null
    ) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent , false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)


    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class MovieViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun bind(movie: Movie) {
            val img : ImageView = itemView.findViewById(R.id.movie_img)
            DownloadImageTask(object : DownloadImageTask.Callback {
                override fun onResult(bitmap: Bitmap) {
                    img.setImageBitmap(bitmap)
                }
            }).execute(movie.coverUrl)
            img.setOnClickListener {
                onItemClickListener?.invoke(movie.id)
            }

        }
    }

}