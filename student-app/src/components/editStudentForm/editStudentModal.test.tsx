import * as React from 'react';
import { mount, shallow } from 'enzyme';
import { EditStudentModal } from './editStudentModal';
import { StudentData } from '../../types';
import toJson from 'enzyme-to-json';

describe('EditStudentModal', () => {
    it('renders without crashing and matches the last snapshot', () => {
        const form = mount(
                        <EditStudentModal 
                            title="Add Student"
                            submitButtonText="Add Student"
                            initialStudent={new StudentData()}
                            show={false}
                            onHide={jest.fn()}
                            onSubmit={jest.fn()}
                        />);
        expect(toJson(form)).toMatchSnapshot();
    });

    it('handles form events and submit', () => {
        const onSubmit = jest.fn();
        const form = shallow(
            <EditStudentModal 
                title="Add Student"
                submitButtonText="Add Student"
                initialStudent={new StudentData()}
                show={false}
                onHide={jest.fn()}
                onSubmit={onSubmit}
            />);
        const inst = form.instance() as EditStudentModal;

        inst.onStudentTextChange('id', '123');
        inst.onStudentTextChange('name', 'abc');
        expect(inst.state.student).toEqual({id: '123', name: 'abc'});

        const file = new File(['editStudentModal'], 'editStudentModal.test.txt', {
            type: 'text/plain',
          });
        inst.onStudentFileChange(file);
        expect(inst.state.photo).toEqual(file);

        inst.onSubmit();
        expect(onSubmit).toHaveBeenCalled();
    });
});