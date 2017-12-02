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
    addStudent: StudentData;
    addStudentFilePhoto?: File;
}

export class StudentListFilterForm extends React.Component<StudentListFilterFormProps, StudentListFilterState> {
    constructor(props: StudentListFilterFormProps) {
        super(props);

        this.state = { show: false, addStudent: new StudentData() };

        this.onFormFilterChange = this.onFormFilterChange.bind(this);
        this.onAddStudentTextChange = this.onAddStudentTextChange.bind(this);
        this.onAddStudentFileChange = this.onAddStudentFileChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
        this.onShowModal = this.onShowModal.bind(this);
        this.onHideModal = this.onHideModal.bind(this);
    }

    public onFormFilterChange(e: React.FormEvent<FormControlProps>): void {
        let f = e.currentTarget.value as string;
        this.props.onFilterStudents(f);
    }

    public onAddStudentTextChange(label: string, value: string): void {
        let student = {...this.state.addStudent};
        student[label] = value;
        this.setState( {addStudent: student});
    }

    public onAddStudentFileChange(file: File) {
        console.log(file);
        this.setState( {addStudentFilePhoto: file} );
    }

    public onSubmit() {
        this.props.onAddStudent(this.state.addStudent, this.state.addStudentFilePhoto);
        this.onHideModal();
    }

    public onShowModal() {
        this.setState({ show: true, addStudent: new StudentData() });
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
                    student={this.state.addStudent}
                    show={this.state.show}
                    onHide={this.onHideModal}
                    onSubmit={this.onSubmit}
                    onStudentFormTextChange={this.onAddStudentTextChange}
                    onStudentFormFileChange={this.onAddStudentFileChange}
                />
            </Form>
        );
    }
}

export default StudentListFilterForm;
