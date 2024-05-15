import React, { Component } from 'react';
import VideoComponent from './VideoComponent';

class UserVideoComponent extends Component {
    getNicknameTag() {
        // Gets the nickName of the user
        return JSON.parse(this.props.streamManager.stream.connection.data).clientData;
    }

  render() {
    return (
      <div>
        {this.props.streamManager !== undefined ? (
          <div className="streamcomponent">
            <VideoComponent streamManager={this.props.streamManager} />
            <div><p>{this.getNicknameTag()}</p></div>
          </div>
        ) : null}
      </div>
    );
  }
}

export default UserVideoComponent;