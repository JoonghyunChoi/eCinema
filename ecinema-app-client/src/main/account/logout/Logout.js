import { Component } from "react"
import './Logout.css'

class Logout extends Component {

    logoutHandler = () => {
        localStorage.clear()
        
        this.props.tokenHandler(localStorage.getItem("token"))
    }

    render() {
        return (
            <span className='logout' onClick={this.logoutHandler}>๋ก๊ทธ์์</span>
        )
    }
}

export default Logout