export interface StudentPhoto {
    bucketName: string;
    key: string;
}

export class StudentData {
    skey: string;
    id: string;
    name: string;
    photo: {};
}

export interface StoreState {
    studentList: Array<StudentData>;
    filter: string;
}