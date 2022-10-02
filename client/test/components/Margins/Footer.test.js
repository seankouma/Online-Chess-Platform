import React from 'react';
import { render, screen, waitFor } from '@testing-library/react';
import '@testing-library/jest-dom';
import user from '@testing-library/user-event';
import { beforeEach, describe, it, jest } from '@jest/globals';
import { VALID_CONFIG_RESPONSE } from '../../sharedMocks';
import Footer from '../../../src/components/Margins/Footer';

describe('Footer', () => {
    const processServerConfigSuccess = jest.fn();
    const serverSettings = {
        serverConfig: { 'requestType': 'config', 'serverName': 't99' },
        serverUrl: 'http://localhost:8000'
    };

    let serverSettingsLink;

    beforeEach(() => {
        fetch.resetMocks();
        fetch.mockResponse(VALID_CONFIG_RESPONSE);

        render(<Footer
            serverSettings={serverSettings}
            processServerConfigSuccess={processServerConfigSuccess}
        />);

        serverSettingsLink = screen.getByText(`(${serverSettings.serverUrl}).`);
    });

    it('opens ServerSettings on link pressed and closes on cancel button', async () => {
        user.click(serverSettingsLink);

        const cancelButton = screen.getByRole('button', { name: /cancel/i });
        user.click(cancelButton);

        await waitFor(() => {
            expect(screen.queryByDisplayValue(serverSettings.serverUrl)).toBe(null);
        });
    });

    it('opens ServerSettings on link pressed and saves on close button', async () => {
        user.click(serverSettingsLink);

        const saveButton = screen.getByRole('button', { name: /save/i });
        await waitFor(() => {
            expect(saveButton.classList.contains("disabled")).toBe(false);
        });
        user.click(saveButton);

        await waitFor(() => {
            const modalInput = screen.queryByDisplayValue(serverSettings.serverUrl);
            expect(modalInput).not.toBeInTheDocument();
        });
    });
});
