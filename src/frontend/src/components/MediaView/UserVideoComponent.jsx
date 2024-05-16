import React, { Component } from 'react';
import VideoComponent from './VideoComponent/VideoComponent';

class UserVideoComponent extends Component {
  render() {
    return (
      <div>
        {this.props.streamManager !== undefined ? (
          <div className="streamcomponent">
            <VideoComponent streamManager={this.props.streamManager} />
            {/* <div><p>{this.getNicknameTag()}</p></div> */}
          </div>
        ) : null}
      </div>
    );
  }
}

export default UserVideoComponent;