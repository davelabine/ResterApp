import FilterableStudentList from '../components/filterableStudentList/FilterableStudentList';
import * as actions from '../actions/';
import { StoreState } from '../types/index';
import { connect, Dispatch } from 'react-redux';
import { StudentData } from '../types';
import { ResterAppManager } from '../clients/ResterAppManager';

export const resterApp: ResterAppManager = new ResterAppManager();

function fetchStudents(dispatch: Dispatch<actions.StudentAction>) {
  resterApp.fetchStudents()
    .then((students) => dispatch(actions.setStudents(students)))
    .catch((err) => console.log('FilterableStudentList.c - ' + err));
}

function addStudent(dispatch: Dispatch<actions.StudentAction>, student: StudentData) {
  dispatch(actions.addStudent(student));
}

export function mapStateToProps({ studentList, filter  }: StoreState) {
  return {
    students: studentList,
    filter,
  };
}

export function mapDispatchToProps(dispatch: Dispatch<actions.StudentAction>) {
  return {
    onFetchStudents: () => fetchStudents(dispatch),
    onFilterStudents: (filter: string) => dispatch(actions.filterStudents(filter)),
    onAddStudent: (student: StudentData) => addStudent(dispatch, student),
    onUpdateStudent: (student: StudentData) => dispatch(actions.updateStudent(student)),
    onDeleteStudent: (skey: string) => dispatch(actions.deleteStudent(skey))
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(FilterableStudentList as any);
