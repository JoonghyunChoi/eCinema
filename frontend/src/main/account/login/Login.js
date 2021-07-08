import { Component } from "react";
import axios from "axios";
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
        axios.post('/api/login', post)
            .then(response => {
            console.log(response);
            
            this.props.history.push('/home'); 
        })
        .catch(error => console.log(error)) 
    }

    toListHandler = () => {
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

export default Login