package co.tiagoaguiar.netflixremake

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import co.tiagoaguiar.netflixremake.model.Movie
import com.squareup.picasso.Picasso

class MovieAdapter(
    private val movies: List<Movie>,
    @LayoutRes private val layoutId:Int
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
            Picasso.get().load(movie.coverUrl).into(img)

        }
    }

}