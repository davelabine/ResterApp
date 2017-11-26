import { StudentData } from '../types'; 

/* export const RESTERAPP_BASE_URL: string = 'http://resterapp.lvictories.us:8080/api';*/
export const RESTERAPP_BASE_URL: string = 'http://0.0.0.0:8080/service/api';
export const RESTERAPP_STUDENTS_URL: string = RESTERAPP_BASE_URL + '/students';

export class ResterAppManager {
      
    public async fetchStudents(): Promise<Array<StudentData>> {
        console.log('ResterAppManager() - fetching students');
        const myHeaders = new Headers();
        const requestInit: RequestInit = { method: 'GET',
                            headers: myHeaders,
                            mode: 'cors',
                            cache: 'default' };

        let students: Array<StudentData> = [];                    
        let response: Response = await fetch(RESTERAPP_STUDENTS_URL, requestInit);
        let contentType = response.headers.get('content-type');
        if (contentType && contentType.includes('application/json')) {
            students = await response.json();
            console.log(JSON.stringify(students));
            return students;
        }
        console.log('Incorrect contentType.  Expected application/json and received' 
                        + contentType);
        return students;
    }

    public async createStudent(s: StudentData): Promise<string> {
        console.log('ResterAppManager() - create student');
        const myHeaders = new Headers();
        /* DO NOT append any Content-Type headers.  The browser will do it for you.
           Yes, you heard right.  
        myHeaders.append('Content-Type', undefined);
        myHeaders.append('Accept-Encoding', 'multipart/form-data');
        */

        var formData = new FormData();
        formData.append('name', s.name);
        formData.append('id', s.id);

        const requestInit: RequestInit = {
            body: formData,
            headers: myHeaders,
            method: 'post',
        };
                 
        let response: Response = await fetch(RESTERAPP_STUDENTS_URL, requestInit);
        let skey = response.headers.get('student-key');
        if (!skey) { skey = ''; }
        console.log('createStudent response: ' + response.status + ', skey:' + skey);
        return skey;
    }

    public async deleteStudent(skey: string): Promise<void> {
        console.log('deleteStudent, skey: ' + skey);

        var myHeaders = new Headers();
        const requestInit: RequestInit = {
            headers: myHeaders,
            method: 'DELETE',
        };
                 
        const url = RESTERAPP_STUDENTS_URL + '/' + skey;
        let response: Response = await fetch(url, requestInit);
        console.log('deleteStudent response: ' + response.status);
    }
}