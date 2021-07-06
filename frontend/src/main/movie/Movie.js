import { Component } from "react";

function Movie({ title, poster }) {
    return (
        <div className="movie">
            <div>
                <MoviePoster poster = {poster} alt = {title}/>
            </div>
            <div>
                <h1>{title}</h1>
            </div>
        </div>
    )
}

function MoviePoster({ poster, alt }) {
    return (
        <img src = {poster} alt= {alt} title = {alt}/>
    )
}

export default Movie