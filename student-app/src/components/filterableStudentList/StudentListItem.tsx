import * as React from 'react';

import { StudentData } from '../../types';

/* let styles = require('./StudentListItem.css'); */

export interface StudentListItemProps {
    student: StudentData;
}

export function StudentListItem (props: StudentListItemProps) {
        
    let student = props.student;
    return (
        <tr>
            <td>{student.name}</td>
            <td>{student.id}</td>
            <td><a href="http://google.com">view</a> | 
                <a href="http://google.com"> edit</a></td>
        </tr>
    );
}