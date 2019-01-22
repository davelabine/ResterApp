
import * as actions from '../actions/';
import * as constants from '../constants';
import { StudentData } from '../types/';
import * as testData from '../testData/index';

describe('actions', () => {

  it('should create an action to set students ', () => {
    const students: StudentData[] = testData.LIST_STUDENT_DATA_TWO;
    const expectedAction = {
        type: constants.SET_STUDENTS,
        students
    };
    expect(actions.setStudents(students)).toEqual(expectedAction);
  });

  it('should create an action to filter students ', () => {
    const filter: string = 'afilter';
    const expectedAction = {
        type: constants.FILTER_STUDENTS,
        filter
    };
    expect(actions.filterStudents(filter)).toEqual(expectedAction);
  });

  it('should create an action to add a student ', () => {
    const student: StudentData = testData.STUDENT_DATA_BILLY;
    const expectedAction = {
        type: constants.ADD_STUDENT,
        student
    };
    expect(actions.addStudent(student)).toEqual(expectedAction);
  });

  it('should create an action to update a student ', () => {
    const student: StudentData = testData.STUDENT_DATA_BILLY;
    const expectedAction = {
        type: constants.UPDATE_STUDENT,
        student
    };
    expect(actions.updateStudent(student)).toEqual(expectedAction);
  });
  
  it('should create an action to delete a student ', () => {
    const id: string = testData.STUDENT_DATA_BILLY.id;
    const expectedAction = {
        type: constants.DELETE_STUDENT,
        id
    };
    expect(actions.deleteStudent(id)).toEqual(expectedAction);
  });

});
