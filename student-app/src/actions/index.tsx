import * as constants from '../constants';

export interface FetchStudents {
    type: constants.FETCH_STUDENTS;
}

export interface AddStudent {
    type: constants.ADD_STUDENT;
}

export type StudentAction = FetchStudents | AddStudent;

export function fetchStudents(): FetchStudents {
    return {
        type: constants.FETCH_STUDENTS
    };
}

export function AddStudent(): AddStudent {
    return {
        type: constants.ADD_STUDENT
    };
}