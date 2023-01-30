import { useEffect } from "react";
import { useNavigate } from "react-router";

export default function ErrorComponent() {
  const navigate = useNavigate();
  // useEffect(() => {
  //   alert("확인되지 않은 접근입니다!");
  //   navigate(-1);
  // }, []);
  return <h1>This component crashed</h1>;
}
