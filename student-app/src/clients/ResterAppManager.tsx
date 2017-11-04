import { StudentData } from '../types'; 

export const RESTERAPP_BASE_URL: string = 'http://resterapp.lvictories.us:8080/api/students/';

export class ResterAppManager {
      
    public async fetchStudents(): Promise<any> {
        console.log('ResterAppManager() - fetching students');
        var myHeaders = new Headers();
        let requestInit: RequestInit = { method: 'GET',
                            headers: myHeaders,
                            mode: 'cors',
                            cache: 'default' };

        let students: Array<StudentData> = [];                    
        let response: Response = await fetch(RESTERAPP_BASE_URL, requestInit);
        let contentType = response.headers.get('content-type');
        if (contentType && contentType.includes('application/json')) {
            students = await response.json();
            /*
            for (let student of students) {
                console.log(student.name + ', ' + student.id + ', ' + student.skey);
            }
            */
            return students;
        }
        console.log('ResterAppManager() - Incorrect contentType.  Expected application/json and received' 
                        + contentType);
        return students;
    }

}