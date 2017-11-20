import * as React from 'react';
import { ButtonGroup, Button, Glyphicon } from 'react-bootstrap';
import { StudentData } from '../../types';

export interface StudentListItemProps {
    student: StudentData;
    onDeleteStudent: (skey: string) => void;
}

export class StudentListItem extends React.Component<StudentListItemProps, object> {
    
    constructor(props: StudentListItemProps) {
        super(props);
        this.handleDeleteClick = this.handleDeleteClick.bind(this);
    }

    public handleDeleteClick(): void {
        this.props.onDeleteStudent(this.props.student.skey);
    } 

    public render() {
        let student = this.props.student;
        return (
            <tr>
                <td>{student.name}</td>
                <td>{student.id}</td>
                <td>
                    <ButtonGroup>
                        <Button id="edit"><Glyphicon glyph="pencil"/></Button>
                        <Button id="delete" onClick={this.handleDeleteClick}><Glyphicon glyph="trash"/></Button>
                    </ButtonGroup>
                </td>
            </tr>
        );
    }
}