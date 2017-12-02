import * as React from 'react';
import './FilterableStudentList.css';
import { StudentData } from '../../types';
import { StudentListFilterForm } from './StudentListFilterForm';
import { StudentListItems } from './StudentListItems';
import { Well } from 'react-bootstrap';

export interface Props {
    students: Array<StudentData>;
    filter: string;
    onFetchStudents: () => void;
    onFilterStudents: (filter: String) => void;
    onAddStudent: (student: StudentData, filePhotoUpload: File) => void;
    onUpdateStudent: (student: StudentData, photo: File) => void;
    onDeleteStudent: (skey: string) => void;
}

export class FilterableStudentList extends React.Component<Props, object> {
    
    constructor(props: Props) {
        super(props);
    }

    public componentDidMount() {
        this.props.onFetchStudents();
    }

    public render() {
        return (
        <div className="filterableStudentList">
            <Well>
                <StudentListFilterForm
                    filter={this.props.filter}
                    onFilterStudents={this.props.onFilterStudents}
                    onAddStudent={this.props.onAddStudent}
                />
                <StudentListItems 
                    filter={this.props.filter}
                    students={this.props.students} 
                    onUpdateStudent={this.props.onUpdateStudent}
                    onDeleteStudent={this.props.onDeleteStudent}
                />
            </Well>
        </div>
        );
    }
}
export default FilterableStudentList;