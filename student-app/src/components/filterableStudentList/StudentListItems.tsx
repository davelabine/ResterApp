import * as React from 'react';

import { StudentListItem } from './StudentListItem';
import { StudentData } from '../../types';
import { Table } from 'react-bootstrap';
import * as constants from '../../constants/index';

export interface StudentListItemsProps {
    filter: string;
    students: Array<StudentData>;
    onUpdateStudent: (student: StudentData, photo?: File ) => void;
    onDeleteStudent: (skey: string) => void;
}

export class StudentListItems extends React.Component<StudentListItemsProps> {
    
    constructor(props: StudentListItemsProps) {
        super(props);
    }

    public render() {
        const studentList = this.filterRows();

        return (
            <div>
                <Table striped={true} condensed={true} hover={true}>
                    <thead className="studentListItemsHead">
                    <tr>
                        <th>Last Name</th>
                        <th>First Name</th>
                        <th>ID</th>
                        <th>URL</th>
                    </tr>
                    </thead>
                    <tbody className="studentListItemsBody">
                        {studentList}
                    </tbody>
                </Table>
            </div>
        );
    }

    private filterRows(): Array<JSX.Element> {
        const filter = this.props.filter;
        const students = this.props.students;
        const rows: Array<JSX.Element> = [];

        students.map((s) => {
            if (s.lastName.toLowerCase().indexOf(filter.toLowerCase()) === -1) {
                return;
            }

            rows.push(
                <StudentListItem 
                    key={s.id} 
                    student={s}
                    onUpdateStudent={this.props.onUpdateStudent}
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