import * as React from 'react';
import { FormGroup, ControlLabel, FormControl, FormControlProps, Thumbnail } from 'react-bootstrap';
import { StudentData } from '../../types';

export interface EditStudentFormBaseProps {
    student: StudentData;
    onFormTextChange?: (label: string, filter: String) => void;
}

export class EditStudentFormBase extends React.Component<EditStudentFormBaseProps> {
    
    constructor(props: EditStudentFormBaseProps) {
        super(props);
        this.handleTextFieldChange = this.handleTextFieldChange.bind(this);
    }

    public handleTextFieldChange(e: React.FormEvent<FormControlProps>): void {
        const label = e.currentTarget.id as string;
        const value = e.currentTarget.value as string;
        if (this.props.onFormTextChange) {
            this.props.onFormTextChange(label, value);
        }
    }
    
    public render() {
        let student = this.props.student;
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