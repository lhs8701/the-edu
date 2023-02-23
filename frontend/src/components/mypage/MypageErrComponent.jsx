import { useEffect } from "react";
import { useNavigate } from "react-router";

export default function MypageErrComponent() {
  const navigate = useNavigate();
  // useEffect(() => {
  //   navigate("/my");
  // }, []);
  return <h1>새로고침했음</h1>;
}
