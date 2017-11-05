import * as React from 'react';
import { mount } from 'enzyme';
import { StudentListFilterForm } from './StudentListFilterForm';

describe('FilterableStudentList', () => {
    it('renders without crashing and matches the last snapshot', () => {
      const form = mount(<StudentListFilterForm filter="" onFilterStudents={jest.fn()} onAddStudentClick={jest.fn()}/>);
      expect(form).toMatchSnapshot();
    });
    
    it('triggers filter callbacks when input is changed', () => {
      const spy = jest.spyOn(StudentListFilterForm.prototype, 'handleFormFilterChange');
      const mockFilterStudents = jest.fn();
      const form = mount(
                      <StudentListFilterForm 
                        filter="" 
                        onFilterStudents={mockFilterStudents} 
                        onAddStudentClick={jest.fn()}
                      />);
      const input = form.find('input');
      expect(input.length).toEqual(1);
      input.simulate('change', {target: {value: 'abc'}});
      /* Check the form callback */
      expect(spy).toHaveBeenCalled();
      /* Check the external callback */
      expect(mockFilterStudents).toHaveBeenCalled();
    });

    it('triggers addStudent callback when AddStudent button is clicked', () => {
      const mockAddStudent = jest.fn();
      const form = mount(
                      <StudentListFilterForm 
                        filter="" 
                        onFilterStudents={jest.fn()} 
                        onAddStudentClick={mockAddStudent}
                      />);
      const input = form.find('button');
      expect(input.length).toEqual(1);
      input.simulate('click', 1);
      /* Check the external callback */
      expect(mockAddStudent).toHaveBeenCalled();
    });
});