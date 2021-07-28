import { Component } from "react"
import './ChildComment.css'

class ChildComment extends Component {


    render() {
        
        const { parentId, childComment } = this.props

        const childCommentBlock = <div className="child-comment">
                        <p className="cc-writer">{childComment.writer}</p>
                        <p className="cc-content">{childComment.content}</p>
                        <p className="cc-regdate">{childComment.createdAt}</p>
                     </div>
                
        return (
            (parentId === childComment.parentId) && childCommentBlock
        )
    }
}

export default ChildComment