import * as React from 'react';

import { StudentListItem } from './StudentListItem';
import { StudentData } from './StudentListInterfaces.d';

export interface StudentListItemsProps {
    students: Array<StudentData>;
}

export function StudentListItems(props: StudentListItemsProps) {
    
    let students = props.students;
    return (
        <table className="table table-striped table-sm table-hover">
            <thead>
              <tr>
                <th>Name</th>
                <th>ID</th>
                <th>URL</th>
              </tr>
            </thead>
            <tbody>
                {students.map((s) =>
                    <StudentListItem key={s.skey} student={s}/>
                )}
            </tbody>
        </table>
    );
}