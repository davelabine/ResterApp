import * as React from 'react';
import './FilterableStudentList.css';
import { StudentData } from '../../types';
import { StudentListFilterForm } from './StudentListFilterForm';
import { StudentListItems } from './StudentListItems';

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
        this.handleAddStudentClick = this.handleAddStudentClick.bind(this);
    }

    public componentDidMount() {
        if (this.props.onFetchStudents) {
            this.props.onFetchStudents();
        }
    }

    public render() {
        return (
        <div className="filterableStudentList">
            <div className="well">
            <StudentListFilterForm
                filter={this.props.filter}
                onFilterStudents={this.props.onFilterStudents}
                onAddStudentClick={this.handleAddStudentClick}
            />
            <StudentListItems 
                filter={this.props.filter}
                students={this.props.students} 
            />
            </div>
        </div>
        );
    }

    public handleAddStudentClick() {
        console.log('handleAddStudentClick()');
        if (!this.props.onAddStudent) {
            return;
        }
        
        let student: StudentData = {
            skey : 'abc',
            id : '123',
            name : 'Mickey Mouse',
            photo : {},
        };
        this.props.onAddStudent(student);
    }

}
export default FilterableStudentList;
