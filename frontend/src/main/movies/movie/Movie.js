import './Movie.css'
import { Link } from 'react-router-dom'

function Movie({ id, poster, title_long, genres, synopsis, rating, runtime }) {
    
    return (
        <div className="movie">
            <MoviePoster
                id = {id} poster = {poster} title_long = {title_long} rating = {rating}
                genres = {genres} synopsis = {synopsis} runtime = {runtime}/>
        </div>
    )
}

function MoviePoster({ id, poster, title_long, genres, synopsis, rating, runtime }) {

    return (
        <div>
            <div className="movie-poster">
                <Link  to = {
                    {pathname: '/movies/mvdetail' + id,
                    state: {poster: poster,
                            title_long: title_long,
                            genres: genres,
                            synopsis: synopsis,
                            rating: rating,
                            runtime: runtime}
                    }}>
                    <img src = {poster}/>   
                </Link>
            </div>
            <div>
                <p className="rating">평점 {rating}</p>
            </div>
        </div>
    )
}

export default Movie