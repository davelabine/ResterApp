import * as React from 'react';
import { FormGroup, ControlLabel, FormControl, FormControlProps, Thumbnail } from 'react-bootstrap';
import { StudentData } from '../../types';

export interface EditStudentFormBaseProps {
    student: StudentData;
    onFormTextChange: (label: string, filter: String) => void;
}

export class EditStudentFormBase extends React.Component<EditStudentFormBaseProps> {
    
    constructor(props: EditStudentFormBaseProps) {
        super(props);
        this.handleTextFieldChange = this.handleTextFieldChange.bind(this);
    }

    public handleTextFieldChange(e: React.FormEvent<FormControlProps>): void {
        const label = e.currentTarget.id as string;
        const value = e.currentTarget.value as string;
        this.props.onFormTextChange(label, value);
    }
    
    public render() {
        /* Make sure we set a default value for our form controls 
           Otherwise, React throws a warning that we are switching between uncontrolled and controlled components */
        let student = this.props.student;
        if (!student.name) { student.name = ''; }
        if (!student.id) { student.id = ''; }

        return (
            <form>
                <FormGroup>
                    <ControlLabel>Name:</ControlLabel>
                    <FormControl
                        id="name"
                        type="text"
                        value={student.name}
                        onChange={this.handleTextFieldChange}
                    />
                    <ControlLabel>ID:</ControlLabel>
                    <FormControl
                        id="id"
                        type="text"
                        value={student.id}
                        onChange={this.handleTextFieldChange}
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
}