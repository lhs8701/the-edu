import { useEffect, useState } from "react";
import ReactPlayer from "react-player";
import { useNavigate, useParams } from "react-router";
import { useRecoilValue } from "recoil";
import { getUnitVideoApi } from "../../../api/unitApi";
import { getAdminAccessTokenSelector } from "../../../atom";
import { STATIC_URL } from "../../../static";

export default function UnitVideo() {
  const accessToken = useRecoilValue(getAdminAccessTokenSelector);
  const { unitId } = useParams();
  const [unitInfo, setUnitInfo] = useState();
  const navigate = useNavigate();

  const getUnitInfo = () => {
    getUnitVideoApi(unitId, accessToken)
      .then(({ data }) => {
        if (data?.code) {
          alert("err");
        }
        setUnitInfo(data);
      })
      .catch((err) => {
        alert(err);
      });
  };

  useEffect(() => {
    getUnitInfo();
  }, []);

  return (
    <div style={{ height: "500px" }}>
      <button
        onClick={() => {
          navigate(-1);
        }}
      >
        뒤로가기
      </button>
      <div>{unitInfo?.title}</div>
      <div>{unitInfo?.description}</div>
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
