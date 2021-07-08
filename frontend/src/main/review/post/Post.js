import { Component } from 'react'
import axios from 'axios';
import './Post.css'

class Post extends Component {

    state = {
        post: {title: '좋은 영화', content: '즐거운 영화', writer: '최중현', createdAt: '2021/7/6 17:00'},
        parentComment: '',
        childComment: '',
        comments: [
                {postId: 1, parentId: null, id: 1, writer: 'person1', content: '1', createdAt: '00/00 00:00'},
                {postId: 1, parentId: 1, id: 3, writer: 'person2', content: '3', createdAt: '00/00 00:00'},
                {postId: 1, parentId: 1, id: 4, writer: 'person3', content: '4', createdAt: '00/00 00:00'},
                {postId: 1, parentId: null, id: 2, writer: 'person4', content: '2', createdAt: '00/00 00:00'}
                ],
        replyForms: []
    }

    componentDidMount() {
        if (this.props.match.params.id) {
            axios.get('api/post') // + this.props.match.params.id
                .then(response => {this.setState( {post: response.data} )})
                .catch(error => console.log(error))

            axios.get('api/comments') //  + this.props.match.params.id
                .then(response => {this.setState( {comments: response.data} )})
                .catch(error => console.log(error))
            }
    }

    parentCommentHandler = () => {
        const parentComment = { 
            messageId: this.props.match.params.id,
            content: this.state.parentComment,
            parentId: null
         }
        axios.post('api/comment', parentComment)
            .then(response => {console.log(response)})
            .catch(error => console.log(error))
    }

    childCommentHandler = (parentId) => {
        const childComment = {
            messageId: this.props.match.params.id,
            content: this.state.childComment,
            parentId: parentId
        }
        axios.post('api/comment', childComment)
            .then(response => {console.log(response)})
            .catch(error => console.log(error))
    }
    
    showReplyForm = (commentId) => {
        let replyForms = this.state.replyForms

        if (replyForms.includes(commentId)) {
            replyForms.splice(replyForms.indexOf(commentId), 1)
            this.setState( {replyForms: replyForms} )}
            
        else {
            replyForms.push(commentId)
            this.setState( {replyForms: replyForms} )
        }
    }

    render() {      
        return (
            <div className="message-container">
                <div className="board">
                </div>
                <div className="message">
                    <p className="writer">{this.state.post.writer}</p>
                    <p className="dates">{this.state.post.createdAt}</p>
                    <h1 className="title">{this.state.post.title}</h1>
                    <p className="contents">{this.state.post.content}</p>
                </div>
                <div className="parent-reply-form">
                    <input type="text" onChange={(event) => this.setState({parentComment: event.target.value})}></input>
                </div>
                <div className="parent-reply-button">
                    <button onClick={this.parentCommentHandler}>댓글</button>
                </div>

                {this.state.comments.map(comment => {
                if (comment.parentId === null) {
                        return (
                            <div>
                                <div className="parent-comment">
                                    <p className="pc-writer">{comment.writer}</p>
                                    <p className="pc-content">{comment.content}</p>
                                    <p className="pc-regdate">{comment.createdAt}</p>
                                    <p className="post-comment" onClick={ () => this.showReplyForm(comment.id)}>comment</p>
                                </div>
                                <div className={this.state.replyForms.includes(comment.id) ? "reply-show" : "reply-hide"}>
                                        <div className="child-reply-form">
                                            <input type="text" onChange={ (event) => this.setState({childComment: event.target.value}) }></input>
                                        </div>
                                        <div className="child-reply-button">
                                            <button onClick={ () => this.childCommentHandler(comment.id) }>답글</button> 
                                        </div>
                                </div>
                            </div>
                        )}

                else { 
                    return (
                        <div className="child-comment" key={comment.id}>
                            <p className="cc-writer">{comment.writer}</p>
                            <p className="cc-content">{comment.content}</p>
                            <p className="cc-regdate">{comment.createdAt}</p>
                        </div>
                    )}
                })}
            </div>
        )
    }
}

export default Post