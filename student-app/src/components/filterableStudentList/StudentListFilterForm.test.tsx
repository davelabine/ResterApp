import * as React from 'react';
import { mount, shallow } from 'enzyme';
import { StudentListFilterForm } from './StudentListFilterForm';

describe('FilterableStudentList', () => {
    it('renders without crashing', () => {
      mount(<StudentListFilterForm filter="" onFilterChange={jest.fn()}/>);
    });
    
    it('triggers callback when input is changed', () => {
      const mockOnFilterChange = jest.fn();
      const form = shallow(<StudentListFilterForm filter="" onFilterChange={mockOnFilterChange}/>);
      const input = form.find('input');
      expect(input.length).toEqual(1);
      input.simulate('change', {target: {value: 'abc'}});
      expect(mockOnFilterChange).toHaveBeenCalled();
    });
});