import { Component } from "react";
import axios from "axios";

class Reservation extends Component {

    state = {
        movieId: this.props.match.params.id,
        rsvDate: '',
        rsvTime: '09:00',
        ccNumber: '',
        ccExpiration: '',
        ccCVV: ''
    }

    postHandler = () => {
        const post = {
            movieId: this.state.movieId,
            rsvDate: this.state.rsvDate,
            rsvTime: this.state.rsvTime,
            ccNumber: this.state.ccNumber,
            ccExpiration: this.state.ccExpiration,
            ccCVV: this.state.ccCVV
        }
        console.log(post)

        axios.post('/api/reservation', post)
             .then(response => {
                console.log(response)
                this.props.history.push('/home')})
             .catch(e => console.log(e))
    }

    render () {
        const { rsvDate, rsvTime, ccNumber, ccExpiration, ccCVV } = this.state
        const { title_long } = this.props.location.state
        
        return (
            <section>
                <div className="NewPost">
                    <h2>{title_long}</h2>
                    <h3>6,000원</h3>
                    <label>예매일자</label>
                    <input type="date" value={rsvDate} onChange={(e) => this.setState({rsvDate: e.target.value})} />
                    <label>예매시간</label>
                    <select value={this.state.rsvTime} onChange={(e) => this.setState({rsvTime: e.target.value})}>
                        <option value="09:00">09:00</option>
                        <option value="13:00">13:00</option>
                        <option value="17:00">17:00</option>
                        <option value="21:00">21:00</option>
                        <option value="01:00">01:00</option>
                        <option value="05:00">05:00</option>
                    </select>
                    <label>카드번호</label>
                    <input type="text" value={ccNumber} onChange={(e) => this.setState({ccNumber: e.target.value})} />
                    <label>카드 만료기한</label>
                    <input type="text" value={ccExpiration} onChange={(e) => this.setState({ccExpiration: e.target.value})} />
                    <label>카드 CVV</label>
                    <input type="text" value={ccCVV} onChange={(e) => this.setState({ccCVV: e.target.value})} />
                    <button onClick={this.postHandler}>결제</button>
                </div>
            </section>
        );
    }
}

export default Reservation