import * as React from 'react';
import { mount, shallow } from 'enzyme';
import { FilterableStudentList } from './FilterableStudentList';
import toJson from 'enzyme-to-json';
import * as studentTestData from '../../testData/';

describe('FilterableStudentList', () => {
    it('renders without crashing and matches the last snapshot', () => {
      const list = mount(
                      <FilterableStudentList 
                        students={studentTestData.LIST_STUDENT_DATA_TWO} 
                        filter=""
                        onFetchStudents={jest.fn()}
                        onAddStudent={jest.fn()}
                        onFilterStudents={jest.fn()}
                        onUpdateStudent={jest.fn()} 
                        onDeleteStudent={jest.fn()}
                      />);
      expect(toJson(list)).toMatchSnapshot();
    });

    it('calls onFetchStudents after being mounted', () => {
      const mockFetchStudents = jest.fn();
      shallow(
          <FilterableStudentList 
            students={studentTestData.LIST_STUDENT_DATA_TWO} 
            filter=""
            onFetchStudents={mockFetchStudents}
            onAddStudent={jest.fn()}
            onFilterStudents={jest.fn()}
            onUpdateStudent={jest.fn()} 
            onDeleteStudent={jest.fn()}
          />);
      expect(mockFetchStudents).toHaveBeenCalled();
    });
});
