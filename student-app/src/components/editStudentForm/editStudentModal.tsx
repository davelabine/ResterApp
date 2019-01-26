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
        this.onStudentTextValidate = this.onStudentTextValidate.bind(this);
        this.onStudentFileChange = this.onStudentFileChange.bind(this);
        this.getValidationState = this.getValidationState.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
        this.validateForm = this.validateForm.bind(this);
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

    public onStudentTextValidate(label: string): 'success' | 'warning' | 'error' {
        console.log('validating label - ' + label);

        let value = this.state.student[label];
        console.log('value is: ' + value);
        if ((value.length > 0) && (value.length < 100) ) { 
            // console.log('return success');
            return 'success'; 
        }
        console.log('return error');
        return 'error';
    }

    public getValidationState(): 'success' | 'warning' | 'error' {
        return this.validateForm();
    }

    public onSubmit() {
        if (this.validateForm() === 'error') {
            console.log('Form is not valid - block onSubmit()');
            return;
        }
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
                  onStudentTextValidate={this.onStudentTextValidate}
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

    private validateForm(): 'success' | 'warning' | 'error' {
        if ( this.onStudentTextValidate('lastName') === 'success' &&
             this.onStudentTextValidate('lastName') === 'success' &&
             this.onStudentTextValidate('studentId') === 'success' ) {
            return 'success'; 
        }
        return 'error'; 
    }
}