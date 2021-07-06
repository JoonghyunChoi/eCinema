import { Component } from "react"
import { Route, Switch } from "react-router-dom"

import Home from "./home/Home"
import Screen from "./screen/Screen"
import Reserve from "./reserve/Reserve"
import Review from "./review/Review"
import Login from "./login/Login"

class Main extends Component {

    render() {
        return (
            <div className="main">
                <Switch>
                    <Route path='/home' component={Home}/>
                    <Route path='/reserve' component={Reserve}/>
                    <Route path='/screen' component={Screen}/>
                    <Route path='/review' component={Review}/>
                    <Route path='/login' component={Login}/>
                </Switch>
                
            </div>
        )
    }
}

export default Main