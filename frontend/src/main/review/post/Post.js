import { Component } from 'react'
import axios from 'axios';
import ParentComment from './ParentComment';
import './Post.css'

class Post extends Component {

    state = {
        postId: this.props.match.params.id,
        post: {},
        parentCommentContent: '',
        parentComments: [],
        childComments: [],
    }

    componentDidMount() {
        this.getComments()
    }

    getComments = () => {
        const postId = this.state.postId

        if (postId) {
            axios.get('/api/comments/' + postId)
                .then(response => { console.log(response)
                    const comments = response.data.content
                    this.setState( {parentComments: comments[0], childComments: comments[1]} )
                })
                .catch(e => console.log(e))
            }
    }

    parentCommentHandler = () => {
        const parentCommentForm = {
            postId: this.props.match.params.id,
            content: this.state.parentCommentContent,
            parentId: 0
         }
        axios.post('/api/comments/parent_comment', parentCommentForm)
            .then(response => {console.log(response)
                this.getComments()
            })
            .catch(e => console.log(e))
        
    }


    render() {

        const { title, content, writer, createdAt } = this.props.location.state
        const { postId, parentComments, childComments } = this.state

        const postBlcok = <div>
                            <div className="post">
                                <p className="writer">{writer}</p>
                                <p className="dates">{createdAt}</p>
                                <h1 className="title">{title}</h1>
                                <p className="contents">{content}</p>
                            </div>
                            <div className="parent-reply-form">
                                <input type="text" onChange={(event) => this.setState({ parentCommentContent: event.target.value })}></input>
                            </div>
                            <div className="parent-reply-button">
                                <button onClick={this.parentCommentHandler}>댓글</button>
                            </div>
                        </div>


        return (
            <div>
                {postBlcok}
                
                {parentComments.map( parentComment => { return (
                    <ParentComment postId={postId} parentComment={parentComment} childComments={childComments}
                        getComments={this.getComments}/>)}
                )}
            </div>
        )           
    }
}

export default Post