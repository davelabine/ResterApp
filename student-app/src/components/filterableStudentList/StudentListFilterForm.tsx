import * as React from 'react';
import './FilterableStudentList.css';
import { Form, FormGroup, ControlLabel, FormControl, FormControlProps, Button } from 'react-bootstrap';
import { EditStudentModal } from '../editStudentForm/editStudentModal';
import { StudentData } from '../../types';

export interface StudentListFilterFormProps {
    filter: string;
    onFilterStudents?: (filter: String) => void;
    onAddStudent?: (student: StudentData) => void;
}

export interface StudentListFilterState {
    show: boolean;
}

export class StudentListFilterForm extends React.Component<StudentListFilterFormProps, StudentListFilterState> {
    constructor(props: StudentListFilterFormProps) {
        super(props);

        this.state = { show: false };

        this.handleFormFilterChange = this.handleFormFilterChange.bind(this);
        this.onShowModal = this.onShowModal.bind(this);
        this.onHideModal = this.onHideModal.bind(this);
    }

    public handleFormFilterChange(e: React.FormEvent<FormControlProps>): void {
        let f = e.currentTarget.value as string;
        if (this.props.onFilterStudents) {
            this.props.onFilterStudents(f);
        }
    }

    public onShowModal() {
        this.setState({ show: true});
    }

    public onHideModal() {
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
                        onClick={this.onShowModal}
                    >Add Student
                    </Button>
                </FormGroup>
                <EditStudentModal 
                    title="Add Student"
                    submitButtonText="Add Student"
                    initialStudent={student}
                    show={this.state.show}
                    onHide={this.onHideModal}
                    onSubmit={this.props.onAddStudent}
                />
            </Form>
        );
    }
}

export default StudentListFilterForm;
