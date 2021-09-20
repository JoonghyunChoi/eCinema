import { Component } from 'react'
import axios from 'axios'
import Movie from '../movies/movie/Movie'

class Home extends Component {

    state = { movies: [] }

    componentDidMount() {
        axios.get("https://yts.mx/api/v2/list_movies.json?limit=4&sort_by=like_count")
            .then(response => {console.log(response)
                this.setState({ movies: response.data.data.movies })
            })
            .catch(e => console.log(e))
    }


    render() {
        const movies = this.state.movies.map(movie => {
            return (    
                <Movie 
                    id = {movie.id}
                    poster = {movie.medium_cover_image}
                    rating = {movie.rating}
                    title_long = {movie.title_long}
                    genres = {movie.genres}
                    runtime = {movie.runtime}
                    synopsis = {movie.synopsis} /> 
                )
            })  

        return (
            <div>
                {movies}
            </div>
        )
    }
}

export default Home