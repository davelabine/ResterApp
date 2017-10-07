import * as React from 'react';
import * as ReactDOM from 'react-dom';
import registerServiceWorker from './registerServiceWorker';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import './index.css';

import { createStore } from 'redux';
import { enthusiasm } from './reducers/index';
import { StoreState } from './types/index';
import { Provider } from 'react-redux';
import { STUDENT_DATA_BIG_LIST } from './testData/testStudents';

import FilterableStudentList from './components/filterableStudentList/FilterableStudentList';

const store = createStore<StoreState>(enthusiasm, {
  enthusiasmLevel: 1,
  languageName: 'TypeScript',
});

ReactDOM.render(
  <Provider store={store}>
    <FilterableStudentList students={STUDENT_DATA_BIG_LIST}/>
  </Provider>,
  document.getElementById('root') as HTMLElement
);

registerServiceWorker();