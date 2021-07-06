import { Component } from 'react'
import { NavLink } from 'react-router-dom'

import './Header.css'

class Header extends Component {

    render() {
        return (
            <div className="all-header">
                <section className="left-header">
                    <ul>
                        <NavLink exact to={{ pathname: '/home' }} className="logo">
                            eCinema
                        </NavLink>
                        <NavLink to={{ pathname: '/reserve'}}>
                            예매
                        </NavLink>
                        <NavLink to={{ pathname: '/screen'}}>
                            상영
                        </NavLink>
                        <NavLink to={{ pathname: '/review'}}>
                            후기
                        </NavLink>
                    </ul>
                </section>
                <section className="right-header">
                    <ul>

                        <NavLink to={{ pathname: '/login'}}>
                            로그인
                        </NavLink>
                    </ul>
                </section>
            </div>
        )
    }
}

export default Header