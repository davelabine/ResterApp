import * as constants from '../constants';
import { StudentData } from '../types/';

export interface SetStudents {
    type: constants.SET_STUDENTS;
    students: Array<StudentData>;
}

export interface FilterStudents {
    type: constants.FILTER_STUDENTS;
    filter: string;
}

export interface AddStudent {
    type: constants.ADD_STUDENT;
    student: StudentData;
}

export interface UpdateStudent {
    type: constants.UPDATE_STUDENT;
    student: StudentData;
}

export interface DeleteStudent {
    type: constants.DELETE_STUDENT;
    id: string;
}

export interface ServerError {
    type: constants.SERVER_ERROR;
    error: string;
}

export type StudentAction = SetStudents | FilterStudents | AddStudent | UpdateStudent | DeleteStudent | ServerError;

export function setStudents(students: Array<StudentData>): SetStudents {
    return {
        type: constants.SET_STUDENTS,
        students
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

export function updateStudent(student: StudentData): UpdateStudent {
    return {
        type: constants.UPDATE_STUDENT,
        student
    };
}

export function deleteStudent(id: string): DeleteStudent {
    return {
        type: constants.DELETE_STUDENT,
        id: id
    };
}
