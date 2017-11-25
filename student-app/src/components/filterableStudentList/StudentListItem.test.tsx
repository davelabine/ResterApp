import * as React from 'react';
import { shallow } from 'enzyme';
import { StudentListItem } from './StudentListItem';
import * as studentTestData from '../../testData';
import toJson from 'enzyme-to-json';

describe('StudentListItem', () => {

  it('renders wiuuthout crashing and matches the last snapshot', () => {
    const item = shallow(
                        <StudentListItem
                          student={studentTestData.STUDENT_DATA_BILLY}
                          onDeleteStudent={jest.fn()}
                          onUpdateStudentClick={jest.fn()}
                        />);
    expect(toJson(item)).toMatchSnapshot();
  });

  it('calls the updateStudent callback', () => {
    const onUpdate = jest.fn();
    const item = shallow(
                        <StudentListItem
                          student={studentTestData.STUDENT_DATA_BILLY}
                          onDeleteStudent={jest.fn()}
                          onUpdateStudentClick={onUpdate}
                        />);
    const input = item.find({ id: 'edit'});
    expect(input.length).toEqual(1);
    input.simulate('click', 1);
    expect(onUpdate).toHaveBeenCalled();
  });

  it('calls the delete callback', () => {
    const onDelete = jest.fn();
    const item = shallow(
                        <StudentListItem
                          student={studentTestData.STUDENT_DATA_BILLY}
                          onDeleteStudent={onDelete}
                          onUpdateStudentClick={jest.fn()}
                        />);
    const input = item.find({ id: 'delete'});
    expect(input.length).toEqual(1);
    input.simulate('click', 1);
    expect(onDelete).toHaveBeenCalled();
  });
}); 