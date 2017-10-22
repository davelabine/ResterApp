import { StudentAction } from '../actions';
import { StoreState } from '../types/index';
import { FETCH_STUDENTS } from '../constants/index';

export function studentReducer(state: StoreState, action: StudentAction): StoreState {
  switch (action.type) {
    case FETCH_STUDENTS:
      console.log('studentReducer!!!');
      return state;
    default:
      return state;
  }
}