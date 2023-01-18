import { getKakaoAuthToken } from "../../api/socialAuthApi";
import { useMutation } from "react-query";
import { Suspense, useEffect } from "react";

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

  useEffect(() => {
    try {
      getKakaoAuthToken({ code }).then((data) => {
        console.log(data.data);
      });
    } catch {}
  }, [code]);

  return (
    <Suspense fallback={<div>loading</div>}>
      <div>login..</div>
    </Suspense>
  );
}
