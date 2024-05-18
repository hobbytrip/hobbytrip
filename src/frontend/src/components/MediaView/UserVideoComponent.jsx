import React, { Component } from 'react';
import VideoComponent from './VideoComponent';

class UserVideoComponent extends Component {
  render() {
    return (
      <div>
        {this.props.streamManager !== undefined ? (
            <VideoComponent streamManager={this.props.streamManager} />
        ) : null}
      </div>
    );
  }
}

export default UserVideoComponent;