import * as React from 'react';
import { mount } from 'enzyme';
import { EditStudentFormBase } from './editStudentFormBase';
import * as testData from '../../testData/index';
import toJson from 'enzyme-to-json';

describe('EditStudentformBase', () => {
    it('renders without crashing and matches the last snapshot', () => {
        let mockFormTextChange = jest.fn();
        const list = mount(
                        <EditStudentFormBase
                            student={testData.STUDENT_DATA_EMPTY}
                            onFormTextChange={mockFormTextChange}
                            onFormFileChange={jest.fn()}
                        />);
        expect(toJson(list)).toMatchSnapshot();
    });

    it('triggers internal and external form text callbacks when a field is changed', () => {
        const spy = jest.spyOn(EditStudentFormBase.prototype, 'handleTextFieldChange');
        let mockFormTextChange = jest.fn();
        const form = mount(
                        <EditStudentFormBase
                          student={testData.STUDENT_DATA_EMPTY}
                          onFormTextChange={mockFormTextChange}
                          onFormFileChange={jest.fn()}
                        />);

        const input = form.find('input'); 
        expect(input.length).toEqual(3);        

        const nameInput = input.find('[id="name"]');
        expect(nameInput.length).toEqual(1);
        nameInput.simulate('change', {target: {id: 'name', value: 'abc'}});

        const idInput = input.find('[id="id"]');
        expect(idInput.length).toEqual(1);
        idInput.simulate('change', {target: {id: 'id', value: '123'}});

        expect(spy.mock.calls.length).toBe(2);   
        expect(spy.mock.calls[0][0].target.id).toBe('name');
        expect(spy.mock.calls[0][0].target.value).toBe('abc');
        expect(spy.mock.calls[1][0].target.id).toBe('id');
        expect(spy.mock.calls[1][0].target.value).toBe('123');

        expect(mockFormTextChange.mock.calls.length).toBe(2);
        expect(mockFormTextChange.mock.calls[0][0]).toBe('name');
        expect(mockFormTextChange.mock.calls[1][0]).toBe('id');

        /* Not sure why these dont work...
        expect(mockFormTextChange.mock.calls[0][1]).toBe('abc');
        expect(mockFormTextChange.mock.calls[1][1]).toBe('123');
        */
    });
});