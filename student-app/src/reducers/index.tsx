import { StudentAction } from '../actions';
import { StoreState } from '../types/index';
import * as constants from '../constants/index';

export function studentReducer(state: StoreState, action: StudentAction): StoreState {
  console.log('studentReducer!!!');
  switch (action.type) {
    case constants.FETCH_STUDENTS:
      console.log('FETCH_STUDENTS');
      return state;
    case constants.FILTER_STUDENTS:
      console.log('FILTER_STUDENTS');
      return { ...state, filter: action.filter };
    case constants.ADD_STUDENT:
      console.log('ADD_STUDENTS');
      state.studentList.push(action.student);
      return state;
    default:
      return state;
  }
}