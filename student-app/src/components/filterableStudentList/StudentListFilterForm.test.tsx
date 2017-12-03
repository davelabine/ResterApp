import * as React from 'react';
import { mount, shallow } from 'enzyme';
import { StudentListFilterForm } from './StudentListFilterForm';
import toJson from 'enzyme-to-json';
import * as studentTestData from '../../testData';

describe('FilterableStudentList', () => {
    it('renders without crashing and matches the last snapshot', () => {
      const form = mount(<StudentListFilterForm filter="" onFilterStudents={jest.fn()} onAddStudent={jest.fn()}/>);
      expect(toJson(form)).toMatchSnapshot();
    });
    
    it('triggers filter callbacks when input is changed', () => {
      const spy = jest.spyOn(StudentListFilterForm.prototype, 'onFormFilterChange');
      const mockFilterStudents = jest.fn();
      const form = mount(
                      <StudentListFilterForm 
                        filter="" 
                        onFilterStudents={mockFilterStudents} 
                        onAddStudent={jest.fn()}
                      />);
      const input = form.find('input');
      expect(input.length).toEqual(1);
      input.simulate('change', {target: {value: 'abc'}});
      /* Check the form callback */
      expect(spy).toHaveBeenCalled();
      /* Check the external callback */
      expect(mockFilterStudents).toHaveBeenCalled();
    });

    it('opens EditStudentModal dialog when Add Student button is clicked.', () => {
      const mockAddStudent = jest.fn();
      const form = mount(
                      <StudentListFilterForm 
                        filter="" 
                        onFilterStudents={jest.fn()} 
                        onAddStudent={mockAddStudent}
                      />);
      const input = form.find('button');
      expect(input.length).toEqual(1);
      input.simulate('click', 1);
      expect(form.state().show).toBeTruthy();
    });

    it('handles onShowModal, onHideModal, and Submit', () => {
      const onAdd = jest.fn();
      const item = shallow(
          <StudentListFilterForm 
            filter="" 
            onFilterStudents={jest.fn()} 
            onAddStudent={onAdd}
          />);
      const inst = item.instance() as StudentListFilterForm;
  
      inst.onShowModal();
      expect(inst.state.show).toBeTruthy();
  
      inst.onHideModal();
      expect(inst.state.show).toBeFalsy();

      inst.onShowModal();
      inst.onSubmit(studentTestData.STUDENT_DATA_BILLY, undefined);
      expect(inst.state.show).toBeFalsy();
      expect(onAdd).toHaveBeenCalled();
    });
});
