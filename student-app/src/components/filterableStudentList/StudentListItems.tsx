import * as React from 'react';

import { StudentListItem } from './StudentListItem';
import { StudentData } from './StudentListInterfaces.d';

export interface StudentListItemsProps {
    filter: string;
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
        let studentList = this.filterRows();

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

    private filterRows(): Array<JSX.Element> {
        let filter = this.props.filter;
        let students = this.props.students;
        let rows: Array<JSX.Element> = [];

        students.map((s) => {
            if (s.name.indexOf(filter) === -1) {
                return;
            }

            rows.push(
                <StudentListItem key={s.skey} student={s}/>
            );
        });

        if (!rows.length) {
            rows.push(<tr><td>{STUDENT_LIST_ITEMS_EMPTY}</td></tr>);
        }

        return rows;
    }

    /* Another way to map the list of students...
        if (!students.length) {
            studentList = <tr><td>{STUDENT_LIST_ITEMS_EMPTY}</td></tr>;
        } else {
            studentList = students.map((s) =>
                <StudentListItem key={s.skey} student={s}/>
            );
        }
    */
}
export default StudentListItems;