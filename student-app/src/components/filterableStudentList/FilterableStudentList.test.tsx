import * as React from 'react';
import { mount } from 'enzyme';
import { FilterableStudentList } from './FilterableStudentList';
import * as studentTestData from '../../testData/testStudents';

describe('FilterableStudentList', () => {
    it('renders without crashing', () => {
      mount(<FilterableStudentList students={studentTestData.STUDENT_DATA_EMPTY}/>);
    });
});

/*
    it('handles ', () => {
      const list = shallow(<FilterableStudentList defaultFilter="testorama"/>);
      
    });
*/
