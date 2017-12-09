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
                          onUpdateStudent={jest.fn()}
                          onDeleteStudent={jest.fn()}
                        />);
    expect(toJson(item)).toMatchSnapshot();
  });

  it('handles an edit click', () => {
    const spy = jest.spyOn(StudentListItem.prototype, 'onShowModal');
    const item = shallow(
                        <StudentListItem
                          student={studentTestData.STUDENT_DATA_BILLY}
                          onUpdateStudent={jest.fn()}
                          onDeleteStudent={jest.fn()}
                        />);
    const input = item.find({ id: 'edit'});
    expect(input.length).toEqual(1);
    input.simulate('click', 1);
    expect(spy).toHaveBeenCalled();
  });

  it('handles a delete click', () => {
    const onDelete = jest.fn();
    const item = shallow(
                        <StudentListItem
                          student={studentTestData.STUDENT_DATA_BILLY}
                          onUpdateStudent={jest.fn()}
                          onDeleteStudent={onDelete}
                        />);
    const input = item.find({ id: 'delete'});
    expect(input.length).toEqual(1);
    input.simulate('click', 1);
    expect(onDelete).toHaveBeenCalled();
  });

  it('handles onShowModal and onHideModal', () => {
    const onUpdate = jest.fn();
    const item = shallow(
      <StudentListItem
        student={studentTestData.STUDENT_DATA_BILLY}
        onUpdateStudent={onUpdate}
        onDeleteStudent={jest.fn()}
      />);
    const inst = item.instance() as StudentListItem;

    inst.onShowModal();
    expect(inst.state.show).toBeTruthy();

    inst.onHideModal();
    expect(inst.state.show).toBeFalsy();

    inst.onShowModal();
    inst.onSubmit(studentTestData.STUDENT_DATA_BILLY, undefined);
    expect(inst.state.show).toBeFalsy();
    expect(onUpdate).toHaveBeenCalled();
  });
}); 