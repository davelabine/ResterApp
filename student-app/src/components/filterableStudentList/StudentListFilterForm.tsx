import * as React from 'react';
import './FilterableStudentList.css';

export interface StudentListFilterFormProps {
    filter: string;
    onFilterChange(e: React.FormEvent<HTMLInputElement>): void;
}

export class StudentListFilterForm extends React.Component<StudentListFilterFormProps> {
    constructor(props: StudentListFilterFormProps) {
        super(props);
    }

    public render() {
        return (
            <form className="form-inline">
                <label>Search Last Name: </label>
                <input 
                    type="text" 
                    name="filter" 
                    onChange={this.props.onFilterChange}
                    value={this.props.filter}
                />
                <button type="button" className="btn btn-default pull-right">Add Student</button>
            </form>
        );
    }
}
