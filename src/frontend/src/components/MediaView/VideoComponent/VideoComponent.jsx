import React, { Component } from 'react';
import style from './VideoComponent.module.css';

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
      <video autoPlay={true} ref={this.videoRef} className={style.video} />
    )
  }
}

export default VideoComponent;