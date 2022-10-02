import React from 'react';
import '@testing-library/jest-dom';
import {act, renderHook} from '@testing-library/react-hooks';
import { beforeEach, describe, expect, it } from '@jest/globals';
import { REVERSE_GEOCODE_RESPONSE } from '../sharedMocks';
import { LOG } from '../../src/utils/constants';
import { usePlaces } from '../../src/hooks/usePlaces';

describe('usePlaces', () => {
    const mockPlace = {latitude: "40.570", longitude: "-105.085"};
    const mockPlaceResponse = {
        lat: 40.57,
        lng: -105.085,
        name: 'Colorado State University, South College Avenue, Fort Collins, Larimer County, Colorado, 80525-1725, United States'
    };

    let hook;

    beforeEach(() => {
        jest.clearAllMocks();
        fetch.resetMocks();
        const { result } = renderHook(() => usePlaces());
        hook = result;
    });

    it('appends a place', async () => {
        fetch.mockResponse(REVERSE_GEOCODE_RESPONSE);
        expect(hook.current.places).toEqual([]);

        await act(async () => {
            hook.current.placeActions.append(mockPlace);
        });

        expect(hook.current.places).toEqual([mockPlaceResponse]);
    });

    it('selects a place at an index', async () => {
        fetch.mockResponse(REVERSE_GEOCODE_RESPONSE);

        await act(async () => {
            hook.current.placeActions.append(mockPlace);
        });
        expect(hook.current.selectedIndex).toEqual(0);

        await act(async () => {
            hook.current.placeActions.append(mockPlace);
        });
        expect(hook.current.selectedIndex).toEqual(1);
        expect(hook.current.places.length).toEqual(2);

        act(() => {
            hook.current.placeActions.selectIndex(0);
        });
        expect(hook.current.selectedIndex).toEqual(0);
    });

    it('sets index to -1 if selecting invalid index', () => {
        jest.spyOn(LOG, 'error').mockImplementation(() => {});

        act(() => {
            hook.current.placeActions.selectIndex(99);
        });
        expect(hook.current.selectedIndex).toEqual(-1);
        expect(LOG.error.mock.calls.length).toEqual(1);
    });

    it('removes a place at an index', async () => {
        fetch.mockResponse(REVERSE_GEOCODE_RESPONSE);

        await act(async () => {
            hook.current.placeActions.append(mockPlace);
        });
        expect(hook.current.places.length).toEqual(1);

        act(() => {
            hook.current.placeActions.removeAtIndex(0);
        });
        expect(hook.current.places).toEqual([]);
        expect(hook.current.selectedIndex).toEqual(-1);
    });

    it('sets preceding place as selected when removing an index', async () => {
        fetch.mockResponse(REVERSE_GEOCODE_RESPONSE);

        for (let i = 0; i < 5; i++) {
            await act(async () => {
                hook.current.placeActions.append(mockPlace);
            });
        }

        act(() => {
            hook.current.placeActions.removeAtIndex(4);
        });
        expect(hook.current.places.length).toEqual(4);
        expect(hook.current.selectedIndex).toEqual(3);
    });

    it('keeps selected index at 0 if index 0 is selected and removed', async () => {
        fetch.mockResponse(REVERSE_GEOCODE_RESPONSE);

        for (let i = 0; i < 3; i++) {
            await act(async () => {
                hook.current.placeActions.append(mockPlace);
            });
        }

        act(() => {
            hook.current.placeActions.selectIndex(0);
        });
        act(() => {
            hook.current.placeActions.removeAtIndex(0);
        });
        expect(hook.current.places.length).toEqual(2);
        expect(hook.current.selectedIndex).toEqual(0);
    });

    it('denies removing a place at an invalid index', () => {
        jest.spyOn(LOG, 'error').mockImplementation(() => {});

        act(() => {
            hook.current.placeActions.removeAtIndex(42);
        });
        expect(LOG.error.mock.calls.length).toEqual(1);
    });

    it('removes all places', async () => {
        fetch.mockResponse(REVERSE_GEOCODE_RESPONSE);

        for (let i = 0; i < 5; i++) {
            await act(async () => {
                hook.current.placeActions.append(mockPlace);
            });
        }
        expect(hook.current.places.length).toEqual(5);

        act(() => {
            hook.current.placeActions.removeAll();
        });
        expect(hook.current.places).toEqual([]);
    });
});