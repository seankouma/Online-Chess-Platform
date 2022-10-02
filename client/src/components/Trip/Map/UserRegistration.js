import React, { Component } from 'react';
import {Form, FormGroup, Label, Input, Button} from 'reactstrap';
import { LOG } from '../../../utils/constants';
import { getOriginalServerUrl, sendAPIRequest } from '../../../utils/restfulAPI';


export default class UserRegistration extends Component {

    constructor(props) {
        super(props);
        this.state = {
            firstName: '',
            lastName: '',
            email: '',
            username: '',
            password: ''
        }
    }

    submitForm(e) {
        console.log('Email: ${this.state.email}');
        sendRegisterUserRequest(this.state.firstName, this.state.lastName, this.state.email, this.state.username, this.state.password);
    }

    handleChange = (event) => {
        const { target } = event;
        const value = target.value;
        const { name } = target;
    
        this.setState({
          [name]: value,
        });
      };

    render() {
        return (
            <Form className="registerForm" onSubmit={(e) => this.submitForm(e)}>
                <FormGroup>
                    <Label for="fName_label">First Name</Label>
                    <Input type="name" name="firstName" id="fName_label" placeholder="" value={this.firstName} onChange={(e) => this.handleChange(e)}/>
                </FormGroup>
                <FormGroup>
                    <Label for="lName_label">Last Name</Label>
                    <Input type="name" name="lastName" id="lName_label" placeholder="" value={this.lastName} onChange={(e) => this.handleChange(e)}/>
                </FormGroup>
                <FormGroup>
                    <Label for="email_label">Email</Label>
                    <Input type="email" name="email" id="email_label" placeholder="" value={this.email} onChange={(e) => this.handleChange(e)}/>
                </FormGroup>
                <FormGroup>
                    <Label for="username_label">Username</Label>
                    <Input type="username" name="username" id="username_label" placeholder="" value={this.username} onChange={(e) => this.handleChange(e)}/>
                </FormGroup>
                <FormGroup>
                    <Label for="password_label">Password</Label>
                    <Input type="password" name="password" id="password_label" placeholder="" value={this.password} onChange={(e) => this.handleChange(e)}/>
                </FormGroup>
                <Button>Register</Button>
            </Form>
        );
    }
}

function processServerConfigSuccess(config) {
    LOG.info("Register Success");
}

async function sendRegisterUserRequest(first, last, email, username, password) {
    const requestResponse = await sendAPIRequest({ requestType: "register", firstName: first, lastName: last, email: email, username: username, password: password}, getOriginalServerUrl());
    if (requestResponse) {
        processServerConfigSuccess(requestResponse);
    } else {
        LOG.info("Register failure");
    }
}

