import { Component } from 'react'
import Movie from '../movies/movie/Movie'

class Home extends Component {

    state = {}

    componentDidMount() {
        this.getMovies() 
    }

    getMovies = async () => {
        const movies = await this.callApi()
        this.setState({ movies })
    }

    callApi = () => {
        return fetch("https://yts.mx/api/v2/list_movies.json?limit=4&sort_by=like_count")
            .then(response => response.json())
            .then(json => json.data.movies)
            .catch(e => console.log(e))
    }

    renderMovies = () => {
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
        return movies
    }

    render() {
        const { movies } = this.state
        return (
            <div>
                {movies ? this.renderMovies() : ""}
            </div>
        )
    }
}

export default Home