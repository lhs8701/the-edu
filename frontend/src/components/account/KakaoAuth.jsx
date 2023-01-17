import { fetchCoins, getKakaoAuthToken } from "../../api/socialAuthApi";
import { useMutation, useQuery } from "react-query";
import { Suspense, useEffect } from "react";
import { useParams } from "react-router";

export default function KaKaoAuth() {
  const kakao = useMutation(getKakaoAuthToken.post);
  // const { status, data, error, isIdle } = useMutation(
  //   "test",
  //   getKakaoAuthToken.post,
  //   {
  //     suspense: true,
  //   }
  // );

  const code = new URL(window.location.href).searchParams.get("code");

  useEffect(() => {
    kakao.mutate({ code });
  }, [code]);
  console.log(kakao);
  return (
    <Suspense fallback={<div>loading</div>}>
      <div>실험중</div>
    </Suspense>
  );
}
