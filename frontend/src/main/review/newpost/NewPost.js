import { Component } from "react";
import axios from "axios";
import './NewPost.css'

class NewPost extends Component {
    state = {
        title: '',
        content: '',
        author: '',
        email: 'sdfg4@naver.com',
        token: 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhd3NTZXJ2ZXIiLCJleHAiOjE2MDExOTU0NTksImlhdCI6MTYwMTE5NDg1OX0.LgdsG-OWmYuYmnOkg8BMesAZTpqZFHjSOjopxPkhAmM',
    }

    postHandler = () => {
        const post = {
            title: this.state.title,
            contents: this.state.content,
            writer: this.state.author,
            email: this.state.email,        // this.props.
            token: this.state.token         // this.props.
        }
        axios.post('/frontQABoard/create', post)
            .then(response => {
            console.log(response);
            
            this.props.history.push('/qa'); 
        })
        .catch(error => console.log(error)) 
    }

    toListHandler = () => {
        this.props.history.push('/qa')
    }

    render () {
     /* let redirect = null;
        if(this.state.submited) {
            redirect = <Redirect to="/posts"/>
            } */
        return (
            <section>
                <div className="NewPost">
                    <h1>좋은 후기를 남겨주세요.</h1>
                    <label>제목</label>
                    <input type="text" value={this.state.title} onChange={(event) => this.setState({title: event.target.value})} />
                    <label>내용</label>
                    <textarea rows="10" value={this.state.content} onChange={(event) => this.setState({content: event.target.value})} />
                    <label>작성자</label>
                    <input type="text" value={this.state.author} onChange={(event) => this.setState({author: event.target.value})}/>
                    <button onClick={this.postHandler}>게시글 올리기</button>
                </div>
                <div className="tolist">
                    <button onClick={this.toListHandler}>목록</button>
                </div>
            </section>
        );
    }
}

export default NewPost