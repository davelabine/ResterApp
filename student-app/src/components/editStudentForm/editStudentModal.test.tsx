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

    /* Todo: write some unit tests for the modal dialog callbacks
       My first attempt proved tricky since the objects are rendered 
       into a seperate object tree 
       it('returns callbacks for Submit and Close', () => {
        const form = mount(
                        <EditStudentModal 
                            title="Add Student"
                            submitButtonText="Add Student"
                            initialStudent={new StudentData()}
                            show={true}
                            onHide={jest.fn()}
                            onSubmit={jest.fn()}
                        />);
        expect(toJson(form)).toMatchSnapshot();
    });
    */
});