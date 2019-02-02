
export class StudentData {
    id: string;
    studentId: string;
    firstName: string;
    lastName: string;
    photoUrl: string;
}

export interface StoreState {
    studentList: Array<StudentData>;
    filter: string;
}