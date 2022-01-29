import React from 'react';
import ReactDOM from "react-dom";
import { FrappeGantt, Task, ViewMode } from 'frappe-gantt-react'


const startTask = [new Task(
    {
      id: "Task 2",
      name: "Redesign website",
      start: undefined,
      end: undefined,
      progress: 20,
      dependencies: "",
    },
)]


export default class DynamicTable extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      mode: ViewMode.Month,
      tasks: startTask,
    }

  }



  getIssuesPromise() {
    return new Promise(async resolve => {
      let newTaskArray = [];
      let response = await fetch('http://localhost:2990/jira/rest/api/2/search?jql&assignee=currentUser()', {
        method: 'GET',
        headers: {
          "Accept": "application/json",
          'Content-Type': 'application/json'
        }
      })
      let data = await response.json();
      for (let obj in Array.from(data.issues)) {
        let key = data.issues[obj].key
        let name = data.issues[obj].fields.summary
        let start = data.issues[obj].fields.customfield_10102
        let end = data.issues[obj].fields.customfield_10103
        console.log(key, name, start, end)

        newTaskArray.push(new Task({
          id: key,
          name: name,
          start: start,
          end: end,
          progress: 0,
          dependencies: "",
        }))
      }
      resolve(newTaskArray);
    })
  }


  async updateTasks(){

    let newTaskArray = await this.getIssuesPromise();

    console.log("newTaskArray: " + newTaskArray)
    console.log("state.tasks: ")
    console.log(this.state.tasks)

    let nextTasks = this.state.tasks;

    nextTasks = nextTasks.concat(newTaskArray);

    this.setState({
      tasks: nextTasks,
    })
  }

  componentDidMount() {
    this.updateTasks();
  }

  componentDidUpdate(prevProps, prevState) {
    //this.updateTasks();
  }

  render() {
    return (
      <div>
        <FrappeGantt
            tasks={this.state.tasks}
            viewMode={this.state.mode}
            onClick={task => console.log(task)}
            onDateChange={(task, start, end) => console.log(task, start, end)}
            onProgressChange={(task, progress) => console.log(task, progress)}
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

