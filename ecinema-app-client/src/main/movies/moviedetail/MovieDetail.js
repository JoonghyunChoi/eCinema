import { Component } from 'react'
import { Link } from 'react-router-dom'
import './MovieDetail.css'

class MovieDetail extends Component {

    render () {
        const { id } = this.props.match.params
        const { poster, title_long, genres, synopsis, rating, runtime } = this.props.location.state 

        return (
            <section>
                <div className="NewPost">
                    <img src = {poster}/>
                    <label>{title_long}</label>
                    <label>{genres}</label>
                    <label>{rating}</label>
                    <label>{runtime}</label>
                    <label>{synopsis}</label>
                </div>
                <div className="tolist">
                    <Link to={{
                        pathname: '/movies/mvdetail' + id + '/reserve',
                        state: { title_long: title_long }}}>
                        <button>예매하기</button>
                    </Link>
                </div>
            </section>
        );
    }
}

export default MovieDetail