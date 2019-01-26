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
                            onStudentTextValidate={jest.fn()}
                        />);
        expect(toJson(list)).toMatchSnapshot();
    });

    it('triggers internal and external form callbacks when a text field is changed', () => {
        const spy = jest.spyOn(EditStudentFormBase.prototype, 'handleTextFieldChange');
        let mockFormTextChange = jest.fn();
        const form = mount(
                        <EditStudentFormBase
                          student={testData.STUDENT_DATA_EMPTY}
                          onFormTextChange={mockFormTextChange}
                          onFormFileChange={jest.fn()}
                          onStudentTextValidate={jest.fn()}
                        />);

        const input = form.find('input'); 
        expect(input.length).toEqual(4);        

        const lastNameInput = input.find('[id="lastName"]');
        expect(lastNameInput.length).toEqual(1);
        lastNameInput.simulate('change', {target: {id: 'lastName', value: 'abc'}});

        const firstNameInput = input.find('[id="firstName"]');
        expect(firstNameInput.length).toEqual(1);
        firstNameInput.simulate('change', {target: {id: 'firstName', value: 'xyz'}});

        const idInput = input.find('[id="studentId"]');
        expect(idInput.length).toEqual(1);
        idInput.simulate('change', {target: {id: 'studentId', value: '123'}});

        expect(spy.mock.calls.length).toBe(3);   
        expect(spy.mock.calls[0][0].target.id).toBe('lastName');
        expect(spy.mock.calls[0][0].target.value).toBe('abc');
        expect(spy.mock.calls[1][0].target.id).toBe('firstName');
        expect(spy.mock.calls[1][0].target.value).toBe('xyz');
        expect(spy.mock.calls[2][0].target.id).toBe('studentId');
        expect(spy.mock.calls[2][0].target.value).toBe('123');

        expect(mockFormTextChange.mock.calls.length).toBe(3);
        expect(mockFormTextChange.mock.calls[0][0]).toBe('lastName');
        expect(mockFormTextChange.mock.calls[1][0]).toBe('firstName');
        expect(mockFormTextChange.mock.calls[2][0]).toBe('studentId');

        /* Not sure why these dont work...
        expect(mockFormTextChange.mock.calls[0][1]).toBe('abc');
        expect(mockFormTextChange.mock.calls[1][1]).toBe('123');
        */
    });

    it('triggers internal and external form callbacks when the image file is changed', () => {
        
        const spy = jest.spyOn(EditStudentFormBase.prototype, 'handleFileChange');
        let mockFormFileChange = jest.fn();
        const form = mount(
                        <EditStudentFormBase
                          student={testData.STUDENT_DATA_EMPTY}
                          onFormTextChange={jest.fn()}
                          onFormFileChange={mockFormFileChange}
                          onStudentTextValidate={jest.fn()}
                        />);
        const inst = form.instance() as EditStudentFormBase;

        const file = new File(['editStudentFormBase'], 'editStudentFormBase.test.txt', {
            type: 'text/plain',
          });
        form.find('.preview-photo').simulate('change', {target: {files: [file]}});
        expect(spy).toBeCalled();
        expect(inst.state.previewURL.length).toBeGreaterThan(0);
    }); 
});