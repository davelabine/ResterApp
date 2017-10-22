import * as React from 'react';
import * as ReactDOM from 'react-dom';
import registerServiceWorker from './registerServiceWorker';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import './index.css';

import { createStore } from 'redux';
import { studentReducer } from './reducers/index';
import { StoreState } from './types/index';
import { Provider } from 'react-redux';
import * as testData from './testData';

import FilterableStudentList from './containers/FilterableStudentList.c';

const store = createStore<StoreState>(studentReducer, {
  studentList: testData.STUDENT_DATA_TWO,
});

ReactDOM.render(
  <Provider store={store}>
    <FilterableStudentList/>
  </Provider>,
  document.getElementById('root') as HTMLElement
);

registerServiceWorker();