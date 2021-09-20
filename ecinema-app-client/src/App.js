import {Component} from 'react';
import Header from './header/Header';
import Main from './main/Main';

class App extends Component {

    state = { token: localStorage.getItem("token") }
    
    tokenHandler = (token) => {
        this.setState( {token: token} )

    }

    render() {
        return (
            <div className="App">
                <Header token={this.state.token} tokenHandler={this.tokenHandler}/>
                <Main tokenHandler={this.tokenHandler}/>
            </div>
        )
    }
}

export default App;