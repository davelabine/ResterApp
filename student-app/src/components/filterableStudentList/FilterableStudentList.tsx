import * as React from 'react';
import './FilterableStudentList.css';
import { StudentData } from './StudentListInterfaces.d';
import { StudentListFilterForm } from './StudentListFilterForm';
import { StudentListItems } from './StudentListItems';

export interface FilterableStudentListProps {
    name: string;
}

let funTimes: StudentData[] =  [
    {
        'skey': '8a80810d5dd89a3f015dd89ac8280000',
        'id': 'Billy Bob',
        'name': '1234',
        'photo': {
            'bucketName': 'resterapp-dev',
            'key': 'e93ecbf0-ba96-4290-a906-5ea67e4c4a9f'
        }
    },
    {
        'skey': '8a80810d5dd89a3f015dd8a252800001',
        'id': '503074',
        'name': 'Albert Russell',
        'photo': {
            'bucketName': 'resterapp-dev',
            'key': 'e93ecbf0-ba96-4290-a906-5ea67e4c4a9f'
        }
    },
];

export class FilterableStudentList extends React.Component<FilterableStudentListProps, any> {
    constructor(props: FilterableStudentListProps) {
        super(props);
        this.handleChange = this.handleChange.bind(this);
        this.state = { 
            filter: '***'
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
                students={funTimes} 
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

/*
[   
    {
        'skey': '8a80810d5dd89a3f015dd89ac8280000',
        'id': 'BIlly Bob',
        'name': '1234',
        'photo': {
            'bucketName': 'resterapp-dev',
            'key': 'e93ecbf0-ba96-4290-a906-5ea67e4c4a9f'
        }
    },
    {
        'skey': '8a80810d5dd89a3f015dd8a252800001',
        'id': '503074',
        'name': 'Albert Russell',
        'photo': null
    },
    {
        "skey": "8a80810d5dd89a3f015dd89ac8280000",
        "id": "BIlly Bob",
        "name": "1234",
        "photo": {
            "bucketName": "resterapp-dev",
            "key": "e93ecbf0-ba96-4290-a906-5ea67e4c4a9f"
        }
    },
    {
        "skey": "8a80810d5dd89a3f015dd8a252800001",
        "id": "503074",
        "name": "Albert Russell",
        "photo": null
    },
    {
        "skey": "8a80810d5dd89a3f015dd8a259b2000e",
        "id": "942226",
        "name": "Alfredo Cummings",
        "photo": null
    },
    {
        "skey": "8a80810d5dd89a3f015dd8a2677f0027",
        "id": "824388",
        "name": "Allen Russell",
        "photo": null
    },
    {
        "skey": "8a80810d5dd89a3f015dd8a271fb003a",
        "id": "266192",
        "name": "Andre McKenna",
        "photo": null
    },
    {
        "skey": "8a80810d5dd89a3f015dd8a27f0d0052",
        "id": "932083",
        "name": "Angelica Obrien",
        "photo": null
    },
    {
        "skey": "8a80810d5dd89a3f015dd8a281c80057",
        "id": "682307",
        "name": "Anna Washington",
        "photo": null
    },
    {
        "skey": "8a80810d5dd89a3f015dd8a27c52004d",
        "id": "734552",
        "name": "Antonio Fowler",
        "photo": null
    },
    {
        "skey": "8a80810d5dd89a3f015dd8a25fb80019",
        "id": "615643",
        "name": "Ashley Mcdaniel",
        "photo": null
    },
    {
        "skey": "8a80810d5dd89a3f015dd8a25897000c",
        "id": "753238",
        "name": "Beatrice Russell",
        "photo": null
    }
]
*/