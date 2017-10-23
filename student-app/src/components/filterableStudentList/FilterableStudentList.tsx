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
}

export class FilterableStudentList extends React.Component<Props, object> {
    
    constructor(props: Props) {
        super(props);
        this.handleFormFilterChange = this.handleFormFilterChange.bind(this);
    }

    public render() {
        return (
        <div className="filterableStudentList">
            <div className="well">
            <StudentListFilterForm
                filter={this.props.filter}
                onFilterChange={this.handleFormFilterChange}
            />
            <StudentListItems 
                filter={this.props.filter}
                students={this.props.students} 
            />
            </div>
        </div>
        );
    }

    public handleFormFilterChange(e: React.FormEvent<HTMLInputElement>): void {
        console.log('need to check handleChange() name! ' + e.currentTarget.name + ':' + e.currentTarget.value);
        let f = e.currentTarget.value;
        if (this.props.onFilterStudents) {
            console.log('calling onFilterStudents');
            this.props.onFilterStudents(f);
        }

    }

}
export default FilterableStudentList;
