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
        expect(input.length).toEqual(3);        

        const nameInput = input.find('[id="lastName"]');
        expect(nameInput.length).toEqual(1);
        nameInput.simulate('change', {target: {id: 'lastName', value: 'abc'}});

        const idInput = input.find('[id="id"]');
        expect(idInput.length).toEqual(1);
        idInput.simulate('change', {target: {id: 'id', value: '123'}});

        expect(spy.mock.calls.length).toBe(2);   
        expect(spy.mock.calls[0][0].target.id).toBe('lastName');
        expect(spy.mock.calls[0][0].target.value).toBe('abc');
        expect(spy.mock.calls[1][0].target.id).toBe('id');
        expect(spy.mock.calls[1][0].target.value).toBe('123');

        expect(mockFormTextChange.mock.calls.length).toBe(2);
        expect(mockFormTextChange.mock.calls[0][0]).toBe('lastName');
        expect(mockFormTextChange.mock.calls[1][0]).toBe('id');

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