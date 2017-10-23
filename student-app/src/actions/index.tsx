import * as constants from '../constants';

export interface FetchStudents {
    type: constants.FETCH_STUDENTS;
}

export interface FilterStudents {
    type: constants.FILTER_STUDENTS;
    filter: string;
}

export interface AddStudent {
    type: constants.ADD_STUDENT;
}

export type StudentAction = FetchStudents | FilterStudents | AddStudent;

export function fetchStudents(): FetchStudents {
    return {
        type: constants.FETCH_STUDENTS
    };
}

export function filterStudents(filter: string): FilterStudents {
    return {
        type: constants.FILTER_STUDENTS,
        filter
    };
}

export function AddStudent(): AddStudent {
    return {
        type: constants.ADD_STUDENT
    };
}