import { StudentData } from '../types'; 

export const RESTERAPP_BASE_URL: string = 'http://resterapp.lvictories.us:8080/api';
export const RESTERAPP_STUDENTS_URL: string = RESTERAPP_BASE_URL + '/students/';

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

}