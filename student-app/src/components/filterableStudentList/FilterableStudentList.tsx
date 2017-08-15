import * as React from 'react';
import Input from 'react-toolbox/lib/input/Input';
/*
import AppBar from 'react-toolbox/lib/app_bar';
import Navigation from 'react-toolbox/lib/navigation';
*/

import './FilterableStudentList.css';

export interface Props {
  name: string;
}

class FilterableStudentList extends React.Component<Props, object> {
  render() {
    const { name } = this.props;

    return (
      <div className="search">
        <Input 
            type="text"
            label="Name"
            name="name"
            maxLength={70}
        />
        <div>{name}</div>
      </div>
    );
  }
}
export default FilterableStudentList;

/*

      <div className="filterableStudentList">
        <div className="well">
          <form className="form-inline">
              <label>Search Last Name:
                <input type="text" value={name} />
              </label>
              <button type="submit" className="btn btn-default">Search</button>
          </form>
          <div> No Students yet!</div>
        </div>
      </div>

    return (
      <div className="filterableStudentList">
        <div class="well">
        <form class="form-inline">
          <div class="form-group">
            <label for="lname">Search Last Name: </label>
            <input type="text" name="name">
            <button type="submit" class="btn btn-default">Search</button>
          </div>
          <div> No Students yet! </div>
          <a id="addStudentButton" type="button" class="btn btn-default pull-right" href="">Add Student</a>
        </form>
      </div>
    );
    */
