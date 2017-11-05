import * as React from 'react';
import { mount } from 'enzyme';
import { FilterableStudentList } from './FilterableStudentList';
import toJson from 'enzyme-to-json';
import * as studentTestData from '../../testData/';

describe('FilterableStudentList', () => {
    it('renders without crashing and matches the last snapshot', () => {
      const list = mount(<FilterableStudentList students={studentTestData.STUDENT_DATA_TWO} filter=""/>);
      expect(toJson(list)).toMatchSnapshot();
    });

    it('calls onFetchStudents after being mounted', () => {
      const mockFetchStudents = jest.fn();
      mount(
          <FilterableStudentList 
            students={studentTestData.STUDENT_DATA_TWO} 
            filter=""
            onFetchStudents={mockFetchStudents}
          />);
      expect(mockFetchStudents).toHaveBeenCalled();
    });

  /*
    it('calls onAddStudent when a new student is created', () => {

      const mockAddStudent = jest.fn();
      const list = mount(
                    <FilterableStudentList 
                      students={studentTestData.STUDENT_DATA_TWO} 
                      filter=""
                      onAddStudent={mockAddStudent}
                    />);
      const input = list.find('button');
      expect(input.length).toEqual(1);
      input.simulate('click', 1);
      expect(mockAddStudent).toHaveBeenCalled();
    });
    */
});
