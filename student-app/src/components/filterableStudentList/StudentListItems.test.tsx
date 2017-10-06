import * as React from 'react';
import { shallow, mount } from 'enzyme';
import { StudentData } from './StudentListInterfaces.d';
import { StudentListItems, STUDENT_LIST_ITEMS_EMPTY } from './StudentListItems';

let studentListEmpty: StudentData[] = [];
let studentList: StudentData[] =  [
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

describe('StudentListItems', () => {

  it('renders without crashing', () => {
    mount(<StudentListItems students={studentListEmpty}/>);
  });

  it('wraps the studentListItems in a table with a header and a body', () => {
    let list = shallow(<StudentListItems students={studentList}/>);
    let table = list.find('.studentListItems');
    expect(table.length).toEqual(1);
    expect(table.find('.studentListItemsHead').length).toEqual(1); 
    expect(table.find('.studentListItemsBody').length).toEqual(1); 
  });

  it('renders the right number of studentListItems', () => {
    let list = shallow(<StudentListItems students={studentList}/>);
    let listRows = list.find('.studentListItemsBody').children();
    expect(listRows.length).toEqual(2);
  });

  it('renders a message when it gets an empty student list', () => {
    let list = shallow(<StudentListItems students={studentListEmpty}/>);
    let listRows = list.find('.studentListItemsBody').children();
    expect(listRows.length).toEqual(1);
    expect(listRows.first().text()).toEqual(STUDENT_LIST_ITEMS_EMPTY);
  });

});