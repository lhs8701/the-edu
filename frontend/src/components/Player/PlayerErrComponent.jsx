import { useEffect } from "react";

export default function PlayerErrComponent() {
  useEffect(() => {
    alert("오류로 인해 플레이어를 종료합니다.");
    window.close();
  }, []);
}
