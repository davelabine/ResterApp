import { studentReducer } from '../reducers/index';
import { StoreState, StudentData } from '../types/index';
import * as actions from '../actions/index';
import * as testData from '../testData/index';
import { StudentAction } from '../actions/index';

const initialState: StoreState =  {
    studentList: testData.LIST_STUDENT_DATA_TWO,
    filter: '',
};

describe('studentReducer', () => {
    it ('should handle undefined actions by returning current state', () => {
        const newState: StoreState = studentReducer(initialState, {} as StudentAction);
        expect(newState).toEqual({
            studentList: testData.LIST_STUDENT_DATA_TWO,
            filter: ''
        });
    });
    
    it('should set students', () => {
        const newState: StoreState = studentReducer(initialState, actions.setStudents(testData.LIST_STUDENT_DATA_BIG));
        expect(newState).toEqual({
            studentList: testData.LIST_STUDENT_DATA_BIG,
            filter: ''
      });
    });

    it('should filter students', () => {
        const newState: StoreState = studentReducer(initialState, actions.filterStudents('abc'));
        expect(newState).toEqual({
            studentList: testData.LIST_STUDENT_DATA_TWO,
            filter: 'abc'
        });
    });

    it('should add students', () => {
        const state: StoreState =  {
            studentList: [],
            filter: '',
        };
        const addStudent: StudentData = {
            studentId: '1234',
            lastName: 'Bobby',
            id: '',
            photo: {}
        };
        const newState: StoreState = studentReducer(state, actions.addStudent(addStudent));
        /* We expect the skey to be defined by the reducer, but everything else should be the same */
        addStudent.id = newState.studentList[0].id;
        state.studentList.push(addStudent);
        expect(newState).toEqual(state);
    });

    it('should delete students', () => {
        const newState: StoreState = studentReducer(initialState, 
                                                    actions.deleteStudent(testData.STUDENT_DATA_BILLY.id));
        expect(newState.studentList.length).toEqual(1);
    });

    it('should skip deleting students who are not in its studentList', () => {
        const newState: StoreState = studentReducer(initialState, 
                                                    actions.deleteStudent('FAKE_KEY'));
        expect(newState.studentList.length).toEqual(2);
    });

    it('should update students', () => {
        const state =  {
            studentList: [testData.STUDENT_DATA_BILLY],
            filter: '',
        };
        const newState: StoreState = studentReducer(state, 
                                                    actions.updateStudent(testData.STUDENT_DATA_BILLY_UPDATE));
        expect(newState).toEqual({
            studentList: [testData.STUDENT_DATA_BILLY_UPDATE],
            filter: ''
        });
    });

    it('should skip updating students who are not in its studentList', () => {
        const newState: StoreState = studentReducer(initialState, 
                                                    actions.updateStudent(testData.STUDENT_DATA_FAKE));
        expect(newState).toEqual({
            studentList: testData.LIST_STUDENT_DATA_TWO,
            filter: ''
        });
    });
});