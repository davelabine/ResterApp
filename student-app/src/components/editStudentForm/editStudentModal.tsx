import * as React from 'react';
import { Button, Modal } from 'react-bootstrap';
import { EditStudentFormBase } from './editStudentFormBase';
import { StudentData } from '../../types';

export interface EditStudentModalProps {
    title: string;
    submitButtonText: string;
    initialStudent: StudentData;
    show: boolean;
    onHide?: () => void;
    onSubmit?: (student: StudentData) => void;
}

export interface EditStudentModalState {
    submitStudent: StudentData;
}

export class EditStudentModal extends React.Component<EditStudentModalProps, EditStudentModalState> {
    
    constructor(props: EditStudentModalProps) {
        super(props);
        this.state =  { submitStudent: this.props.initialStudent};
        this.handleCloseClick = this.handleCloseClick.bind(this);
        this.handleSubmitClick = this.handleSubmitClick.bind(this);
        this.handleFormTextChange = this.handleFormTextChange.bind(this);
    }

    public handleCloseClick(): void {
        if (this.props.onHide) {
          this.props.onHide();
        }
    } 

    public handleSubmitClick(): void {
      if ( (this.props.onHide) && (this.props.onSubmit) ) {
        /* We need to add a key to this submission, but setState copies asynchronously
           So just copy the state manually and set the key on that object */
        const student: StudentData = {...this.state.submitStudent};
        student.skey = Math.random().toString();
        console.log('Adding student: ', JSON.stringify(student));
        this.props.onSubmit(student);
        this.props.onHide();
      }
    } 

    public handleFormTextChange(label: string, value: string): void {
      let student = {...this.state.submitStudent};
      student[label] = value;
      this.setState( {submitStudent: student});
    }

    public render() {    
      let student = this.props.initialStudent;
      return (
          <div className="static-modal">
          <Modal
            show={this.props.show}
            onHide={this.handleCloseClick}
            dialogClassName="custom-modal"
          >
            <Modal.Header>
              <Modal.Title>{this.props.title}</Modal.Title>
            </Modal.Header>
      
            <Modal.Body>
              <EditStudentFormBase
                  student={student}
                  onFormTextChange={this.handleFormTextChange}
              />
            </Modal.Body>
      
            <Modal.Footer>
              <Button onClick={this.handleCloseClick}>Close</Button>
              <Button onClick={this.handleSubmitClick} bsStyle="primary">{this.props.submitButtonText}</Button>
            </Modal.Footer>
      
          </Modal>
        </div>
      );
    }
}