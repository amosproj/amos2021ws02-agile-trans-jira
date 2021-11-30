import React from 'react';
import ReactDOM from "react-dom";



export default class DynamicTable extends React.Component {

  render() {
    return (
      <div> Hello World!!! </div>
    );
  }
}

window.addEventListener('load', function() {

    const wrapper = document.getElementById("container");
    wrapper ? ReactDOM.render(<DynamicTable />, wrapper) : false;
});
