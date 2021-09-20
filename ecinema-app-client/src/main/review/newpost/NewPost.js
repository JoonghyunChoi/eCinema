import { Component } from "react";
import { withRouter } from "react-router";
import axios from "axios";
import './NewPost.css'

class NewPost extends Component {
    state = {
        title: '',
        content: '',
        writer: '',
    }

    postHandler = () => {
        const post = {
            title: this.state.title,
            content: this.state.content,
            writer: this.state.writer,
        }
        axios.post('/api/posts', post)
            .then(response => {
            console.log(response);
            
            this.props.history.push('/review'); 
        })
        .catch(e => console.log(e)) 
    }

    toListHandler = () => {
        this.props.history.push('/review')
    }

    render () {

        return (
            <section>
                <div className="NewPost">
                    <h1>영화 후기를 남겨주세요</h1>
                    <label>제목</label>
                    <input type="text" value={this.state.title} onChange={(event) => this.setState({title: event.target.value})} />
                    <label>내용</label>
                    <textarea rows="10" value={this.state.content} onChange={(event) => this.setState({content: event.target.value})} />
                    <label>작성자</label>
                    <input type="text" value={this.state.writer} onChange={(event) => this.setState({writer: event.target.value})}/>
                    <button onClick={this.postHandler}>게시글 올리기</button>
                </div>
                <div className="tolist">
                    <button onClick={this.toListHandler}>목록</button>
                </div>
            </section>
        );
    }
}

export default withRouter(NewPost)