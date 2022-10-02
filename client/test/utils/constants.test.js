import { describe, expect, it } from "@jest/globals";
import { setLogLevelIfDefault } from '../../src/utils/constants'
import { ulog } from 'ulog/ulog';

describe('Constants', () => {
    it('log level is correctly set', () => {
        setLogLevelIfDefault();
        expect(ulog.level).toEqual(ulog.ERROR);
    });

    it('log level gets set to info with env var', () => {
        process.env.CLIENT_LOG_LEVEL = 'INFO';
        setLogLevelIfDefault();
        expect(ulog.level).toEqual(ulog.INFO);
    });
});