import { Component } from "react"
import { withRouter } from "react-router"
import jwt_decode from 'jwt-decode'
import axios from "axios"
import './Login.css'

class Login extends Component {
    
    state = {
        username: '',
        password: '',
    }

    postHandler = () => {
        const post = {
            username: this.state.username,
            password: this.state.password,
        }
        axios.post('/login', post)
            .then(response => {console.log(response)

            const token = response.data
            const decoded = jwt_decode(token)
            const username = decoded.username
            const userId = decoded.userId

            localStorage.setItem("token", token)
            localStorage.setItem("username", username)
            localStorage.setItem("userId", userId)

            this.props.history.push('/home')
            this.props.tokenHandler(token)
        })
        .catch(e => console.log(e))
    }

    toListHandler = () => {     // navlink로 변경
        this.props.history.push('/signup')
    }

    render () {
        return (
            <section>
                <div className="NewPost">
                    <label>아이디</label>
                    <input type="text" value={this.state.username} onChange={(event) => this.setState({username: event.target.value})} />
                    <label>비밀번호</label>
                    <input type="password" value={this.state.password} onChange={(event) => this.setState({password: event.target.value})}/>
                    <button onClick={this.postHandler}>로그인</button>
                </div>
                <div className="tolist">
                    <button onClick={this.toListHandler}>회원가입</button>
                </div>
            </section>
        );
    }
}

export default withRouter(Login)