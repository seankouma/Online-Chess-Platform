import React, { useEffect, useState, useRef } from 'react';
import { sendAPIRequest } from '../utils/restfulAPI';

export function useServerInputValidation(serverUrl, close) {
    const [serverInput, setServerInput] = useState(serverUrl);
    const [validServer, setValidServer] = useState(false);
    const [config, setConfig] = useState(null);

    const inputUrl = useRef();
    const context = { serverInput, setServerInput, validServer, setValidServer, config, setConfig, inputUrl };

    const updateServerInput = (newUrl) => updateServerInputImpl(newUrl, context);
    const resetModal = () => resetModalImpl(serverUrl, close, context);

    useEffect(() => {
        updateServerInput(serverUrl);
    }, []);

    return [serverInput, updateServerInput, config, validServer, resetModal];
}

function updateServerInputImpl(newUrl, context) {
    const { setServerInput, setValidServer, setConfig, inputUrl } = context;

    setServerInput(newUrl);
    inputUrl.current = newUrl;
    if (shouldAttemptConfigRequest(newUrl)) {
        sendConfigRequest(newUrl, context);
    } else {
        setConfig(null);
        setValidServer(false);
    }
}

async function sendConfigRequest(serverURL, context) {
    const { setValidServer, setConfig, inputUrl } = context;

    setValidServer(false);
    const requestBody = { requestType: "config" };
    const configResponse = await sendAPIRequest(requestBody, serverURL);

    if (configResponse && serverURL === inputUrl.current) {
        setConfig(configResponse);
        setValidServer(true);
    }
}

function resetModalImpl(serverUrl, close, context) {
    const { setServerInput, setValidServer, setConfig } = context;

    setServerInput(serverUrl);
    setConfig(null);
    setValidServer(true);
    close();
}

function shouldAttemptConfigRequest(resource) {
    const urlRegex = /https?:\/\/.+/;
    return resource.match(urlRegex) !== null && resource.length > 15;
}