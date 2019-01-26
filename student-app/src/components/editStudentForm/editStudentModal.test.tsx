import * as React from 'react';
import { mount } from 'enzyme';
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
        const initialStudent = new StudentData();
        const form = mount(
            <EditStudentModal 
                title="Add Student"
                submitButtonText="Add Student"
                initialStudent={initialStudent}
                show={false}
                onHide={jest.fn()}
                onSubmit={onSubmit}
            />);
        const inst = form.instance() as EditStudentModal;

        form.setProps({show: true});
        inst.onStudentTextChange('studentId', '123');
        inst.onStudentTextChange('lastName', 'abc');
        inst.onStudentTextChange('firstName', 'xyz');
        expect(inst.state.student).toEqual({studentId: '123', lastName: 'abc', firstName: 'xyz'});

        const file = new File(['editStudentModal'], 'editStudentModal.test.txt', {
            type: 'text/plain',
          });
        inst.onStudentFileChange(file);
        expect(inst.state.photo).toEqual(file);

        inst.onSubmit();
        expect(onSubmit).toHaveBeenCalled();
        form.setProps({show: false});

        /* Check that the form resets properly */
        /* should happen even if show is called multiple times */
        form.setProps({show: true});
        form.setProps({show: true});
        expect(inst.state.student).toEqual(initialStudent);
    
    });    
});