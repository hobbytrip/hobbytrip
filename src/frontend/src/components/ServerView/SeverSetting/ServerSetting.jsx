import React, { useEffect, useRef, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import style from './ServerSetting.module.css';
import { TbCameraPlus, TbCheck } from "react-icons/tb";
import { IoClose } from "react-icons/io5";
import useUserStore from '../../../actions/useUserStore';

const URL = 'http://localhost:8080';

const ServerSetting = ({  }) => {
  const [planetName, setPlanetName] = useState('');
  const [planetDescription, setPlanetDescription] = useState('');
  const [img, setImg] = useState(null);  
  const [openRoom, setOpenRoom] = useState(false);

  const serverId = 'serverid';
  const userId = 'user';
  const user = useUserStore();
  const navigate = useNavigate();
  const imgRef = useRef();

  useEffect(() => {
    axios.get(`${URL}/api/community/server/${serverId}/${userId}`)
      .then(res => {
        const data = res.data;
        setPlanetName(data.name || '');
        setPlanetDescription(data.description || '');
        // setPlanetCategory(data.category || '');
        setImg(data.profile || null);
        setOpenRoom(data.open || false);
      })
      .catch(error => {
        console.error('Error fetching server data:', error);
      });
  }, [serverId, userId]);

  const handleSubmit = () => {
    const formdata = new FormData();
    formdata.append("requestDto", JSON.stringify({
      serverId: serverId,
      userId: userId,
      name: planetName,
      description: planetDescription,
      // category: planetCategory,
      profile: img,
      open: openRoom,
    }));

    axios.patch(`${URL}/api/community/server/${serverId}/${userId}`, formdata, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
    .then(res => {
      if (!res.data.success) {
        alert("수정하는 중에 오류가 발생했습니다");
        console.error(res);
      } else {
        alert("수정되었습니다");
      }
    })
    .catch(err => {
      alert("수정하는 중에 오류가 발생했습니다");
      console.error('Error submitting update:', err);
    });
  }

  const handleClose = () => {
    navigate(-1);
  }

  const handleImage = () => {
    const reader = new FileReader();
    const file = imgRef.current.files[0];
    reader.readAsDataURL(file);
    reader.onloadend = () => {
      setImg(reader.result);
    };
  };

  const handleDelete = () => {
    const confirmDelete = window.confirm("정말 삭제하시겠습니까?");
    if (confirmDelete) {
      axios.delete(`${URL}/api/community/server`, {
        data: {
          serverId: serverId,
          userId: userId
        }
      })
      .then(res => {
        if (res.data.success) {
          alert("삭제되었습니다");
          navigate('/main');
        } else {
          alert("삭제하는 중에 오류가 발생했습니다");
          console.error(res);
        }
      })
      .catch(err => {
        alert("삭제하는 중에 오류가 발생했습니다");
        console.error('Error deleting server:', err);
      });
    }
  }

  return(
    <div className={style.setting}>
      <div className={style.updateForm}>
        <div className={style.inputDiv}>
          <div>
            <h4>행성 이름 변경하기</h4>
            <input value={planetName} 
              onChange={(e) => setPlanetName(e.target.value)} />
          </div>
        </div>
        <div className={style.inputDiv}>
          <div>
            <h4>행성 소개글 수정하기</h4>
            <input value={planetDescription} 
              onChange={(e) => setPlanetDescription(e.target.value)} />
          </div>
        </div>
        <div className={style.inputDiv}>
          <div>
            <h4>행성 카테고리 수정하기</h4>
            <input disabled />
            {/* <input value={planetCategory} 
              onChange={(e) => setPlanetCategory(e.target.value)} /> */}
          </div>
        </div>
        <div className={style.inputDivImage}>
          <div>
            <h4>행성 아이콘 변경하기</h4>
            <div className={style.addImg}>
              <div>
                { img ? <img src={img} alt="planet icon" /> : null }
              </div>
              <label className={style.addImgBtn}>
                <h4>이미지 업로드</h4>
                <input type="file" 
                  ref={imgRef}
                  style={{ display: 'none' }}
                  onChange={handleImage} />
                <TbCameraPlus style={{width: '15px', height: '15px'}} />
              </label>
            </div>
          </div>
          <button 
            className={style.deletePlanet}
            onClick={handleDelete}>
              행성 삭제하기 
          </button>
        </div>
      </div>
      <div className={style.submitBtn}>
        <button onClick={handleClose}>
          <IoClose style={{width: '18px', height: '18px'}} />
        </button>
        <button onClick={handleSubmit}>
          <TbCheck style={{width: '18px', height: '18px'}} />
        </button>
      </div>
    </div>
  )
}

export default ServerSetting;
