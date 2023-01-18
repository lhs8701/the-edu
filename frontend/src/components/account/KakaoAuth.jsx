import { fetchCoins, getKakaoAuthToken } from "../../api/socialAuthApi";
import { useMutation, useQuery } from "react-query";
import { Suspense, useEffect } from "react";
import { useParams } from "react-router";

export default function KaKaoAuth() {
  const code = new URL(window.location.href).searchParams.get("code");
  const kakaoMutation = useMutation(["KakaoAuthKey"], (code) => {
    getKakaoAuthToken(code);
  });

  useEffect(() => {
    kakaoMutation.mutate({ code });
  }, [code]);

  return (
    <Suspense fallback={<div>loading</div>}>
      <div>실험중</div>
    </Suspense>
  );
}
