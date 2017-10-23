import * as constants from '../constants';
import { StudentData } from '../types/';

export interface FetchStudents {
    type: constants.FETCH_STUDENTS;
}

export interface FilterStudents {
    type: constants.FILTER_STUDENTS;
    filter: string;
}

export interface AddStudent {
    type: constants.ADD_STUDENT;
    student: StudentData;
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

export function addStudent(student: StudentData): AddStudent {
    return {
        type: constants.ADD_STUDENT,
        student
    };
}