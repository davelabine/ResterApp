import * as React from 'react';
import { Button, Modal } from 'react-bootstrap';
import { mount, ReactWrapper } from 'enzyme';
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
});