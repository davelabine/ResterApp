import * as React from 'react';
import { mount } from 'enzyme';
import { EditStudentFormBase } from './editStudentFormBase';
import { StudentData } from '../../types';
import toJson from 'enzyme-to-json';

describe('EditStudentformBase', () => {
    it('renders without crashing and matches the last snapshot', () => {
        const list = mount(
                        <EditStudentFormBase
                            student={new StudentData()}
                        />);
        expect(toJson(list)).toMatchSnapshot();
    });
});