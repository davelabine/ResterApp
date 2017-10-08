import * as React from 'react';
import { shallow, mount } from 'enzyme';
import { StudentListItems, STUDENT_LIST_ITEMS_EMPTY } from './StudentListItems';
import * as studentTestData from '../../testData/testStudents';

describe('StudentListItems', () => {

  it('renders without crashing', () => {
    mount(<StudentListItems filter="" students={studentTestData.STUDENT_DATA_TWO}/>);
  }); 

  it('wraps the studentListItems in a table with a header and a body', () => {
    let list = shallow(<StudentListItems filter="" students={studentTestData.STUDENT_DATA_TWO}/>);
    let table = list.find('.studentListItems');
    expect(table.length).toEqual(1);
    expect(table.find('.studentListItemsHead').length).toEqual(1); 
    expect(table.find('.studentListItemsBody').length).toEqual(1); 
  });

  it('renders the right number of studentListItems', () => {
    let list = shallow(<StudentListItems filter="" students={studentTestData.STUDENT_DATA_TWO}/>);
    let listRows = list.find('.studentListItemsBody').children();
    expect(listRows.length).toEqual(2);
  });

  it('renders a message when it gets an empty student list', () => {
    let list = shallow(<StudentListItems filter="" students={studentTestData.STUDENT_DATA_EMPTY}/>);
    let listRows = list.find('.studentListItemsBody').children();
    expect(listRows.length).toEqual(1);
    expect(listRows.first().text()).toEqual(STUDENT_LIST_ITEMS_EMPTY);
  });

  it('filters out all but the A students from a two student list', () => {
    let list = shallow(<StudentListItems filter="A" students={studentTestData.STUDENT_DATA_TWO}/>);
    let listRows = list.find('.studentListItemsBody').children();
    expect(listRows.length).toEqual(1);
  });

  it('ignores case when filtering students from a two student list', () => {
    let list = shallow(<StudentListItems filter="a" students={studentTestData.STUDENT_DATA_TWO}/>);
    let listRows = list.find('.studentListItemsBody').children();
    expect(listRows.length).toEqual(1);
  });

  it('properly filters out all students from a two student list and displays the empty student message', () => {
    let list = shallow(<StudentListItems filter="Z" students={studentTestData.STUDENT_DATA_TWO}/>);
    let listRows = list.find('.studentListItemsBody').children();
    expect(listRows.length).toEqual(1);
    expect(listRows.first().text()).toEqual(STUDENT_LIST_ITEMS_EMPTY);
  });

});