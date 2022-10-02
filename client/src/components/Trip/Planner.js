import React, {useEffect, useRef} from 'react';
import {Col, Container, Row} from 'reactstrap';
import UserRegistration from './Map/UserRegistration';
import Login from '../Login/Login';
import Board from '../Board/Board';
import Notifications from '../Notifications/Notifications';
import Match from '../Match/Match';
import '../../static/styles/sidebar.css'
import {
    BrowserRouter as Router, Link, Redirect,
    Route,
    Switch
} from 'react-router-dom';
import { useHistory } from "react-router-dom";

export default function Planner() {
     return (
        <Router>
            { getCookie('cs414t16user') != "" &&
                <div id="mySidenav" className="sidenav">
                    <Link to="/">Games</Link>
                    <Link to="/notifications">Notifications</Link>
                    <Link onClick={()=>window.location.href='/login'}>{getCookie('cs414t16user')}</Link>
                </div>
            }

            <Container>
                <Section>
                    <Switch>
                        <ProtectedRoute  exact path='/' component={Match}></ProtectedRoute>
                        <Route exact path='/board/:boardId' component={Board}></Route>
                        <Route  path='/notifications' component={Notifications}></Route>
                        <div className='row'>
                            <div className='col-lg-6 offset-lg-1' >
                                <Route exact path='/login' component={Login}></Route>
                                <Route path='/register' component={UserRegistration}></Route>
                            </div>
                        </div>
                    </Switch>
                </Section>
            </Container>
        </Router>
    );
}
function ProtectedRoute({ component: Component, ...restOfProps }) {
    console.log("in ProtectedRoute")
    let isAuthenticated = null;
    if (getCookie('cs414t16user') != "") {
        isAuthenticated = getCookie('cs414t16user')
    }
    // const isAuthenticated = getCookie('cs414t16user');
    console.log("this", isAuthenticated);

    return (
        <Route
            {...restOfProps}
            render={(props) =>
                isAuthenticated ? <Component {...props} /> : <Redirect to="/login" />
            }
        />
    );
}

function getCookie(cname) {
    let name = cname + "=";
    let decodedCookie = decodeURIComponent(document.cookie);
    let ca = decodedCookie.split(';');
    for(let i = 0; i <ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}
function Section(props) {
    return (
        <Row>
            <Col lg={12} md={{ size: 12, offset: 2 }}>
                  {props.children}
            </Col>
        </Row>
    );
}