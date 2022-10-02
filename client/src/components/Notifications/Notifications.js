import React, { Component } from 'react';
import './notifications.css';
import {getOriginalServerUrl, sendAPIRequest} from "../../utils/restfulAPI";
import {LOG} from "../../utils/constants";
import { Collapse, Modal, ModalBody, ModalFooter, ModalHeader, Button } from 'reactstrap';
import {Form, FormGroup, Label, Input} from 'reactstrap';

const UserRecords = [
    {
        userId: 1,
        email: 'loggedInUser'
    },
    {
        userId: 2,
        email: 'user2'
    },
    {
        userId: 3,
        email: 'User 3'
    },
    {
        userId: 4,
        email: 'User 4'
    },
    {
        userId: 5,
        email: 'User 5'
    }]

export default class Notifications extends Component {

    constructor(props) {
        super(props);
        this.sendAcceptMessage = this.sendAcceptMessage.bind(this);
        this.sendRejectMessage = this.sendRejectMessage.bind(this);
        this.setReceivedOpen = this.setReceivedOpen.bind(this);
        this.setSentOpen = this.setSentOpen.bind(this);
        this.setModalOpen = this.setModalOpen.bind(this);
        this.setQuery = this.setQuery.bind(this);
        this.sendInvitation = this.sendInvitation.bind(this);
        this.startMatch = this.startMatch.bind(this);
        this.getCookie = this.getCookie.bind(this);
        this.state = {
            openReceived: true,
            openSent: false,
            openModal: false,
            ws: null,
            invitationRecords: [],
            sentInvites: [],
            userRecords: [],
            query: ''
        };

    }

    async componentDidMount() {
        this.connect();
        const requestResponse = await sendAPIRequest({requestType: "invitations", username:this.getCookie('cs414t16user')}, getOriginalServerUrl());
        console.log(requestResponse);
        if (requestResponse) {
            let invitationRequests = [];
            let sentRequests = [];
            requestResponse.invitations.forEach(not => {
                if (not.sender == this.getCookie('cs414t16user')){
                    sentRequests.push(not);
                } else if (not.sender != this.getCookie('cs414t16user') && not.type == "Invite") {
                    invitationRequests.push(not)
                }
            })
            this.setState({invitationRecords: invitationRequests});
            this.setState({sentInvites: sentRequests});
            this.setState({userRecords:requestResponse.users});
            // this.setState({userRecords:UserRecords});
            console.log("this.state.invitationRecords",this.state.invitationRecords)
        } else {
            LOG.info('Failed to get invitation records');
        }
        //this.connect();
    }
    connect = () => {
        var ws = new WebSocket("ws://localhost:8000/notifications");

        ws.onopen = () => {
            console.log("connected websocket main component");
            this.setState({ws: ws});
            try {
                this.state.ws.send(JSON.stringify({
                    type: "Connected",

                    sender: this.getCookie('cs414t16user'),
                    responder: "",
                    msg: "Connection successful"
                })) //send data to the server
            } catch (error) {
                console.log(error) // catch error
            }
        };

        ws.onclose = e => {
            console.log(
                'Socket is closed. Reconnect will be attempted i',
                e.reason
            );
            //this.connect();
        };

        // websocket onerror event listener
        ws.onerror = err => {
            console.error(
                err.message,
            );
            ws.close();
        };

        ws.onmessage = evt => {
            console.log(JSON.parse(evt.data));
            let receivedEvent = JSON.parse(evt.data);
            if (receivedEvent.type == "Accept" || receivedEvent.type == "Reject") {
                let sentInv = [...this.state.sentInvites];
                let record = [];
                sentInv.forEach(inv => {
                    if (receivedEvent.responder == inv.responder) {
                        inv.sender = receivedEvent.sender;
                        inv.type = receivedEvent.type;
                        inv.responder = receivedEvent.responder;
                        inv.msg = receivedEvent.msg;
                        console.log("record", inv)
                        record.push(inv);
                    }
                })
                if (record.length == 0){
                    sentInv.push(receivedEvent);
                }
                this.setState({sentInvites:sentInv})
            } if (receivedEvent.type == "Invite") {
                let receivedInvitations = [...this.state.invitationRecords];
                let record = [];
                receivedInvitations.forEach(inv => {
                    if (receivedEvent.sender == inv.sender) {
                        inv.sender = receivedEvent.sender;
                        inv.type = receivedEvent.type;
                        inv.responder = receivedEvent.responder;
                        inv.msg = receivedEvent.msg;
                        console.log("record", inv)
                        record.push(inv);
                    }
                })
                if (record.length == 0){
                    receivedInvitations.push(receivedEvent);
                }
                this.setState({invitationRecords:receivedInvitations})
            }

        }
    }
    check = () => {
        const { ws } = this.state;
        if (!ws || ws.readyState == WebSocket.CLOSED) this.connect(); //check if websocket instance is closed, if so call `connect` function.
    };
    sendAcceptMessage (user, index) {
        this.check();
        console.log("sending")
        try {
            this.state.ws.send(JSON.stringify({
                type: "Accept",
                sender: user,
                responder: this.getCookie('cs414t16user'),
                msg: "Invitation Accepted by user"
            })) //send data to the server
            let newInvitationRecords = [...this.state.invitationRecords];
            newInvitationRecords.splice(index, 1);
            this.setState(state => ({
                invitationRecords: newInvitationRecords
            }));
        } catch (error) {
            console.log(error) // catch error
        }

    }
    sendRejectMessage (user, index) {
        this.check();
        try {
            this.state.ws.send(JSON.stringify({
                type: "Reject",
                sender: user,
                responder: this.getCookie('cs414t16user'),
                msg: "Invitation rejected by user"
            })) //send data to the server
            let newInvitationRecords = [...this.state.invitationRecords];
            newInvitationRecords.splice(index, 1);
            this.setState(state => ({
                invitationRecords: newInvitationRecords
            }));
        } catch (error) {
            console.log(error) // catch error
        }

    }
    setReceivedOpen (isOpen) {
        this.setState({openReceived: isOpen});
        this.setState({openSent: !isOpen});
    }
    setSentOpen (isOpen) {
        this.setState({openReceived: !isOpen});
        this.setState({openSent: isOpen});
    }
    setModalOpen (isOpen) {
        this.setState({openModal: isOpen});
    }
    setQuery(e){
        this.setState({query: e.target.value});
    }
    sendInvitation(user){
        this.check();
        try {
            this.state.ws.send(JSON.stringify({
                type: "Invite",
                sender: this.getCookie('cs414t16user'),
                responder: user,
                msg: "Invitation for match"
            })) //send data to the server

            let updatedInvites = [...this.state.sentInvites];
            let record = [];
            updatedInvites.forEach(inv => {
                if (user == inv.responder) {
                    inv.type = "Invite";
                    record.push(inv);
                }
            })
            if (record.length == 0){
                updatedInvites.push({type: "Invite",
                    sender: this.getCookie('cs414t16user'),
                    responder: user,
                    msg: "Invitation for match"});
            }
            this.setState({sentInvites:updatedInvites})
        } catch (error) {
            console.log(error) // catch error
        }

    }
    startMatch (opponent) {
        this.sendMatchRequest(opponent).then((res) => {
            this.state.ws.send(JSON.stringify({
                type: "Start Match",
                sender: this.getCookie('cs414t16user'),
                responder: opponent,
                msg: "Invitation Accepted by user"
            }))
            window.location.href='/board/'+ res.matchID;
        })
    }
    async sendMatchRequest(opponent) {
        const requestResponse = await sendAPIRequest(
            {
                requestType: "creatematch",
                usernameWhite: this.getCookie('cs414t16user'),
                usernameBlack: opponent
            },
            getOriginalServerUrl()
        );
        if (requestResponse) {
            return requestResponse;
        } else {
            LOG.info("Failed to get match response");
        }
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
                <Modal isOpen={this.state.openModal} toggle={() => this.setModalOpen(false)}>
                    <ModalHeader>
                        Invite Friend
                    </ModalHeader>
                    <ModalBody>
                        <Form method="get">
                            <FormGroup>
                                <Label htmlFor="search">
                                    <span className="visually-hidden">Search User</span>
                                </Label>
                                <Input onChange={this.setQuery} type="text" id="search" placeholder="Type Username" name="search"/>
                            </FormGroup>
                        </Form>
                        <ul>
                            {
                                this.state.userRecords.filter(post => {
                                    if (this.state.query === '') {
                                        return post;
                                    } else if (post.email.toLowerCase().includes(this.state.query.toLowerCase())) {
                                        return post;
                                    }
                                }).map((post, index) => (
                                    <div className="searchResult" key={index}>
                                        <span>{post.email}</span>
                                        <button className='btn btn-primary invite-btn' onClick={() => this.sendInvitation(post.email)}>Send Invite</button>
                                    </div>
                                ))
                            }
                        </ul>
                    </ModalBody>
                    <ModalFooter>
                        <button className='btn btn-secondary' onClick={() => this.setModalOpen(false)}>Close</button>
                    </ModalFooter>
                </Modal>
                <div className='col-lg-12'>
                    <button className='btn btn-primary'
                        onClick={() => this.setReceivedOpen(!this.state.openReceived)}
                        aria-controls="example-collapse-text"
                        aria-expanded={this.state.openReceived}
                    >
                        Received
                    </button>&nbsp;
                    <button className='btn btn-primary'
                            onClick={() => this.setSentOpen(!this.state.openSent)}
                            aria-controls="example-collapse-text"
                            aria-expanded={this.state.openSent}
                    >
                        Sent
                    </button>&nbsp;
                    <button className='btn btn-primary' onClick={() => this.setModalOpen(!this.state.openModal)}>Invite Friend</button>
                <Collapse isOpen={this.state.openReceived}>
                        <div className='col-lg-12 table-responsive table-bordered'>
                            <table className="table align-middle table-striped">
                                <thead>
                                <tr>
                                    <th>Username</th>
                                    <th>Accept</th>
                                    <th>Reject</th>
                                </tr>
                                </thead>
                                <tbody>
                                {
                                    this.state.invitationRecords.map((item, index) => {
                                        return (
                                            <tr>
                                                <td align="center">{item.sender}</td>
                                                <td align="center">
                                                    <button onClick={() => this.sendAcceptMessage(item.sender, index)}
                                                            className="btn btn-success">Accept
                                                    </button>
                                                </td>
                                                <td align="center">
                                                    <button onClick={() => this.sendRejectMessage(item.sender, index)} className="btn btn-danger">Reject
                                                    </button>
                                                </td>
                                            </tr>
                                        )
                                    })
                                }
                                </tbody>
                            </table>
                        </div>
                </Collapse>
                    <Collapse isOpen={this.state.openSent}>
                        <div className='col-lg-12 table-responsive table-bordered'>
                            <table className="table align-middle table-striped">
                                <thead>
                                <tr>
                                    <th>Username</th>
                                    <th>Response</th>
                                </tr>
                                </thead>
                                <tbody>
                                {
                                    this.state.sentInvites.map((item) => {
                                        if (item.type != "Invite") {
                                           let button = <button className="btn btn-primary" onClick={() => this.startMatch(item.responder)}>Start Match
                                            </button>;
                                        }
                                        return (
                                            <tr>
                                                <td align="center">{item.responder}</td>
                                                <td align="center">
                                                    { item.type ==  "Invite" &&
                                                    <span>Pending</span>
                                                    }
                                                </td>
                                                <td align="center">
                                                    { item.type != "Invite" &&
                                                        <button className="btn btn-primary" onClick={() => this.startMatch(item.responder)}>Start Match
                                                        </button>
                                                    }
                                                </td>
                                            </tr>
                                        )
                                    })
                                }
                                </tbody>
                            </table>
                        </div>
                    </Collapse>
                </div>
            </div>
        )
        // }
    }
}