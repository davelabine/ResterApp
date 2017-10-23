import FilterableStudentList from '../components/filterableStudentList/FilterableStudentList';
import * as actions from '../actions/';
import { StoreState } from '../types/index';
import { connect, Dispatch } from 'react-redux';

export function mapStateToProps({ studentList, filter  }: StoreState) {
  return {
    students: studentList,
    filter,
  };
}

export function mapDispatchToProps(dispatch: Dispatch<actions.StudentAction>) {
  return {
    onFetchStudents: () => dispatch(actions.fetchStudents()),
    onFilterStudents: (filter: string) => dispatch(actions.filterStudents(filter)),
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(FilterableStudentList as any);
