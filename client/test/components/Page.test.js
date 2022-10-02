import React from 'react';
import { render, screen, waitFor } from '@testing-library/react';
import user from '@testing-library/user-event';
import { beforeEach, describe, expect, it } from '@jest/globals';
import { CLIENT_TEAM_NAME } from '../../src/utils/constants';
import { VALID_CONFIG_RESPONSE } from '../sharedMocks';
import Page from '../../src/components/Page';

describe('Page', () => {
    beforeEach(() => {
        fetch.resetMocks();
        fetch.mockResponse(VALID_CONFIG_RESPONSE);

        render(<Page />);
    });

    it('renders the team name in header', async () => {
        await waitFor(() => {
            const headings = screen.getAllByRole('heading', { name: /T[0-9][0-9]/i });
            expect(headings[0].textContent).toEqual(CLIENT_TEAM_NAME);
        })
    });

    it('closes map and opens about when header is clicked', async () => {
        const collapse = screen.getByTestId('planner-collapse');
        expect(collapse.classList.contains('show')).toBe(true);

        const toggleButton = screen.getByRole('button', { name: /T[0-9][0-9]/i })
        user.click(toggleButton);

        await waitFor(() => {
            expect(collapse.classList.contains('show')).toBe(false);

            const aboutCloseButton = screen.getByRole('button', { name: /close/i });
            expect(aboutCloseButton.textContent).toEqual('Close');
        });
    });
});
