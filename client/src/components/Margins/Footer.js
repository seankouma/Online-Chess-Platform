import React from 'react';
import { Container } from 'reactstrap';
import { useToggle } from '../../hooks/useToggle';
import ServerSettings from './ServerSettings';

const UNICODE_LINK_SYMBOL = "\uD83D\uDD17";
const UNICODE_WARNING_SIGN = "\u26A0";
const UNKNOWN_SERVER_NAME = "Unknown";

export default function Footer(props) {
    const [serverSettingsOpen, toggleServerSettings] = useToggle(false);

    return (
        <div className="full-width footer">
            <ServerInformation toggleServerSettings={toggleServerSettings} serverSettingsOpen={serverSettingsOpen} {...props} />
        </div>
    );
}

function ServerInformation(props) {
    function connectedToValidServer() {
        const serverConfig = props.serverSettings.serverConfig;
        return serverConfig && serverConfig.serverName;
    }
    const serverName = connectedToValidServer() ? props.serverSettings.serverConfig.serverName : UNKNOWN_SERVER_NAME;
    const linkStatusSymbol = connectedToValidServer() ? UNICODE_LINK_SYMBOL : UNICODE_WARNING_SIGN;

    return (
        <div className="vertical-center tco-text">
            <Container>
                <div className="centered">
                    {linkStatusSymbol} Connected to {serverName} &nbsp;
                    <a className="tco-text" onClick={props.toggleServerSettings}>
                        ({props.serverSettings.serverUrl}).
                    </a>
                    <ServerSettings
                        isOpen={props.serverSettingsOpen}
                        toggleOpen={props.toggleServerSettings}
                        serverSettings={props.serverSettings}
                        processServerConfigSuccess={props.processServerConfigSuccess}
                    />
                </div>
            </Container>
        </div>
    );
}