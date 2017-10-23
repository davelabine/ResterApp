import * as React from 'react';
import './FilterableStudentList.css';

export interface StudentListFilterFormProps {
    filter: string;
    onFilterChange(e: React.FormEvent<HTMLInputElement>): void;
    onAddStudentClick(): void;
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
                    className="studentListNameFilter" 
                    onChange={this.props.onFilterChange}
                    value={this.props.filter}
                />
                <button 
                    type="button" 
                    className="btn btn-default pull-right"
                    onClick={this.props.onAddStudentClick}
                >Add Student
                </button>
            </form>
        );
    }
}

export default StudentListFilterForm;
