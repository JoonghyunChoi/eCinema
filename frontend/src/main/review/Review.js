import { Component } from "react";
import { NavLink, Link } from 'react-router-dom'
import axios from 'axios'
import './Review.css'

class Review extends Component {

    state = {
        posts: [],
        pageIndexes: [0],
        start: 0,
        end : 5
    }

    componentDidMount() {
        axios.get('/api/posts?page=0')
            .then(response => {
                console.log(response)
                this.setState({ posts: response.data._embedded.content,
                    pageIndexes:  response.data.pageIndexes 
                })
            })
            .catch(e => console.log(e)) 
    }


    currentPageHandler = (pageIndex) => {
        axios.get('/api/posts?page=' + pageIndex)
        .then(response => { 
            this.setState( {posts: response.data._embedded.content} )
        })
        .catch(e => console.log(e))  
    }

    prevPagesHandler = () => {
        if (this.state.start > 0) {
            const start = this.state.start - 5
            const end = this.state.end - 5

            this.setState( {start: start, end: end} )
        }
    }

    nextPagesHandler = () => {
        if (this.state.pageIndexes.length > this.state.end) {
            const start = this.state.start + 5
            const end = this.state.end + 5

            this.setState( {start: start, end: end} )
        }
    }


    render() {
        return (
            <div className="QA-container">
                <section className="QA-section">
                    <table>
                        <tr>
                            <th className="head-col1" scope="col">번호</th>
                            <th className="head-col2" scope="col">제목</th>
                            <th className="head-col3" scope="col">글쓴이</th>
                            <th className="head-col4" scope="col">작성일</th>
                            <th className="head-col5" scope="col"></th>
                        </tr>

                        {this.state.posts.map(post => 
                        <tr>  
                            <td className="body-data1">{post.id}</td>
                            <td className="body-data2">
                                <Link to={
                                    {pathname: "/review/post" + post.id,
                                     state: {title: post.title,
                                             content: post.content,
                                             writer: post.writer,
                                             createdAt: post.createdAt}
                                }}
                                key={post.id}>{post.title}</Link>
                            </td>
                            <td className="body-data3">{post.writer}</td>
                            <td className="body-data4">{post.createdAt}</td>
                            <td className="body-data5">{}</td>
                        </tr>)}
                    </table>
                </section>

                <div className="new-post">
                        <NavLink to={{pathname: '/review/newpost'}}>New Post</NavLink>
                </div> 

                <div className="pages">
                    <span>
                        <button onClick={this.prevPagesHandler}>prev</button>
                    </span>

                    {this.state.pageIndexes.slice(this.state.start, this.state.end).map(pageIndex => 
                        <span>
                            <button onClick={ () => this.currentPageHandler(pageIndex) } key={pageIndex}>{pageIndex + 1}</button>
                        </span>
                    )}
                        
                    <span>
                        <button onClick={this.nextPagesHandler}>next</button>
                    </span>
                </div>
            </div>
        )
    }
}

export default Review