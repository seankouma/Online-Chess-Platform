import React, { Component } from "react";
import { Form, FormGroup, Label, Input, Button } from "reactstrap";
import { Link } from "react-router-dom";
import "./login.css";
import { LOG } from "../../utils/constants";
import { getOriginalServerUrl, sendAPIRequest } from "../../utils/restfulAPI";

export default class Login extends Component {
  constructor(props) {
    super(props);
    this.state = {
      email: "",
      password: "",
    };
  }

  handleChange = (event) => {
    const { target } = event;
    const value = target.value;
    const { name } = target;

    this.setState({
      [name]: value,
    });
  };

  submitForm(e) {
    console.log("Email: ${this.state.email}");
    sendLoginRequest(this.state.email, this.state.password);
  }

  render() {
    return (
        <div className="card">
          <div className="card-body">
            <Form className="loginForm" onSubmit={(e) => this.submitForm(e)}>
              <FormGroup>
                <Label for="email">Email</Label>
                <Input
                    type="email"
                    name="email"
                    id="email"
                    placeholder="Please enter email"
                    value={this.email}
                    onChange={(e) => this.handleChange(e)}
                />
              </FormGroup>
              <FormGroup>
                <Label for="password">Password</Label>
                <Input
                    type="password"
                    name="password"
                    id="password"
                    placeholder=""
                    value={this.password}
                    onChange={(e) => this.handleChange(e)}
                />
              </FormGroup>
              <Button type="button" onClick={(e) => this.submitForm(e)}>Login</Button> &nbsp;
            </Form>
            <Button className="registerBtn">
              <Link className="link-btn " to="/register">
                Register
              </Link>
            </Button>
          </div>
        </div>
    );
  }
}

function processServerConfigSuccess(config) {
  LOG.info("Login Success");
  document.cookie = "cs414t16user=" + config.email;
  document.location.href = "/";
}

async function sendLoginRequest(email, password) {
  const requestResponse = await sendAPIRequest(
      {
        requestType: "login",
        email: email,
        password: password,
      },
      getOriginalServerUrl()
  );
  if (requestResponse) {
    processServerConfigSuccess(requestResponse);
  } else {
    LOG.info("Login failure");
  }
}