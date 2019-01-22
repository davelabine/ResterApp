export interface StudentPhoto {
    bucketName: string;
    key: string;
}

export class StudentData {
    id: string;
    studentId: string;
    lastName: string;
    photo: {};
}

export interface StoreState {
    studentList: Array<StudentData>;
    filter: string;
}