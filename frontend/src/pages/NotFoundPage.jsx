import { useEffect } from "react";
import { useNavigate } from "react-router";

export default function NotFoundPage() {
  const navigate = useNavigate();
  useEffect(() => {}, []);
  return <h1>404 Not Found</h1>;
}
