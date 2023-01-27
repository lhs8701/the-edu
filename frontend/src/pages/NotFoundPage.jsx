import { useEffect } from "react";
import { useNavigate } from "react-router";

export default function NotFoundPage() {
  const navigate = useNavigate();
  useEffect(() => {
    alert("확인되지 않은 접근입니다!");
    navigate("/");
  }, []);
  return <h1>404 Not Found</h1>;
}
