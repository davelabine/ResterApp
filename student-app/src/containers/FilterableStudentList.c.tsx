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
    .catch((err) => console.log('fetchStudents error - ' + err));
}

function deleteStudent(dispatch: Dispatch<actions.StudentAction>, skey: string) {
  resterApp.deleteStudent(skey)
    .then(() => dispatch(actions.deleteStudent(skey)))
    .catch((err) => console.log('deleteStudent Fetch error- ' + err));
}

function updateStudent(dispatch: Dispatch<actions.StudentAction>, student: StudentData, photo?: File) {
  resterApp.updateStudent(student, photo)
    .then(() => dispatch(actions.updateStudent(student)))
    .catch((err) => console.log('updateStudent Fetch error - ' + err));
}

function addStudent(dispatch: Dispatch<actions.StudentAction>, student: StudentData, photo?: File) {
  resterApp.createStudent(student, photo)
    .then((skey) => {
      student.skey = skey;
      console.log('createStudent - ' + JSON.stringify(student));
      dispatch(actions.addStudent(student));
    })
    .catch((err) => console.log('addStudent Fetch error - ' + err));

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
    onAddStudent: (student: StudentData, photo: File) => addStudent(dispatch, student, photo),
    onUpdateStudent: (student: StudentData, photo: File) => updateStudent(dispatch, student, photo),
    onDeleteStudent: (skey: string) => deleteStudent(dispatch, skey)
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(FilterableStudentList as any);
