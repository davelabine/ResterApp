import * as React from 'react';
import './FilterableStudentList.css';

type StudentPhoto = {
    bucketName: string;
    key: string;
};

type StudentData = {
    skey: string;
    id: string;
    name: string;
    photo: StudentPhoto;
};

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

export interface TableItemProps {
    student: StudentData;
}

function TableItem(props: TableItemProps) {
    let student = props.student;
    return (
        <tr key={student.skey}>
            <td>{student.name}</td>
            <td>{student.id}</td>
            <td><a href="http://google.com">view</a> | 
            <a href="http://google.com"> edit</a></td>
        </tr>
    );
}

export interface FilterableStudentListProps {
    name: String;
}

class FilterableStudentList extends React.Component<FilterableStudentListProps, object> {
  render() {
    const { name } = this.props;

    return (
      <div className="filterableStudentList">
        <div className="well">
          <form className="form-inline">
              <label>Search Last Name: </label>
              <input type="text" name="name"/>
              <button type="button" className="btn btn-primary">Search</button>
              <button type="button" className="btn btn-default pull-right">Add Student</button>
          </form>
          <table className="table table-striped table-sm table-hover">
            <thead>
              <tr>
                <th>Name</th>
                <th>ID</th>
                <th>URL</th>
              </tr>
            </thead>
            <tbody>
                {funTimes.map((s) =>
                    <TableItem key={s.skey} student={s}/>
                )}
            </tbody>
          </table>
        </div>
        {name}
      </div>
    );
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