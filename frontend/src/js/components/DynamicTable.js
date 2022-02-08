import React from 'react';
import ReactDOM from "react-dom";
import { FrappeGantt, Task, ViewMode } from 'frappe-gantt-react';


const startTask = [new Task(
    {
      id: "Task 2",
      name: "Redesign website",
      start: undefined,
      end: undefined,
      url: "",
      progress:100,
        custom_class: "bar-orange"
    },
)]


export default class DynamicTable extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      mode: ViewMode.Day,
      tasks: startTask,
    }
  }



  getIssuesPromise() {
    return new Promise(async resolve => {

        let fieldsResponse = await fetch('http://localhost:2990/jira/rest/api/2/field',{
            method: 'GET',
            headers: {
                "Accept": "application/json",
                'Content-Type': 'application/json'
            }
        })
        let fieldData= await fieldsResponse.json();
        let startField;
        let endField;

        for (let data of fieldData) {
            if (data.name == 'Start of Work' ) {
                startField = data.id;
            }
            if (data.name == 'End of Work' ) {
                endField = data.id;
            }
        };



      let newTaskArray = [];
      let response = await fetch('http://localhost:2990/jira/rest/api/2/search?jql=type%20%3D%20Request%20AND%20assignee%20%3D%20currentUser()%20AND%20status%20!%3D%20Done', {
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
        let start = data.issues[obj].fields[startField]
        let end = data.issues[obj].fields[endField]

        newTaskArray.push(new Task({
          id: key,
          name: name,
          start: start,
          end: end,
          url: window.location.protocol+'//' + window.location.host+'/jira/browse/'+key,
          progress:100,
            custom_class: "bar-orange"
        }))
      }
      resolve(newTaskArray);
    })
  }

  async updateTasks(){

    let newTaskArray = await this.getIssuesPromise();
    console.log("working")
    if(newTaskArray == this.state.tasks){
      return
    }

    console.log("newTaskArray: " + newTaskArray)
    console.log("state.tasks: ")
    console.log(this.state.tasks)



    this.setState({
      tasks: newTaskArray,
    })
  }

  componentDidMount() {
    this.updateTasks();
  }

  componentDidUpdate(prevProps, prevState) {

  }

  render() {
    return (
        <div className="App">
        <FrappeGantt
            tasks={this.state.tasks}
            viewMode={this.state.mode}
            onClick={task => {
              this.updateTasks();
              window.location=task.url;
            }
            }
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

