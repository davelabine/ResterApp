import * as React from 'react';

import { StudentListItem } from './StudentListItem';
import { StudentData } from '../../types';
import { Table } from 'react-bootstrap';
import { EditStudentModal } from '../editStudentForm/editStudentModal';
import * as constants from '../../constants/index';

export interface StudentListItemsProps {
    filter: string;
    students: Array<StudentData>;
    onUpdateStudent: (student: StudentData) => void;
    onDeleteStudent: (skey: string) => void;
}

export interface StudentListItemsState {
    show: boolean;
    updateStudent: StudentData;
}

export class StudentListItems extends React.Component<StudentListItemsProps, StudentListItemsState> {
    
    constructor(props: StudentListItemsProps) {
        super(props);

        this.state = { show: false, updateStudent: new StudentData() };
 
        this.onShowUpdateModal = this.onShowUpdateModal.bind(this);
        this.onUpdateFormTextChange = this.onUpdateFormTextChange.bind(this);
        this.onSubmitUpdateModal = this.onSubmitUpdateModal.bind(this);
        this.onShowModal = this.onShowModal.bind(this);
        this.onHideModal = this.onHideModal.bind(this);
    }

    public onShowUpdateModal(s: StudentData) {
        this.setState({ updateStudent: s});
        this.onShowModal();
    }

    public onUpdateFormTextChange(label: string, value: string): void {
        let student = {...this.state.updateStudent};
        student[label] = value;
        this.setState( {updateStudent: student});
    }

    public onSubmitUpdateModal() {
        this.props.onUpdateStudent(this.state.updateStudent);
        this.onHideModal();
    }

    public onShowModal() {
        this.setState({ show: true});
    }

    public onHideModal() {
        this.setState({ show: false});
    }

    public render() {
        const studentList = this.filterRows();

        return (
            <div>
                <Table striped={true} condensed={true} hover={true}>
                    <thead className="studentListItemsHead">
                    <tr>
                        <th>Name</th>
                        <th>ID</th>
                        <th>URL</th>
                    </tr>
                    </thead>
                    <tbody className="studentListItemsBody">
                        {studentList}
                    </tbody>
                </Table>
                <EditStudentModal 
                    title="Edit Student"
                    submitButtonText="Save"
                    student={this.state.updateStudent}
                    show={this.state.show}
                    onHide={this.onHideModal}
                    onSubmit={this.onSubmitUpdateModal}
                    onStudentFormTextChange={this.onUpdateFormTextChange}
                />
            </div>
        );
    }

    private filterRows(): Array<JSX.Element> {
        const filter = this.props.filter;
        const students = this.props.students;
        const rows: Array<JSX.Element> = [];

        students.map((s) => {
            if (s.name.toLowerCase().indexOf(filter.toLowerCase()) === -1) {
                return;
            }

            rows.push(
                <StudentListItem 
                    key={s.skey} 
                    student={s}
                    onUpdateStudentClick={this.onShowUpdateModal}
                    onDeleteStudent={this.props.onDeleteStudent}
                />
            );
        });

        /* Note: Empty rows could be because of no students in the list
           or we filtered all of the existing students out */ 
        if (!rows.length) {
            rows.push(<tr key="empty"><td colSpan={3}>{constants.STUDENT_LIST_ITEMS_EMPTY}</td></tr>);
        }

        return rows;
    }
}
export default StudentListItems;