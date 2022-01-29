import React from 'react';
import ReactDOM from "react-dom";
import { FrappeGantt, Task, ViewMode } from 'frappe-gantt-react'

//const API = 'http://localhost:2990/jira/rest/api/2/search?jql=';
//const DEFAULT_QUERY = 'assignee=currentUser()';

//const tasks =  new Task();

export default class DynamicTable extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            mode: ViewMode.Month,
            tasks: Task,
        };
    }

    componentDidMount() {
        fetch('http://localhost:2990/jira/rest/api/2/search?jql&assignee=currentUser()', {
            method: 'GET',
            headers: {
                "Accept": "application/json",
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.json())
            .then(data => this.setState({tasks: data})); //
        console.log(data)
    }




    render() {

    return (
      <div>
        <FrappeGantt
            tasks={
                this.state.tasks}
            viewMode={this.state.mode}
            onClick={task => console.log(task)}
            onDateChange={(task, start, end) => console.log(task, start, end)}
            //onProgressChange={(task, progress) => console.log(task, progress)}
            onTasksChange={tasks => console.log(tasks)}
        />
      </div>
    );
  }
}

window.addEventListener('load', function() {

    const wrapper = document.getElementById("container");
    wrapper ? ReactDOM.render(<DynamicTable />, wrapper) : false;
});

