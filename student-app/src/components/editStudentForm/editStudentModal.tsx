import * as React from 'react';
import { Button, Modal } from 'react-bootstrap';
import { EditStudentFormBase } from './editStudentFormBase';
import { StudentData } from '../../types';

export interface EditStudentModalProps {
    title: string;
    submitButtonText: string;
    initialStudent: StudentData;
    show: boolean;
    onHide: () => void;
    onSubmit: (student: StudentData, photo?: File) => void;
}

export interface EditStudentModalState {
    student: StudentData;
    photo?: File;
}

export class EditStudentModal extends React.Component<EditStudentModalProps, EditStudentModalState> {
    
    constructor(props: EditStudentModalProps) {
        super(props);

        this.state = { student: new StudentData() };

        this.onStudentTextChange = this.onStudentTextChange.bind(this);
        this.onStudentFileChange = this.onStudentFileChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    public componentWillReceiveProps(nextProps: EditStudentModalProps) {
        if (this.props.show !== nextProps.show) {
          this.setState( {student: this.props.initialStudent, photo: undefined} );
        }
    }

    public onStudentTextChange(label: string, value: string): void {
        let student = {...this.state.student};
        student[label] = value;
        this.setState( {student: student});
    }

    public onStudentFileChange(file: File) {
        console.log(file);
        this.setState( {photo: file} );
    }

    public onSubmit() {
        console.log('Submitting student: ' + JSON.stringify(this.state.student));
        this.props.onSubmit(this.state.student, this.state.photo);
    }

    public render() {    
      return (
          <div className="static-modal">
          <Modal
            show={this.props.show}
            onHide={this.props.onHide}
            dialogClassName="custom-modal"
          >
            <Modal.Header>
              <Modal.Title>{this.props.title}</Modal.Title>
            </Modal.Header>
      
            <Modal.Body>
              <EditStudentFormBase
                  student={this.state.student}
                  onFormTextChange={this.onStudentTextChange}
                  onFormFileChange={this.onStudentFileChange}
              />
            </Modal.Body>
      
            <Modal.Footer>
              <Button id="close" onClick={this.props.onHide}>Close</Button>
              <Button id="submit" onClick={this.onSubmit} bsStyle="primary">
                {this.props.submitButtonText}
              </Button>
            </Modal.Footer>
      
          </Modal>
        </div>
      );
    }
}