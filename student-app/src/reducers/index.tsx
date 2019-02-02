import { StudentAction } from '../actions';
import { StoreState, StudentData } from '../types/index';
import * as constants from '../constants/index';

export function studentReducer(state: StoreState, action: StudentAction): StoreState {
  console.log('studentReducer Action - %s', JSON.stringify(action.type));
  switch (action.type) {     
    case constants.SET_STUDENTS:
      return { ...state, studentList: action.students };     
    case constants.FILTER_STUDENTS:
      return { ...state, filter: action.filter };
    case constants.ADD_STUDENT:
      let addList: StudentData[] = [...state.studentList];
      // let addIndex = addList.findIndex(s => s.lastName > action.student.lastName);
      // If no index found just add it to the front
      // if (addIndex === -1) { addIndex = 0; } 
      addList.splice(0, 0, action.student);
      return { ...state, studentList: addList };
    case constants.DELETE_STUDENT:
      let delList: StudentData[] = [...state.studentList];
      const delIndex = delList.findIndex(s => s.id === action.id);
      if (delIndex === -1) { return state; } 
      delList.splice(delIndex, 1);
      return { ...state, studentList: delList };
    case constants.UPDATE_STUDENT:
      let upList: StudentData[] = [...state.studentList];
      const upIndex = upList.findIndex(function (s: StudentData) {
        console.log('s: %s, action: %s', s.id, action.student.id);
        return s.id === action.student.id;
      });
      console.log('UPDATE_STUDENT upIndex - ', upIndex);
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