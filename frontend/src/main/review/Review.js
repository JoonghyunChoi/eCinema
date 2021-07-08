import { Component } from "react";
import { NavLink, Link } from 'react-router-dom'
import axios from 'axios'
import './Review.css'

class Review extends Component {

    state = {
        posts: [ {id:1, title:'좋은 영화', writer:'최중현', content:'', createdAt:"2021/7/6 17:00"} ],
        pageIndexes: [0],
        start: 0,
        end : 10
    }

    componentDidMount() {
        axios.get('/api/posts/count')
            .then(response => {
                const dataCount = response.data

                let pageCount = Math.floor(dataCount / 10)
                if (dataCount % 10 > 0) {
                    pageCount++
                }

                const pageIndexes = []
                for (let i = 0; i < pageCount; i++) {
                    pageIndexes.push(i)
                }

                this.setState( {pageIndexes: pageIndexes} )
            })
            .catch(error => console.log(error))

        axios.get('/api/posts')
            .then(response => {this.setState( {posts: response.data} )})
            .catch(error => console.log(error)) 
    }

    pageHandler = (pageIndex) => {
        pageIndex = pageIndex * 10

        axios.get('/api/posts')     // ?index={pageIndex}
        .then(response => {this.setState( {posts: response.data} )})
        .catch(error => console.log(error))  
    }

    prevPagesHandler = () => {
        if (this.state.start > 0) {
            const start = this.state.start - 10
            const end = this.state.end - 10

            this.setState( {start: start, end: end} )
        }
    }

    nextPagesHandler = () => {
        if (this.state.pageIndexes.length > this.state.end) {
            const start = this.state.start + 10
            const end = this.state.end + 10

            this.setState( {start: start, end: end} )
        }
    }


    render() {      // 컴포넌트 쪼개기
        return (
            <div className="QA-container">
                <section className="QA-section">
                    <table>
                        <tr>
                            <th className="head-col1" scope="col">번호</th>
                            <th className="head-col2" scope="col">제목</th>
                            <th className="head-col3" scope="col">작성자</th>
                            <th className="head-col4" scope="col">등록일</th>
                            <th className="head-col5" scope="col"></th>
                        </tr>

                        {this.state.posts.map(post => 
                        <tr>  
                            <td className="body-data1">{post.id}</td>
                            <td className="body-data2">
                                <Link to={"/review/post" + post.id} key={post.id}>{post.title}</Link>
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
                            <button onClick={ () => this.pageHandler(pageIndex) } key={pageIndex}>{pageIndex + 1}</button>
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