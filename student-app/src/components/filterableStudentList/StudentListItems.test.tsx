import * as React from 'react';
import { shallow, mount } from 'enzyme';
import { StudentListItems } from './StudentListItems';
import * as constants from '../../constants/index';
import * as studentTestData from '../../testData';
import toJson from 'enzyme-to-json';

describe('StudentListItems', () => {

  it('renders without crashing and matches the last snapshot', () => {
    const list = mount(<StudentListItems filter="" students={studentTestData.LIST_STUDENT_DATA_TWO}/>);
    expect(toJson(list)).toMatchSnapshot();
  }); 

  it('renders the right number of studentListItems', () => {
    const list = shallow(<StudentListItems filter="" students={studentTestData.LIST_STUDENT_DATA_TWO}/>);
    const listRows = list.find('.studentListItemsBody').children();
    expect(listRows.length).toEqual(2);
  });

  it('renders a message when it gets an empty student list', () => {
    const list = shallow(<StudentListItems filter="" students={studentTestData.LIST_STUDENT_DATA_EMPTY}/>);
    const listRows = list.find('.studentListItemsBody').children();
    expect(listRows.length).toEqual(1);
    expect(listRows.first().text()).toEqual(constants.STUDENT_LIST_ITEMS_EMPTY);
  });

  it('filters out all but the A students from a two student list', () => {
    const list = shallow(<StudentListItems filter="A" students={studentTestData.LIST_STUDENT_DATA_TWO}/>);
    const listRows = list.find('.studentListItemsBody').children();
    expect(listRows.length).toEqual(1);
  });

  it('ignores case when filtering students from a two student list', () => {
    const list = shallow(<StudentListItems filter="A" students={studentTestData.LIST_STUDENT_DATA_TWO}/>);
    const listRows = list.find('.studentListItemsBody').children();
    expect(listRows.length).toEqual(1);
  });

  it('properly filters out all students from a two student list and displays the empty student message', () => {
    const list = shallow(<StudentListItems filter="Z" students={studentTestData.LIST_STUDENT_DATA_TWO}/>);
    const listRows = list.find('.studentListItemsBody').children();
    expect(listRows.length).toEqual(1);
    expect(listRows.first().text()).toEqual(constants.STUDENT_LIST_ITEMS_EMPTY);
  });
});