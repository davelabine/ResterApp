import * as React from 'react';
import { FormGroup, ControlLabel, FormControl, Thumbnail } from 'react-bootstrap';
import { StudentData } from '../../types';

export interface EditStudentFormBaseProps {
    student: StudentData;
}

export function EditStudentFormBase (props: EditStudentFormBaseProps) {
    let student = props.student;
    return (
        <form>
            <FormGroup controlId="formEditStudent">
                <ControlLabel>Name:</ControlLabel>
                <FormControl
                    type="text"
                    value={student.name}
                />
                <ControlLabel>ID:</ControlLabel>
                <FormControl
                    type="text"
                    value={student.id}
                />
                <ControlLabel>Photo:</ControlLabel>
                <FormControl
                    type="file"
                />
                <Thumbnail 
                    href="#" 
                    alt="171x180" 
                    src="https://static.pexels.com/photos/104827/cat-pet-animal-domestic-104827.jpeg" 
                />
            </FormGroup>
        </form>
    );
}