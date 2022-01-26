import React from 'react';
import ReactDOM from "react-dom";
import { FrappeGantt, Task, ViewMode } from 'frappe-gantt-react'


const tasks = [{"name":"MOC-3","start":"2022-01-25 16:00:18.0","progress":"10","end":"2022-01-31 00:00:00.0","id":"10002","dependencies":""},{"name":"MOC-2","start":"2022-01-25 16:00:10.0","progress":"10","end":"2022-01-28 00:00:00.0","id":"10001","dependencies":""},{"name":"MOC-1","start":"2022-01-25 15:59:56.0","progress":"10","end":"2022-01-28 00:00:00.0","id":"10000","dependencies":""}].map((x) => new Task(x));



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

