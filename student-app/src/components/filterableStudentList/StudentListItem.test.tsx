import * as React from 'react';
import { mount, shallow} from 'enzyme';
import { StudentListItem } from './StudentListItem';
import * as studentTestData from '../../testData';
import toJson from 'enzyme-to-json';

describe('StudentListItem', () => {

  it('renders wiuuthout crashing and matches the last snapshot', () => {
    const item = shallow(
                        <StudentListItem
                          key={studentTestData.STUDENT_DATA_BILLY.skey}
                          student={studentTestData.STUDENT_DATA_BILLY}
                          onDeleteStudent={jest.fn()}
                        />);
    expect(toJson(item)).toMatchSnapshot();
  });

  it('calls the delete callback', () => {
    const onDelete = jest.fn();
    const item = shallow(
                        <StudentListItem
                          key={studentTestData.STUDENT_DATA_BILLY.skey}
                          student={studentTestData.STUDENT_DATA_BILLY}
                          onDeleteStudent={onDelete}
                        />);
    const input = item.find({ id: 'delete'});
    expect(input.length).toEqual(1);
    input.simulate('click', 1);
    expect(onDelete).toHaveBeenCalled();
  });
}); 