import { Component } from "react"
import axios from "axios"
import ChildComment from "./ChildComment"
import './ParentComment.css'

class ParentComment extends Component {

    state = {
        childCommentContent: '',
        showList: []
    }

    childCommentHandler = (parentId) => {
        const childCommentForm = {
            postId: this.props.postId,
            content: this.state.childCommentContent,
            parentId: parentId
        }
        axios.post('/api/comments/child_comments', childCommentForm)
            .then(response => {console.log(response)})
            .catch(e => console.log(e))
    }
    
    showReply = (commentId) => {
        let showList = this.state.showList

        if (showList.includes(commentId)) {
            showList.splice(showList.indexOf(commentId), 1)
            this.setState( {showList: showList} )}
        else {
            showList.push(commentId)
            this.setState( {showList: showList} )
        }
    }

    
    render() {

        const { parentComment, childComments } = this.props
        
        const parentCommentBlock = <div className="parent-comment">
                                        <p className="pc-writer">{parentComment.writer}</p>
                                        <p className="pc-content">{parentComment.content}</p>
                                        <p className="pc-regdate">{parentComment.createdAt}</p>
                                        <p className="post-comment" onClick={ () => this.showReply(parentComment.id)}>comment</p>
                                    </div>

        const reply = <div>
                        <div className="child-reply-form">
                            <input type="text" onChange={ (event) => this.setState({ childCommentContent: event.target.value }) }></input>
                        </div>
                        <div className="child-reply-button">
                            <button onClick={ () => this.childCommentHandler(parentComment.id) }>답글</button> 
                        </div>
                      </div>


        return (
                <div>
                    {parentCommentBlock}

                    {this.state.showList.includes(parentComment.id) && reply}
    
                    {childComments.map(childComment => { return (
                        <ChildComment parentId={parentComment.id} childComment={childComment}/>)}
                    )}
                </div>
        )
    }
}

export default ParentComment