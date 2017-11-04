import { StudentAction } from '../actions';
import { StoreState } from '../types/index';
import * as constants from '../constants/index';

export function studentReducer(state: StoreState, action: StudentAction): StoreState {
  console.log('studentReducer Action - ' + action.type);
  switch (action.type) {     
    case constants.SET_STUDENTS:
      return { ...state, studentList: action.students };     
    case constants.FILTER_STUDENTS:
      return { ...state, filter: action.filter };
    case constants.ADD_STUDENT:
      state.studentList.push(action.student);
      return state;
    case constants.DELETE_STUDENT:
      return state;
    case constants.SERVER_ERROR:
      return state;
    default:
      console.warn('Unhandled action! - ' + action);
      return state;
  }
}