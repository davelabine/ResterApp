import * as React from 'react';
import './FilterableStudentList.css';
import { Form, FormGroup, ControlLabel, FormControl, FormControlProps, Button } from 'react-bootstrap';
import { EditStudentModal } from '../editStudentForm/editStudentModal';
import { StudentData } from '../../types';

export interface StudentListFilterFormProps {
    filter: string;
    onFilterStudents: (filter: String) => void;
    onAddStudent: (student: StudentData, filePhotoUpload?: File) => void;
}

export interface StudentListFilterState {
    show: boolean;
}

export class StudentListFilterForm extends React.Component<StudentListFilterFormProps, StudentListFilterState> {
    constructor(props: StudentListFilterFormProps) {
        super(props);

        this.state = { show: false };

        this.onFormFilterChange = this.onFormFilterChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
        this.onShowModal = this.onShowModal.bind(this);
        this.onHideModal = this.onHideModal.bind(this);
    }

    public onFormFilterChange(e: React.FormEvent<FormControlProps>): void {
        let f = e.currentTarget.value as string;
        this.props.onFilterStudents(f);
    }

    public onSubmit(student: StudentData, photo?: File) {
        this.props.onAddStudent(student, photo);
        this.onHideModal();
    }

    public onShowModal() {
        this.setState({ show: true });
    }

    public onHideModal() {
        this.setState({ show: false, });
    }

    public render() {
        return (
            <Form inline={true}>
                <FormGroup controlId="formFilterStudents">
                    <ControlLabel>Search Last Name:</ControlLabel>
                    <FormControl
                        type="text"
                        onChange={this.onFormFilterChange}
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
                    initialStudent={new StudentData()}
                    show={this.state.show}
                    onHide={this.onHideModal}
                    onSubmit={this.onSubmit}
                />
            </Form>
        );
    }
}

export default StudentListFilterForm;
