import * as React from 'react';

import { StudentListItem } from './StudentListItem';
import { StudentData } from './StudentListInterfaces.d';

export interface StudentListItemsProps {
    students: Array<StudentData>;
}

/* TODO: There is probably a better way to do this without polluting the global namespace.
   Initial attempt at adding this as a class constant didn't work - could not access from another class */
export const STUDENT_LIST_ITEMS_EMPTY = 'No Students!';

export class StudentListItems extends React.Component<StudentListItemsProps, object> {

    constructor(props: StudentListItemsProps) {
        super(props);
    }

    public render() {
        let students = this.props.students;
        let studentList;

        if (!students.length) {
            studentList = <tr>{STUDENT_LIST_ITEMS_EMPTY}</tr>;
        } else {
            studentList = students.map((s) =>
                <StudentListItem key={s.skey} student={s}/>
            );
        }

        return (
            <table className="studentListItems table table-striped table-sm table-hover">
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
            </table>
        );
    }
}
export default StudentListItems;