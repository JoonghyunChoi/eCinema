import { Component } from "react"
import axios from "axios"
import './Logout.css'

class Logout extends Component {

    logoutHandler = () => {
        localStorage.clear()
        
        this.props.tokenHandler(localStorage.getItem("token"))
        
    }

    render() {
        return (
            <button onClick={this.logoutHandler}>로그아웃</button>
        )
    }
}

export default Logout