import * as React from 'react';
import { Button, Modal } from 'react-bootstrap';
import { EditStudentFormBase } from './editStudentFormBase';
import { StudentData } from '../../types';

export interface AddStudentModalProps {
    studentInput: StudentData;
    show: boolean;
    onHide?: () => void;
}

export class AddStudentModal extends React.Component<AddStudentModalProps> {
    
    constructor(props: AddStudentModalProps) {
        super(props);

        this.handleCloseClick = this.handleCloseClick.bind(this);
    }

    public handleCloseClick(e: React.FormEvent<any>): void {
        if (this.props.onHide) {
          this.props.onHide();
        }
    } 

    public render() {    
      let student = this.props.studentInput;
      return (
          <div className="static-modal">
          <Modal
            show={this.props.show}
            onHide={this.handleCloseClick}
            dialogClassName="custom-modal"
          >
            <Modal.Header>
              <Modal.Title>Modal title</Modal.Title>
            </Modal.Header>
      
            <Modal.Body>
              <EditStudentFormBase
                  student={student}
              />
            </Modal.Body>
      
            <Modal.Footer>
            <Button onClick={this.handleCloseClick}>Close</Button>
              <Button bsStyle="primary">Save changes</Button>
            </Modal.Footer>
      
          </Modal>
        </div>
      );
    }
}