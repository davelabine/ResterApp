import * as React from 'react';

import { StudentListItem } from './StudentListItem';
import { StudentData } from './StudentListInterfaces.d';

export interface StudentListItemsProps {
    filter: string;
    students: Array<StudentData>;
}

/* TODO: There is probably a better way to do this without polluting the global namespace.
   Initial attempt at adding this as a class constant didn't work - could not access from another class */
export const STUDENT_LIST_ITEMS_EMPTY: string = 'No Students!';

export class StudentListItems extends React.Component<StudentListItemsProps, object> {
    
    constructor(props: StudentListItemsProps) {
        super(props);
    }

    public render() {
        const studentList = this.filterRows();

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
        const filter = this.props.filter;
        const students = this.props.students;
        const rows: Array<JSX.Element> = [];

        students.map((s) => {
            if (s.name.toLowerCase().indexOf(filter.toLowerCase()) === -1) {
                return;
            }

            rows.push(
                <StudentListItem key={s.skey} student={s}/>
            );
        });

        /* Note: Empty rows could be because of no students in the list
           or we filtered all of the existing students out */ 
        if (!rows.length) {
            rows.push(<tr key="empty"><td>{STUDENT_LIST_ITEMS_EMPTY}</td></tr>);
        }

        return rows;
    }
}
export default StudentListItems;