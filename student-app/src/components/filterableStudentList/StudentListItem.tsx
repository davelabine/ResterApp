import * as React from 'react';
import { ButtonGroup, Button, Glyphicon } from 'react-bootstrap';
import { StudentData } from '../../types';
import { EditStudentModal } from '../editStudentForm/editStudentModal';

export interface StudentListItemProps {
    student: StudentData;
    onUpdateStudent: (student: StudentData, photo?: File ) => void;
    onDeleteStudent: (skey: string) => void;
}

export interface StudentListItemState {
    show: boolean;
}

export class StudentListItem extends React.Component<StudentListItemProps, StudentListItemState> {
    
    constructor(props: StudentListItemProps) {
        super(props);

        this.state = { show: false };

        this.onSubmit = this.onSubmit.bind(this);
        this.onShowModal = this.onShowModal.bind(this);
        this.onHideModal = this.onHideModal.bind(this);
        this.handleDeleteClick = this.handleDeleteClick.bind(this);
    }

    public onSubmit(student: StudentData, photo?: File) {
        this.props.onUpdateStudent(student, photo);
        this.onHideModal();
    }

    public onShowModal() {
        this.setState({ show: true });
    }

    public onHideModal() {
        this.setState({ show: false });
    }

    public handleDeleteClick(): void {
        this.props.onDeleteStudent(this.props.student.skey);
    } 

    public render() {
        let student = this.props.student;
        return (
            <tr>
                <td>{student.lastName}</td>
                <td>{student.studentId}</td>
                <td>
                    <ButtonGroup>
                        <Button id="edit" onClick={this.onShowModal}><Glyphicon glyph="pencil"/></Button>
                        <Button id="delete" onClick={this.handleDeleteClick}><Glyphicon glyph="trash"/></Button>
                    </ButtonGroup>
                    <EditStudentModal 
                        title={'Edit ' + this.props.student.lastName}
                        submitButtonText="Save"
                        initialStudent={this.props.student}
                        show={this.state.show}
                        onHide={this.onHideModal}
                        onSubmit={this.onSubmit}
                    />
                </td>
            </tr>
        );
    }
}