import * as React from 'react';
import './FilterableStudentList.css';
import { Form, FormGroup, ControlLabel, FormControl, FormControlProps, Button } from 'react-bootstrap';

export interface StudentListFilterFormProps {
    filter: string;
    onFilterStudents?: (filter: String) => void;
    onAddStudentClick?: () => void;
}

export class StudentListFilterForm extends React.Component<StudentListFilterFormProps> {
    constructor(props: StudentListFilterFormProps) {
        super(props);
        this.handleFormFilterChange = this.handleFormFilterChange.bind(this);
    }

    public handleFormFilterChange(e: React.FormEvent<FormControlProps>): void {
        let f = e.currentTarget.value as string;
        if (this.props.onFilterStudents) {
            this.props.onFilterStudents(f);
        }
    }

    public render() {
        return (
            <Form inline={true}>
                <FormGroup controlId="formBasicText">
                    <ControlLabel>Search Last Name:</ControlLabel>
                    <FormControl
                        type="text"
                        onChange={this.handleFormFilterChange}
                        value={this.props.filter}
                    />
                    <Button 
                        className="pull-right"
                        type="button"
                        bsStyle="default"
                        onClick={this.props.onAddStudentClick}
                    >Add Student
                    </Button>
                </FormGroup>
            </Form>
        );
    }
}

export default StudentListFilterForm;
