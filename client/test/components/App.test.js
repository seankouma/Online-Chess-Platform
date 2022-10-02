import React from 'react';
import { render, screen } from '@testing-library/react';
import { beforeEach, describe, it } from '@jest/globals';
import { LOG } from '../../src/utils/constants';
import App from '../../src/components/App';

describe('App', () => {
    beforeEach(() => {
        fetch.resetMocks();
    });

    it('shows error snackbar if no server config', async () => {
        jest.spyOn(LOG, 'error').mockImplementation(() => {});
        fetch.mockReject(() => Promise.reject("API is down (expected)."));

        render(<App />);

        await screen.findByText(/failed/i);
    });
});

