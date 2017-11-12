import * as React from 'react';
import './FilterableStudentList.css';
import { StudentData } from '../../types';
import { StudentListFilterForm } from './StudentListFilterForm';
import { StudentListItems } from './StudentListItems';
import { Well } from 'react-bootstrap';

export interface Props {
    students: Array<StudentData>;
    filter: string;
    onFetchStudents?: () => void;
    onFilterStudents?: (filter: String) => void;
    onAddStudent?: (student: StudentData) => void;
}

export class FilterableStudentList extends React.Component<Props, object> {
    
    constructor(props: Props) {
        super(props);
    }

    public componentDidMount() {
        if (this.props.onFetchStudents) {
            this.props.onFetchStudents();
        }
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
                />
            </Well>
        </div>
        );
    }
}
export default FilterableStudentList;
