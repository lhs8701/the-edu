import { getKakaoAuthToken } from "../../api/authApi";
import { useMutation } from "react-query";
import { Suspense, useEffect } from "react";
import { useRecoilState } from "recoil";
import { LoginState } from "../../atom";

export default function KaKaoAuth() {
  const code = new URL(window.location.href).searchParams.get("code");
  // const kakaoMutation = useMutation(
  //   ["KakaoAuthKey"],
  //   (code) => {
  //     getKakaoAuthToken(code);
  //   },
  //   {
  //     onSuccess: (data) => {
  //       console.log(data);
  //     },
  //     onError: () => {
  //       alert("우우");
  //     },
  //   }
  // );
  const [isLoggedIn, setIsLoggedIn] = useRecoilState(LoginState);

  useEffect(() => {
    try {
      getKakaoAuthToken(code).then((data) => {
        console.log(data.data);
      });
    } catch {}
  }, [code]);

  useEffect(() => {
    kakao.mutate({ code });
  }, [code]);
  console.log(kakao);
  return (
    <Suspense fallback={<div>loading</div>}>
      <div>login..</div>
    </Suspense>
  );
}
