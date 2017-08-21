import * as React from 'react';
import './FilterableStudentList.css';

export interface StudentListFilterFormProps {
    defaultFilterName: string;
    onFilterChange: any;
}

export class StudentListFilterForm extends React.Component<StudentListFilterFormProps, object> {
    constructor(props: StudentListFilterFormProps) {
        super(props);
        this.state = {
            filterName: this.props.defaultFilterName,
        };
    }

    public render() {
        return (
            <form className="form-inline">
                <label>Search Last Name: </label>
                <input 
                    type="text" 
                    name="filterName" 
                    onChange={this.onFilterChange.bind(this, 'filterName')}
                />
                <button type="button" className="btn btn-default pull-right">Add Student</button>
            </form>
        );
    }

    private onFilterChange(label: string, value: string) {
        console.log('StudentListFilterForm::onFilterChange() - label:' + label, ',value:' + value);
        this.props.onFilterChange(label, value);
    }
}
