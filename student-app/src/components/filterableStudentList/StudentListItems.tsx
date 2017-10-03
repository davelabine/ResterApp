import * as React from 'react';

import { StudentListItem } from './StudentListItem';
import { StudentData } from './StudentListInterfaces.d';

export interface StudentListItemsProps {
    students: Array<StudentData>;
}

function StudentListItemsBody(props: StudentListItemsProps) {
    let students = props.students;
    if (!students.length) {
        return <tr> No students!</tr>;
    } 

    let studentList = students.map((s) =>
        <StudentListItem key={s.skey} student={s}/>
    );

    /* Ugh.  How to cast this expression to return JSX? */
    return (<div>{studentList}</div>);
}

export function StudentListItems(props: StudentListItemsProps) {
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
                <StudentListItemsBody students={props.students}/>
            </tbody>
        </table>
    );
}
export default StudentListItems;