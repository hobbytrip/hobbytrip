import style from './ServerSetting.module.css';
import React, { useEffect, useRef, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import API from '../../../utils/API/API';
import useServerStore from '../../../actions/useServerStore';
import useUserStore from '../../../actions/useUserStore';
import { TbCameraPlus, TbCheck } from "react-icons/tb";
import { IoClose } from "react-icons/io5";
import { axiosInstance } from '../../../utils/axiosInstance';

const ServerSetting = () => {
  const [serverName, setServerName] = useState('');
  const [serverDescription, setServerDescription] = useState('');
  const [serverCategory, setServerCategory] = useState('');
  const [profileImage, setProfileImage] = useState(null);
  const [profilePreview, setProfilePreview] = useState(null);
  const [isOpen, setisOpen] = useState(false);
  const { serverData, setServerData } = useServerStore((state) => ({
    serverData: state.serverData,
    setServerData: state.setServerData
  }));
  const { userId } = useUserStore();
  const imgRef = useRef();
  const nav = useNavigate();
  const serverInfo = serverData.serverInfo;

  useEffect(() => {
    setServerName(serverInfo.name || '');
    setServerDescription(serverInfo.description || '');
    setServerCategory(serverInfo.category || '');
    setProfileImage(serverInfo.profile || null);
    setProfilePreview(serverInfo.profile || null);
    setisOpen(serverInfo.open || false);
  }, [serverInfo]);

  const handleUpdate = async (e) => {
    e.preventDefault();
    if (serverName === '') {
      alert("행성 이름을 적어주세요");
      return;
    }
    try {
      const formData = new FormData();
      const data = JSON.stringify({
        serverId: serverInfo.serverId,
        userId: userId,
        name: serverName,
        // description: serverDescription,
        // open: isOpen
        // category: category,
      });
      const communityData = new Blob([data], { type: "application/json" });
      formData.append("requestDto", communityData);
      if(profileImage !== null){
        formData.append("profile", profileImage);
      }
      else{
        formData.append("profile", 'null');
      }
      console.log(data)

      for (let [key, value] of formData.entries()) {
        console.log(`${key}: ${value}`);
      }

      const response = await axiosInstance.patch(API.SERVER, formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });

      if (response.data.success) {
        alert('성공!!!!!!!!!!!!!!')
        console.log(response);
      } else {
        console.log(response);        
      }
    } catch (error) {
      console.error("데이터 post 에러:", error);
    }
  };

  const handleClose = () => {
    nav(-1);
  };

  const handleImage = () => {
    const file = imgRef.current.files[0];
    if (file) {
      setProfileImage(file);
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onloadend = () => {
        setProfilePreview(reader.result);
      };
    }
  };

  const handleDelete = async () => {
    const confirmDelete = window.confirm("정말 삭제하시겠습니까?");
    if (confirmDelete) {
      if (String(userId) !== String(serverInfo.managerId)) {
        alert("삭제 권한이 없습니다");
      } else {
        try {
          const res = await axiosInstance.delete(API.SERVER, {
            data: {
              serverId: serverInfo.serverId,
              userId: userId,
            },
          });
          if (res.status === 200) {
            alert("삭제되었습니다");
            nav('/main');
          } else {
            alert("삭제하는 중에 오류가 발생했습니다");
            console.error(res);
          }
        } catch (error) {
          alert("삭제하는 중에 오류가 발생했습니다");
          console.error(error);
        }
      }
    }
  };

  return (
    <div className={style.setting}>
      <div className={style.updateForm}>
        <div className={style.inputDiv}>
          <div>
            <h4>행성 이름 변경하기</h4>
            <input
              value={serverName}
              onChange={(e) => setServerName(e.target.value)}
              placeholder="행성 이름을 입력하세요"
            />
          </div>
        </div>
        <div className={style.inputDiv}>
          <div>
            <h4>행성 소개글 수정하기</h4>
            <input
              value={serverDescription}
              onChange={(e) => setServerDescription(e.target.value)}
              placeholder="행성 소개글을 입력하세요"
            />
          </div>
        </div>
        <div className={style.inputDiv}>
          <div>
            <h4>행성 카테고리 수정하기</h4>
            <input
              value={serverCategory}
              onChange={(e) => setServerCategory(e.target.value)}
              placeholder="행성 카테고리를 입력하세요"
            />
          </div>
        </div>
        <div className={style.inputDivImage}>
          <div>
            <h4>행성 아이콘 변경하기</h4>
            <div className={style.addImg}>
              <div>
                {profilePreview ? <img src={profilePreview} alt="profile preview" /> : null}
              </div>
              <label className={style.addImgBtn}>
                <h4>이미지 업로드</h4>
                <input
                  type="file"
                  ref={imgRef}
                  style={{ display: 'none' }}
                  onChange={handleImage}
                />
                <TbCameraPlus style={{ width: '15px', height: '15px' }} />
              </label>
            </div>
          </div>
          <button
            className={style.deleteServer}
            onClick={handleDelete}
          >
            행성 삭제하기
          </button>
        </div>
      </div>
      <div className={style.submitBtn}>
        <button onClick={handleClose}>
          <IoClose style={{ width: '18px', height: '18px' }} />
        </button>
        <button onClick={handleUpdate}>
          <TbCheck style={{ width: '18px', height: '18px' }} />
        </button>
      </div>
    </div>
  );
};

export default ServerSetting;
