import { getKakaoAuthToken, kakaoLogin } from "../../api/authApi";
import { useMutation } from "react-query";
import { Suspense, useEffect } from "react";
import { useRecoilState } from "recoil";
import { KakaoAuthTokenAtom, LoginState } from "../../atom";
import { useNavigate } from "react-router";

export default function KaKaoAuth() {
  const code = new URL(window.location.href).searchParams.get("code");
  const navigate = useNavigate();
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
  const [isKakaoAuthToken, setIsKakaoAuthToken] =
    useRecoilState(KakaoAuthTokenAtom);

  useEffect(() => {
    getKakaoAuthToken(code)
      .then((res) => {
        setIsKakaoAuthToken(res.data.access_token);
        kakaoLogin(res.data.access_token)
          .then(({ data }) => {
            setIsLoggedIn({
              state: true,
              isKakao: true,
              isBasic: false,
              accessToken: data.accessToken,
              refreshToken: data.refreshToken,
              memberId: data.memberId,
            });
            navigate("/");
          })
          .catch((err) => {
            console.log(err.response.status);
            alert("이미 가입된 정보입니다.");
            navigate(-1);
          });
      })
      .catch((err) => {
        console.log(err.response.status);
        if (err.response.data.error_code === "KOE320") {
          alert("너무 많은 로그인 시도입니다. 한번만 시도해주세요.");
        } else {
          alert("카카오 로그인 정보가 부정확합니다.");
        }
        navigate(-1);
      });
  }, [code]);

  return (
    <Suspense fallback={<div>loading</div>}>
      <div>login..</div>
    </Suspense>
  );
}
