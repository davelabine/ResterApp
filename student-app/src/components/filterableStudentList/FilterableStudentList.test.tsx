import * as React from 'react';
import { mount } from 'enzyme';
import { FilterableStudentList } from './FilterableStudentList';

describe('FilterableStudentList', () => {
    it('renders without crashing', () => {
      mount(<FilterableStudentList defaultFilter="testorama"/>);
    });
    it('handles ', () => {
        /*
      const list = shallow(<FilterableStudentList defaultFilter="testorama"/>);
      */
    });
});
