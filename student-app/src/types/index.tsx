export interface StudentPhoto {
    bucketName: string;
    key: string;
}

export interface StudentData {
    skey: string;
    id: string;
    name: string;
    photo: {};
}

export interface StoreState {
    studentList: Array<StudentData>;
    filter: string;
}