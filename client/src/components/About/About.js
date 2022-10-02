import React from 'react';
import { Container, Row, Col, Button } from 'reactstrap';
import { CLIENT_TEAM_NAME } from '../../utils/constants';
import Person from "./Person";
import Team from "./Team";
import {memberData, teamData} from "./teamInfo";

export default function About(props) {
    return (
        <Container>
            <Row>
                <Col>
                    <h2>{CLIENT_TEAM_NAME}</h2>
                </Col>
                <Col xs="auto">
                    <Button color="primary" onClick={props.closePage} xs={1}>
                        Close
                    </Button>
                </Col>
            </Row>
            <Row>
                <Team teamName={teamData.teamName} missionStatement={teamData.missionStatement} pic={teamData.imagePath} />
            </Row>
            <Row>
                {memberData.map((teamMate, index) =>
                    <Person key={index} name={teamMate.name} homeTown={teamMate.homeTown} bio={teamMate.bio} pic={teamMate.imagePath} />
                )}
            </Row>
        </Container>
    );
}
