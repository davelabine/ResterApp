import * as React from 'react';
import { mount, shallow } from 'enzyme';
import { FilterableStudentList } from './FilterableStudentList';
import * as studentTestData from '../../testData/testStudents';

describe('FilterableStudentList', () => {
    it('renders without crashing', () => {
      mount(<FilterableStudentList students={studentTestData.STUDENT_DATA_EMPTY}/>);
    });

    it('has a StudentListFilterForm and StudentListItems ', () => {
      const list = shallow(<FilterableStudentList students={studentTestData.STUDENT_DATA_TWO}/>);
      expect(list.find('StudentListFilterForm').length).toEqual(1);
      expect(list.find('StudentListItems').length).toEqual(1); 
    });

    it('changes state when filter is changed ', () => {
      const list = mount(<FilterableStudentList students={studentTestData.STUDENT_DATA_TWO}/>);
      const form = list.find('StudentListFilterForm');
      const input = form.find('input');
      const newTextFilter = 'abc';
      input.simulate('change', {target: {value: newTextFilter}});
      
      /* TODO: how do we check the state of this component? 
      expect(list.state.filter).toBe(newTextFilter);
      */
    });
});
