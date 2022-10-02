import React from 'react';
import { Container } from 'reactstrap';
import HeaderLogo from '../../static/images/tcoLogo.svg';
import { CLIENT_TEAM_NAME } from '../../utils/constants';

const COURSE_URL = "https://cs.colostate.edu/~cs314";

export default function Header(props) {
    return (
        <div className="full-width header vertical-center">
            <Container>
                <div className="vertical-center">
                    <a href={COURSE_URL} target="_blank">
                        <img className="tco-logo" src={HeaderLogo} alt="TCO Brand Logo" />
                    </a>
                    <a role="button" onClick={props.toggleAbout}>
                        <h1 className="tco-text-upper">{CLIENT_TEAM_NAME}</h1>
                    </a>
                </div>
            </Container>
        </div>
    );
}
