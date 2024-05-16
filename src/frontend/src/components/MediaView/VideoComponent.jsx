import React, { Component } from 'react';

class VideoComponent extends Component {

  constructor(props) {
    super(props);
    this.videoRef = React.createRef();
  }

  componentDidUpdate(props) {
    if (props && !!this.videoRef) {
      this.props.streamManager.addVideoElement(this.videoRef.current);
    }
  }

  componentDidMount() {
    if (this.props && !!this.videoRef) {
      this.props.streamManager.addVideoElement(this.videoRef.current);
    }
  }

  render() {
    return (
      <video autoPlay={true} ref={this.videoRef}
        style={{
          width: '183px',
          height: '343px'}} />
    )
  }
}

export default VideoComponent;