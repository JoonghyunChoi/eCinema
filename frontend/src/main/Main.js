import { Component } from "react"
import { Route, Switch } from "react-router-dom"
import Home from "./home/Home"
import Screen from "./screen/Screen"
import Review from "./review/Review"
import Login from "./account/login/Login"
import NewPost from "./review/newpost/NewPost"
import Post from "./review/post/Post"
import Signup from "./account/signup/Signup"
import MovieDetail from "./movies/moviedetail/MovieDetail"
import Movies from "./movies/Movies"
import Reservation from "./movies/reservation/Reservation"

class Main extends Component {

    render() {
        return (
            <div className="main">
                <Switch>
                    <Route path='/home' component={Home}/>
                    <Route path='/movies' exact component={Movies}/>
                    <Route path='/movies/mvdetail:id/reserve' exact component={Reservation}/>
                    <Route path='/movies/mvdetail:id' exact component={MovieDetail}/>
                    <Route path='/screen' component={Screen}/>
                    <Route path='/review' exact component={Review}/>
                    <Route path='/review/post:id' exact component={Post}/>
                    <Route path='/review/newpost' exact component={NewPost}/>
                    <Route path='/login' render={() => <Login tokenHandler={this.props.tokenHandler} />}/>
                    <Route path='/signup' component={Signup}/>
                </Switch>
            </div>
        )
    }
}

export default Main