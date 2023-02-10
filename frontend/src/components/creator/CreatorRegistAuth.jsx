import { useEffect } from "react";
import { useNavigate } from "react-router";
import { useRecoilValue } from "recoil";
import { requestCreatorApi } from "../../api/creatorApi";
import { getAccessTokenSelector } from "../../atom";

export default function CreatorRegistAuth() {
  const accessToken = useRecoilValue(getAccessTokenSelector);
  const navigate = useNavigate();
  const requestCreator = () => {
    requestCreatorApi(accessToken)
      .then(() => {
        alert("신청 완료");
        navigate(-1);
      })
      .catch((err) => {
        alert(err);
        navigate(-1);
      });
  };

  useEffect(requestCreator, []);
  return <div></div>;
}
