import { ResterAppManager, RESTERAPP_STUDENTS_URL } from './ResterAppManager';
/* import * as testData from '../testData/index'; */
import * as fetchMock from 'fetch-mock';

describe('ResterAppManager', () => {
    const resterApp: ResterAppManager = new ResterAppManager();

    it('can fetch', async () => {
        /*
        const myHeaders: Headers = new Headers({'content-type': 'application/json'});
        var respInit: ResponseInit = { 'status' : 200 , 'statusText' : 'OK', 'headers': myHeaders};
        const resp: Response = new Response(testData.LIST_STUDENT_DATA_TWO, respInit);
        */

        fetchMock.get(RESTERAPP_STUDENTS_URL, 200);
        resterApp.fetchStudents();
        /*expect(response).toEqual({ hello: 'yay'});*/
    });
});