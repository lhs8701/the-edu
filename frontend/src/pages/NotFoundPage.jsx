import { useEffect } from "react";
import { useNavigate } from "react-router";

export default function NotFoundPage() {
  const navigate = useNavigate();
  useEffect(() => {
    navigate(-1);
  }, []);
  return <h1>404 Not Found</h1>;
}
