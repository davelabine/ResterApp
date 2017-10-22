import FilterableStudentList from '../components/filterableStudentList/FilterableStudentList';
import * as actions from '../actions/';
import { StoreState } from '../types/index';
import { connect, Dispatch } from 'react-redux';

export function mapStateToProps({ studentList }: StoreState) {
  return {
    students: studentList,
  };
}

export function mapDispatchToProps(dispatch: Dispatch<actions.StudentAction>) {
  return {
    onFetchStudents: () => dispatch(actions.fetchStudents()),
  };
}

export default connect(mapStateToProps)(FilterableStudentList);
