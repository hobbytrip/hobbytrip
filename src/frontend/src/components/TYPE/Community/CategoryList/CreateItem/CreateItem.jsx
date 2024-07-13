import React, { useState } from "react";
import CreateCategory from "../../../..//Modal/ServerModal/Category/CreateCategory/CreateCategory";
import CreateChannel from "../../../../Modal/ServerModal/Channel/CreateChannel/CreateChannel";
import style from "./CreateItem.module.css";
import { IoCheckmarkCircleOutline, IoCheckmarkCircle } from "react-icons/io5";
import { HiUserGroup, HiHome } from "react-icons/hi2";
import { IoMdCreate } from "react-icons/io";

const CreateItem = ({ userId, onClose }) => {
  const [type, setType] = useState("category");
  const [isFormOpen, setIsFormOpen] = useState(false);

  const handleTypeChange = (newType) => {
    setType(newType);
  };

  const handleContinue = () => {
    setIsFormOpen(true);
  };

  const handleBack = () => {
    if (isFormOpen) {
      setIsFormOpen(false);
    } else {
      onClose();
    }
  };

  return (
    <>
      {!isFormOpen ? (
        <>
          <div className={style.formWrapper}>
            <div className={style.topContainer}>
              <h3>
                <IoMdCreate
                  style={{ width: "18px", height: "18px", marginRight: "5px" }}
                />
                만들기 유형
              </h3>
            </div>
            <div className={style.radioContainer}>
              <div
                className={style.typeRadioOption}
                onClick={() => handleTypeChange("category")}
              >
                <h4>
                  <HiHome style={{ marginRight: "5px" }} />
                  카테고리
                </h4>
                {type === "category" ? (
                  <IoCheckmarkCircle className={style.purpleIcon} />
                ) : (
                  <IoCheckmarkCircleOutline className={style.outlineIcon} />
                )}
              </div>
              <div
                className={style.typeRadioOption}
                onClick={() => handleTypeChange("channel")}
              >
                <h4>
                  <HiUserGroup style={{ marginRight: "5px" }} />
                  채널
                </h4>
                {type === "channel" ? (
                  <IoCheckmarkCircle className={style.purpleIcon} />
                ) : (
                  <IoCheckmarkCircleOutline className={style.outlineIcon} />
                )}
              </div>
            </div>
            <button className={style.continueBtn} onClick={handleContinue}>
              계속하기
            </button>
          </div>
          <button className={style.backBtn} onClick={handleBack}>
            <h4>뒤로 가기</h4>
          </button>
        </>
      ) : type === "category" ? (
        <CreateCategory userId={userId} onClose={onClose} onBack={handleBack} />
      ) : (
        <CreateChannel userId={userId} onClose={onClose} onBack={handleBack} />
      )}
    </>
  );
};

export default CreateItem;
