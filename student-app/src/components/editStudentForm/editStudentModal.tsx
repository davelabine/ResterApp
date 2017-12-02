import * as React from 'react';
import { Button, Modal } from 'react-bootstrap';
import { EditStudentFormBase } from './editStudentFormBase';
import { StudentData } from '../../types';

export interface EditStudentModalProps {
    title: string;
    submitButtonText: string;
    student: StudentData;
    show: boolean;
    onHide: () => void;
    onSubmit: () => void;
    onStudentFormTextChange: (label: string, filter: String) => void;
    onStudentFormFileChange: (file: File) => void;
}

export class EditStudentModal extends React.Component<EditStudentModalProps> {
    
    constructor(props: EditStudentModalProps) {
        super(props);
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
                  student={this.props.student}
                  onFormTextChange={this.props.onStudentFormTextChange}
                  onFormFileChange={this.props.onStudentFormFileChange}
              />
            </Modal.Body>
      
            <Modal.Footer>
              <Button id="close" onClick={this.props.onHide}>Close</Button>
              <Button id="submit" onClick={this.props.onSubmit} bsStyle="primary">
                {this.props.submitButtonText}
              </Button>
            </Modal.Footer>
      
          </Modal>
        </div>
      );
    }
}