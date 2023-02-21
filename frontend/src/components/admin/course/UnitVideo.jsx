import { useEffect, useState } from "react";
import ReactPlayer from "react-player";
import { useNavigate, useParams } from "react-router";
import { useRecoilValue } from "recoil";
import { getUnitVideoApi } from "../../../api/unitApi";
import {
  getAccessTokenSelector,
  getAdminAccessTokenSelector,
} from "../../../atom";
import { STATIC_URL } from "../../../static";
import { MoveBtn } from "../../../style/CommonCss";

export default function UnitVideo() {
  const adminAccessToken = useRecoilValue(getAdminAccessTokenSelector);
  const accessToken = useRecoilValue(getAccessTokenSelector);
  const { unitId } = useParams();
  const [unitInfo, setUnitInfo] = useState();
  const navigate = useNavigate();

  const getUnitInfo = () => {
    getUnitVideoApi(unitId, adminAccessToken)
      .then(({ data }) => {
        if (data?.code === -7001) {
          getUnitVideoApi(unitId, accessToken)
            .then(({ data }) => {
              if (data?.code) {
                alert("에러발생");
                navigate(-1);
              }
              setUnitInfo(data);
            })
            .catch((err) => {
              alert("에러발생");
            });
        } else {
          setUnitInfo(data);
        }
      })
      .catch((err) => {
        getUnitVideoApi(unitId, accessToken)
          .then(({ data }) => {
            if (data?.code) {
              alert("에러발생");
              navigate(-1);
            }
            setUnitInfo(data);
          })
          .catch((err) => {
            alert("에러발생");
          });
      });
  };

  useEffect(() => {
    getUnitInfo();
  }, []);

  return (
    <div style={{ height: "500px" }}>
      <MoveBtn
        style={{ height: "30px", width: "100px" }}
        onClick={() => {
          navigate(-1);
        }}
      >
        뒤로가기
      </MoveBtn>
      <br />
      <div>제목: {unitInfo?.title}</div>
      <div>설명: {unitInfo?.description}</div>
      <br />
      <br />
      <ReactPlayer
        url={STATIC_URL + unitInfo?.videoInfo?.filePath}
        controls={true}
        onEnded={() => {
          navigate(-1);
        }}
        light={false}
        width="100%"
        height="100%"
      />
    </div>
  );
}
