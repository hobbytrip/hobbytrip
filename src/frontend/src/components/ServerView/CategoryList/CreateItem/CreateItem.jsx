import React, { useState } from 'react';
import CreateCategory from '../../../Modal/ServerModal/Category/CreateCategory/CreateCategory';
import CreateChannel from '../../../Modal/ServerModal/Channel/CreateChannel/CreateChannel';
import style from './CreateItem.module.css';
import { IoCheckmarkCircleOutline, IoCheckmarkCircle } from "react-icons/io5";

const CreateItem = ({ userId, onClose }) => {
  const [type, setType] = useState('category');
  const [isFormOpen, setIsFormOpen] = useState(false);

  const handleTypeChange = (newType) => {
    setType(newType);
  };

  const handleContinue = () => {
    setIsFormOpen(true);
  };

  const handleBack = () => {
    setIsFormOpen(false);
  };

  return (
    <>
      {!isFormOpen ? (
        <div className={style.formWrapper}>
          <div className={style.topContainer}>
            <h3>만들기 유형</h3>
          </div>
          <div className={style.radioContainer}>
            <div className={style.typeRadioOption} onClick={() => handleTypeChange('category')}>
              <IoCheckmarkCircleOutline style={{ width: '18px', height: '18px' }} />
              <h4>카테고리</h4>
              {type === 'category' && <IoCheckmarkCircle className={style.purpleIcon} />}
            </div>
            <div className={style.typeRadioOption} onClick={() => handleTypeChange('channel')}>
              <IoCheckmarkCircleOutline style={{ width: '18px', height: '18px' }} />
              <h4>채널</h4>
              {type === 'channel' && <IoCheckmarkCircle className={style.purpleIcon} />}
            </div>
          </div>
          <button className={style.continueBtn} onClick={handleContinue}>
            계속하기
          </button>
          <button className={style.backBtn} onClick={handleBack}>
            <h4>뒤로 가기</h4>
          </button>
        </div>
      ) : type === 'category' ? (
        <CreateCategory userId={userId} onClose={onClose} onBack={handleBack} />
      ) : (
        <CreateChannel userId={userId} onClose={onClose} onBack={handleBack} />
      )}
    </>
  );
};

export default CreateItem;
