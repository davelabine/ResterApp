import { StudentAction } from '../actions';
import { StoreState, StudentData } from '../types/index';
import * as constants from '../constants/index';

export function studentReducer(state: StoreState, action: StudentAction): StoreState {
  console.log('studentReducer Action - ' + JSON.stringify(action.type));
  switch (action.type) {     
    case constants.SET_STUDENTS:
      return { ...state, studentList: action.students };     
    case constants.FILTER_STUDENTS:
      return { ...state, filter: action.filter };
    case constants.ADD_STUDENT:
      let addList: StudentData[] = [...state.studentList];
      /* We need to make sure there is a unique key on the student
         Remove this when we hook up the back end service */
      let addStudent: StudentData = {...action.student as StudentData};
      addStudent.skey = Math.random().toString();
      addList.push(addStudent);
      return { ...state, studentList: addList };
    case constants.DELETE_STUDENT:
      let delList: StudentData[] = [...state.studentList];
      const delIndex = delList.findIndex(s => s.skey === action.skey);
      if (delIndex === -1) { return state; } 
      delList.splice(delIndex, 1);
      return { ...state, studentList: delList };
    case constants.UPDATE_STUDENT:
      let upList: StudentData[] = [...state.studentList];
      const upIndex = upList.findIndex(s => s.skey === action.student.skey);
      if (upIndex === -1) { return state; } 
      upList.splice(upIndex, 1, action.student);
      return { ...state, studentList: upList };    
    case constants.SERVER_ERROR:
      return state;
    default:
      console.warn('Unhandled action!');
      return state;
  }
}