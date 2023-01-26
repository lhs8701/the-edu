import { useEffect } from "react";
import { useNavigate } from "react-router";

export default function ErrorComponent() {
  const navigate = useNavigate();
  useEffect(() => {}, []);
  return <h1>This component crashed</h1>;
}
