import { studentReducer } from '../reducers/index';
import { StoreState } from '../types/index';
import * as actions from '../actions/index';
import * as testData from '../testData/index';
import { StudentAction } from '../actions/index';

const initialState =  {
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
        const newState: StoreState = studentReducer(initialState, actions.addStudent(testData.STUDENT_DATA_BILLY));
        expect(newState).toEqual({
            studentList: [...testData.LIST_STUDENT_DATA_TWO, testData.STUDENT_DATA_BILLY],
            filter: ''
        });
    });

    it('should delete students', () => {
        const newState: StoreState = studentReducer(initialState, 
                                                    actions.deleteStudent(testData.STUDENT_DATA_BILLY.skey));
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