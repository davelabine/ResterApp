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
      let list: StudentData[] = [...state.studentList];
      list.push(action.student);
      return { ...state, studentList: list };
    case constants.DELETE_STUDENT:
      return state;
    case constants.SERVER_ERROR:
      return state;
    default:
      console.warn('Unhandled action!');
      return state;
  }
}