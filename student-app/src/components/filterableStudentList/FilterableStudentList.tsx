import * as React from 'react';
import './FilterableStudentList.css';

export interface Props {
  name: string;
}

class FilterableStudentList extends React.Component<Props, object> {
  render() {
    const { name } = this.props;

    return (
      <div className="filterableStudentList">
        <div className="well">
          <form className="form-inline">
              <label>Search Last Name: </label>
              <input type="text" name="name"/>
              <button type="button" className="btn btn-primary">Search</button>
          </form>
        </div>
        <table className="table table-striped table-sm table-hover">
          <thead>
            <tr>
              <th>Name</th>
              <th>ID</th>
              <th>URL</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>{name}</td>
              <td>12345</td>
              <td><a href="http://google.com">view</a> | 
              <a href="http://google.com"> edit</a></td>
            </tr>
          </tbody>
        </table>
      </div>
    );
  }
}
export default FilterableStudentList;

/*
<table class="table table-striped table-sm table-hover">
    <thead>
      <tr>
          <th>Name</th>
          <th>ID</th>
          <th>URL</th>
      </tr>
    </thead>
    <#list studentList as student>
    <tbody>
      <tr>
          <td>${student.name}</td>
          <td>${student.id}</td>
          <td><a id="viewStudent" href="${rootUrl}id/${student.skey}">view</a></td>
          <td><a id="editStudent" href="${rootUrl}id/${student.skey}/edit">edit</a></td>
      </tr>
      </#list>
    </tbody>
<#else>
  <div> No Students yet! </div>
</#if>
</table>
*/
