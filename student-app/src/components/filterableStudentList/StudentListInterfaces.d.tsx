export interface StudentListItemsProps {
    students: Array<StudentData>;
}

export interface StudentPhoto {
    bucketName: string;
    key: string;
}

export interface StudentData {
    skey: string;
    id: string;
    name: string;
    photo: StudentPhoto;
}