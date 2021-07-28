import { Component } from "react";
import { withRouter } from "react-router";
import axios from "axios";
import './Signup.css'

class Signup extends Component {
    
    state = {
        username: '',
        password: '',
        email: '',
        phoneNumber: ''
    }

    postHandler = () => {
        const post = {
            username: this.state.username,
            password: this.state.password,
            email: this.state.email,
            phoneNumber: this.state.phoneNumber
        }
        axios.post('/signup', post)
             .then(response => { 
                console.log(response);
            
                this.props.history.push('/login'); 
            })
             .catch(e => console.log(e)) 
    }

    render () {
        return (
            <section>
                <div className="NewPost">   
                    <label>아이디</label>
                    <input type="text" value={this.state.username} onChange={(event) => this.setState({username: event.target.value})} />
                    <label>비밀번호</label>
                    <input type="password" value={this.state.password} onChange={(event) => this.setState({password: event.target.value})}/>
                    <label>이메일</label>
                    <input type="text" value={this.state.email} onChange={(event) => this.setState({email: event.target.value})} />
                    <label>전화번호</label>
                    <input type="text" value={this.state.phoneNumber} onChange={(event) => this.setState({phoneNumber: event.target.value})} />
                    <button onClick={this.postHandler}>가입</button>
                </div>
            </section>
        );
    }
}

export default withRouter(Signup)