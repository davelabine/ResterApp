import * as React from 'react';
import './FilterableStudentList.css';
import { StudentData } from './StudentListInterfaces.d';
import { StudentListFilterForm } from './StudentListFilterForm';
import { StudentListItems } from './StudentListItems';

export interface FilterableStudentListProps {
    students: Array<StudentData>;
}

export class FilterableStudentList extends React.Component<FilterableStudentListProps, any> {
    
    constructor(props: FilterableStudentListProps) {
        super(props);
        this.handleChange = this.handleChange.bind(this);
        this.state = { 
            filter: ''
        };
    }

    public render() {
        return (
        <div className="filterableStudentList">
            <div className="well">
            <StudentListFilterForm
                filter={this.state.filter}
                onFilterChange={this.handleChange}
            />
            <StudentListItems 
                filter={this.state.filter}
                students={this.props.students} 
            />
            </div>
            {name}
        </div>
        );
    }

    public handleChange(e: React.FormEvent<HTMLInputElement>): void {
        console.log('handleChange() - label:' + e.currentTarget.name + ', value:' + e.currentTarget.value);
        this.setState({[e.currentTarget.name]: e.currentTarget.value});
    }

}
export default FilterableStudentList;
