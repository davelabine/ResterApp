import * as React from 'react';
import './FilterableStudentList.css';
import { StudentData } from '../../types';
import { StudentListFilterForm } from './StudentListFilterForm';
import { StudentListItems } from './StudentListItems';

export interface Props {
    students: Array<StudentData>;
    onFetchStudents?: () => void;
}

export class FilterableStudentList extends React.Component<Props, object> {
    
    constructor(props: Props) {
        super(props);
        this.handleChange = this.handleChange.bind(this);
        /*
        this.state = { 
            filter: ''
        }
        */
    }

    public render() {
        return (
        <div className="filterableStudentList">
            <div className="well">
            <StudentListFilterForm
                /*filter={this.state.filter}*/
                filter="b"
                onFilterChange={this.handleChange}
            />
            <StudentListItems 
                /*filter={this.state.filter}*/
                filter="b"
                students={this.props.students} 
            />
            </div>
        </div>
        );
    }

    public handleChange(e: React.FormEvent<HTMLInputElement>): void {
        /*
        this.setState({[e.currentTarget.name]: e.currentTarget.value});
        */
        console.log('handleChange');
        if (this.props.onFetchStudents) {
            this.props.onFetchStudents();
        }

    }

}
export default FilterableStudentList;
