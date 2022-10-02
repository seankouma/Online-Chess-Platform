import React, {Component} from 'react';
import { useHistory } from 'react-router-dom';
import {Link} from 'react-router-dom';
import {LOG} from '../../utils/constants';
import {getOriginalServerUrl, sendAPIRequest} from '../../utils/restfulAPI';


export default class Match extends Component {

    constructor(props) {
        super(props);
        this.getCookie = this.getCookie.bind(this);
        this.state = {
            userMatches: []
        };
    }

    async componentDidMount() {
        const requestResponse = await sendAPIRequest({requestType: "match", username: this.getCookie('cs414t16user')}, getOriginalServerUrl());
        console.log(requestResponse);
        if (requestResponse) {
            let matches = [];
            this.setState({userMatches: requestResponse.info});
        } else {
            LOG.info('Failed to get match records');
        }
    }

    handleClick(matchId){
        console.log('clicked')
        window.location.href='/board/' + matchId;
    }
    getCookie(cname) {
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

    render() {
        return (
            <div className='row'>
                <div className='col-lg-12 table-responsive table-bordered'>
                    <table className="table align-middle table-striped">
                        <thead>
                        <tr>
                            <th>Player</th>
                            <th>Match state</th>
                            <th>Result</th>
                        </tr>
                        </thead>
                        <tbody>
                            {
                                this.state.userMatches.map((item, index) => {
                                    if (item.gameState == "In Progress") {
                                        return (
                                            <tr onClick={() => this.handleClick(item.matchID)}>
                                                <td align="center">{item.opponent}</td>
                                                <td align="center">{item.gameState}</td>
                                                <td align="center">Pending</td>
                                            </tr>
                                        )
                                    } else {
                                        return (
                                            <tr onClick={() => this.handleClick(item.matchID)}>
                                                <td align="center">{item.opponent}</td>
                                                <td align="center">{item.gameState}</td>
                                                <td align="center">Winner</td>
                                            </tr>
                                        )
                                    }
                                })
                            }
                        </tbody>
                    </table>
                </div>
            </div>
        );
    }
}


