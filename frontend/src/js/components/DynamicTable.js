import React from 'react';
import ReactDOM from "react-dom";
import { FrappeGantt, Task, ViewMode } from 'frappe-gantt-react'

const tasks = [
  {
    id: "Task 1",
    name: "Redesign website",
    start: "2021-10-28",
    end: "2021-11-31",
    progress: 10,
    dependencies: "",
  },
  {
    id: "Task 2",
    name: "Redesign website",
    start: "2021-11-31",
    end: "2021-12-15",
    progress: 20,
    dependencies: "Task 1",
  },
  {
    id: "Task 3",
    name: "Redesign website",
    start: "2021-12-15",
    end: "2021-12-31",
    progress: 0,
    dependencies: "Task 2, Task 1",
  },
].map((x) => new Task(x));



export default class DynamicTable extends React.Component {

    constructor(props) {
      super(props);
      this.state = {
        mode: ViewMode.Month,
      };
    }

  render() {
    return (
      <div>
        <FrappeGantt
            tasks={tasks}
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

