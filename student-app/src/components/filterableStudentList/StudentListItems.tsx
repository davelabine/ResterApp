import * as React from 'react';

import { StudentListItem } from './StudentListItem';
import { StudentData } from './StudentListInterfaces.d';

export interface StudentListItemsProps {
    students: Array<StudentData>;
}

export function StudentListItems(props: StudentListItemsProps) {
    
    let students = props.students;
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
                {students.map((s) =>
                    <StudentListItem key={s.skey} student={s}/>
                )}
            </tbody>
        </table>
    );
}
export default StudentListItems;