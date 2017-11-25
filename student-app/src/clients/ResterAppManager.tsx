import { StudentData } from '../types'; 

/* export const RESTERAPP_BASE_URL: string = 'http://resterapp.lvictories.us:8080/api';*/
export const RESTERAPP_BASE_URL: string = 'http://0.0.0.0:8080/service/api';
export const RESTERAPP_STUDENTS_URL: string = RESTERAPP_BASE_URL + '/students/';
export const RESTERAPP_STUDENTS_CREATE_URL: string = RESTERAPP_STUDENTS_URL + '/create';

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

    public async createStudent(s: StudentData): Promise<void> {
        console.log('ResterAppManager() - create student');
        const myHeaders = new Headers();
        myHeaders.append('Content-Type', 'application/x-www-form-urlencoded');

        const requestInit: RequestInit = {
            body: 'email=test@example.com&password=pw',
            headers: myHeaders,
            method: 'post',
        };
                 
        let response: Response = await fetch(RESTERAPP_STUDENTS_CREATE_URL, requestInit);
        console.log(JSON.stringify(response));
    }

    public async deleteStudent(skey: string): Promise<string> {
        console.log('deleteStudent, skey: ' + skey);

        var myHeaders = new Headers();
        const requestInit: RequestInit = {
            headers: myHeaders,
            method: 'DELETE',
        };
                 
        const url = RESTERAPP_STUDENTS_URL + skey;
        let response: Response = await fetch(url, requestInit);
        console.log('deleteStudent response: ' + response.status);
        return skey;
    }
}