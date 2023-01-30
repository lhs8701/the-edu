import { useEffect } from "react";
import { getFormResponse } from "../../api/adminApi";

export default function CreatorRequest() {
  useEffect(() => {
    getFormResponse()
      .then((res) => {
        console.log(res);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);
  return <div>목록</div>;
}
