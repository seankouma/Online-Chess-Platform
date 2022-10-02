import React from 'react';
import { act, render, screen, waitFor } from '@testing-library/react';
import user from '@testing-library/user-event';
import { expect, it } from '@jest/globals';
import { VALID_CONFIG_RESPONSE, INVALID_REQUEST } from '../../sharedMocks';
import { LOG } from '../../../src/utils/constants';
import ServerSettings from '../../../src/components/Margins/ServerSettings';

describe('Server Settings Modal', () => {
    const validUrl = 'http://localhost:8000';
    const invalidUrl = 'BAD URL';
    const serverSettings = { 'serverUrl': validUrl, 'serverConfig': null };
    const toggleOpen = jest.fn();
    const processServerConfigSuccess = jest.fn();

    let inputBox;
    let saveButton;

    beforeEach(() => {
        jest.clearAllMocks();
        fetch.resetMocks();

        jest.spyOn(LOG, 'error').mockImplementation(() => {});
        fetch.mockResponse(VALID_CONFIG_RESPONSE);

        render(<ServerSettings
            isOpen={true}
            serverSettings={serverSettings}
            toggleOpen={toggleOpen}
            processServerConfigSuccess={processServerConfigSuccess}
        />);

        inputBox = screen.getByDisplayValue(validUrl);
        saveButton = screen.getByRole('button', { name: /save/i });

        act(() => {
            user.clear(inputBox);
        });
    });

    it('updates input text onChange and disables save button with invalid url', async () => {
        user.type(inputBox, invalidUrl);

        await waitFor(() => {
            expect(inputBox.value).toEqual(invalidUrl);
            expect(saveButton.classList.contains('disabled')).toBe(true);
        });
    });

    it('disables save button on invalid Config response from url', async () => {
        fetch.mockResponseOnce(INVALID_REQUEST);

        user.type(inputBox, validUrl);

        await waitFor(() => {
            expect(saveButton.classList.contains('disabled')).toBe(true);
        });
        expect(LOG.error.mock.calls.length).toBeGreaterThanOrEqual(1);
    });

    it('disables save button on config request rejection', async () => {
        fetch.mockRejectOnce(new Error('Rejected'));

        user.type(inputBox, validUrl);

        await waitFor(() => {
            expect(saveButton.classList.contains('disabled')).toBe(true);
        });
        expect(LOG.error.mock.calls.length).toBeGreaterThanOrEqual(1);
    });

    it('save button is enabled with valid url', async () => {
        user.type(inputBox, validUrl);

        await waitFor(() => {
            expect(saveButton.classList.contains('disabled')).toBeFalsy();
        });
    });
});
