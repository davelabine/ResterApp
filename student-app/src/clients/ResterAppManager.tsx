import { StudentData } from '../types'; 

/* export const RESTERAPP_BASE_URL: string = 'http://resterapp.lvictories.us:8080/api';
export const RESTERAPP_BASE_URL: string = 'http://0.0.0.0:8080/service/api'; */
export const RESTERAPP_BASE_URL: string = 'http://localhost:8080/api/v1';
console.log( 'RESTERAPP_BASE_URL = ' + RESTERAPP_BASE_URL);
export const RESTERAPP_STUDENTS_URL: string = RESTERAPP_BASE_URL + '/student';

export class ResterAppManager {
      
    public async fetchStudents(): Promise<Array<StudentData>> {
        const myHeaders = new Headers();
        const requestInit: RequestInit = { method: 'GET',
                            headers: myHeaders,
                            mode: 'cors',
                            cache: 'default' };

        let students: Array<StudentData> = []; 
        const url = RESTERAPP_STUDENTS_URL;
        console.log('fetchStudents... ' + url);              
        let response: Response = await fetch(url, requestInit);
        console.log('fetchStudents response: ' + response.status);
        let contentType = response.headers.get('content-type');
        if ((!contentType) || (!contentType.includes('application/json')) )  {
            throw Error('Incorrect contentType.  Expected application/json and received' 
                            + contentType);
        }

        let json = await response.json();
        students = json.students;
        console.log(JSON.stringify(students));
        return students;
    }

    public async createStudent(s: StudentData, photo?: File): Promise<StudentData> {

        const myHeaders = new Headers();
        /* DO NOT append any Content-Type headers.  The browser will do it for you.
           Yes, you heard right.  
        myHeaders.append('Content-Type', undefined);
        myHeaders.append('Accept-Encoding', 'multipart/form-data');
        */

        var formData = new FormData();
        formData.append('lastName', s.lastName);
        formData.append('firstName', s.firstName);
        formData.append('studentId', s.studentId);
        if (photo) {
            formData.append('photo', photo, photo.name);
        }

        const requestInit: RequestInit = {
            body: formData,
            headers: myHeaders,
            method: 'post',
        };

        const url = RESTERAPP_STUDENTS_URL;
        console.log('createStudent... ' + url);    
        let response: Response = await fetch(url, requestInit);

        let contentType = response.headers.get('content-type');
        if ((!contentType) || (!contentType.includes('application/json')) )  {
            throw Error('Incorrect contentType.  Expected application/json and received' 
                            + contentType);
        }
        console.log(response.headers);
        let id = response.headers.get('id');
        if (!id) { id = ''; }
        console.log('createStudents response: ' + response.status + ', id:' + id);
        let json = await response.json();
        console.log(JSON.stringify(json));
        if (!json.student) {
            throw Error('No student data returned.');
        }
        return json.student;
    }

    public async updateStudent(s: StudentData, photo?: File): Promise<StudentData> {
        const myHeaders = new Headers();
        /* DO NOT append any Content-Type headers.  The browser will do it for you.
           Yes, you heard right.  
        myHeaders.append('Content-Type', undefined);
        myHeaders.append('Accept-Encoding', 'multipart/form-data');
        */

        var formData = new FormData();
        formData.append('lastName', s.lastName);
        formData.append('firstName', s.firstName);
        formData.append('studentId', s.studentId);
        if (photo) {
            formData.append('photo', photo, photo.name);
        }

        const requestInit: RequestInit = {
            body: formData,
            headers: myHeaders,
            method: 'put',
        };
          
        const url = RESTERAPP_STUDENTS_URL + '/' + s.id;
        console.log('updateStudent fetch... ' + url);
        let response: Response = await fetch(url, requestInit);
        console.log('updateStudent response: ' + response.status);
        let json = await response.json();
        console.log(JSON.stringify(json));
        if (!json.student) {
            throw Error('No student data returned.');
        }
        return json.student;

    }

    public async deleteStudent(id: string): Promise<void> {
        var myHeaders = new Headers();
        const requestInit: RequestInit = {
            headers: myHeaders,
            method: 'DELETE',
        };
                 
        const url = RESTERAPP_STUDENTS_URL + '/' + id;
        console.log('deleteStudent fetch... ' + url);
        let response: Response = await fetch(url, requestInit);
        console.log('deleteStudent response: ' + response.status);
    }

    /*
    private handleErrors(response): function {
        if (!response.ok) {
            throw Error(response.statusText);
        }
        return response;
    }
    */
}