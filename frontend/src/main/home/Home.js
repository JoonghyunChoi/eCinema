import { Component } from 'react'
import Movie from '../movie/Movie'

class Home extends Component {

    state = {}

    componentDidMount() {
        this.getMovies()
    }

    callApi = () => {
        return fetch("https://yts.mx/api/v2/list_movies.json?limit=4")
            .then(response => response.json())
            .then(json => json.data.movies)
            .catch(e => console.log(e))
    }

    getMovies = async () => {
        const movies = await this.callApi()
        this.setState({ movies })
    }

    renderMovies = () => {
        const movies = this.state.movies.map(movie => {
            return (    
            <Movie 
                title = {movie.title_english}
                poster = {movie.large_cover_image}
                /> 
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