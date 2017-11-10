import * as React from 'react';
import './FilterableStudentList.css';
import { Form, FormGroup, ControlLabel, FormControl, FormControlProps, Button } from 'react-bootstrap';
import { AddStudentModal } from '../editStudentForm/addStudentModal';
import { StudentData } from '../../types';

export interface StudentListFilterFormProps {
    filter: string;
    onFilterStudents?: (filter: String) => void;
    onAddStudent?: () => void;
}

export class StudentListFilterForm extends React.Component<StudentListFilterFormProps, any> {
    constructor(props: StudentListFilterFormProps) {
        super(props);

        this.state = { show: false };

        this.handleFormFilterChange = this.handleFormFilterChange.bind(this);
        this.onShowAddStudent = this.onShowAddStudent.bind(this);
        this.onHideAddStudent = this.onHideAddStudent.bind(this);
    }

    public handleFormFilterChange(e: React.FormEvent<FormControlProps>): void {
        let f = e.currentTarget.value as string;
        if (this.props.onFilterStudents) {
            this.props.onFilterStudents(f);
        }
    }

    public onShowAddStudent() {
        this.setState({ show: true});
    }

    public onHideAddStudent() {
        this.setState({ show: false});
    }

    public render() {
        const student = new StudentData();
        return (
            <Form inline={true}>
                <FormGroup controlId="formFilterStudents">
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
                        onClick={this.onShowAddStudent}
                    >Add Student
                    </Button>
                </FormGroup>
                <AddStudentModal 
                    studentInput={student}
                    show={this.state.show}
                    onHide={this.onHideAddStudent}
                />
            </Form>
        );
    }
}

export default StudentListFilterForm;
