import { Button } from "@mui/material";
import { isAxiosError } from "axios";
import { useEffect, useLayoutEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { useQuery } from "react-query";
import { useRecoilValue } from "recoil";
import styled from "styled-components";
import { queryClient } from "../..";
import { postTossTxId, successTossCert } from "../../api/authApi";
import { uploadImageApi } from "../../api/creatorApi";
import { changePwdApi, myInfoApi, revisemyInfoApi } from "../../api/myPageApi";
import { getAccessTokenSelector, getMemberIdSelector } from "../../atom";
import { useScript } from "../../hook";
import { STATIC_URL } from "../../static";
import {
  AccountBtn,
  AccountForm,
  AccountInput,
  InputBox,
  InputLabel,
} from "../../style/AccountComponentCss";
import {
  MyPageBox,
  MyPageContentBox,
  MyPageTitle,
} from "../../style/MypageComponentsCss";

const MIN_PWD_LENGTH = 8;
const MAX_NAME_LENGTH = 16;
const MIN_NAME_LENGTH = 2;
const MAX_PWD_LENGTH = 16;
const MIN_TELE_LENGTH = 10;
const MAX_TELE_LENGTH = 11;

const CertBox = styled.div`
  position: absolute;
  top: 45%;
  right: 0%;
`;

const AuthBtn = styled.div`
  background-color: var(--color-box-gray);
  color: var(--color-black);
  font-size: var(--size-login-btn);
  font-weight: var(--weight-point);
  height: 35px;
  border-radius: 7px;
  width: 180px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  &:active {
    transform: scale(0.9);
  }
  &:hover {
    color: var(--color-background);
  }
`;

const ImgBox = styled.div`
  display: flex;
  align-items: center;
`;

const ReviseForm = styled(AccountForm)`
  width: var(--size-revise-coupon-window);
  margin: 0 auto;
`;

const ProfileImg = styled.img`
  width: 110%;
  border-radius: 50%;
  height: 110%;
  border: 1px solid var(--color-box-gray);
`;

const NameInputBox = styled(InputBox)``;

export default function Revise() {
  const memberId = useRecoilValue(getMemberIdSelector);
  const accessToken = useRecoilValue(getAccessTokenSelector);
  const infos = useQuery(
    ["myInfo", memberId],
    () => {
      return myInfoApi(memberId, accessToken);
    },
    {
      enabled: !!memberId,
      onSuccess: ({ email, nickname, profileImage }) => {
        setIsName(nickname);

        setIsId(email);

        setIsProfileImg(profileImage?.originalFilePath);

        setValue("name", nickname);
        setValue("email", email);
      },
      onError: () => {
        console.error("에러 발생했지롱");
      },
    }
  );

  const [isId, setIsId] = useState(infos?.data?.email);
  const [isName, setIsName] = useState(infos?.data?.nickname);
  const [isCurrentPwd, setIsCurrentPwd] = useState("");
  const [isNewPwd, setIsNewPwd] = useState("");
  const [isProfileImg, setIsProfileImg] = useState(
    infos?.data?.profileImage?.originalFilePath
  );

  const {
    register,
    handleSubmit,
    formState: { errors },
    setValue,
  } = useForm({
    mode: "onBlur",
    defaultValues: {
      name: isName,
      email: isId,
    },
    reValidateMode: "onBlur",
  });

  const {
    register: register2,
    handleSubmit: handleSubmit2,
    formState: { errors: errors2 },
  } = useForm({
    mode: "onBlur",
    reValidateMode: "onBlur",
  });

  const submit = () => {
    const info = {
      nickname: isName,
      email: isId,
      profileImage: isProfileImg,
    };
    revisemyInfoApi(accessToken, info)
      .then(() => {
        alert("완료");
        queryClient.refetchQueries({ queryKey: ["myInfo", memberId] });
      })
      .catch((err) => {
        alert("에러");
        console.log(err);
        console.log(err.response.status);
        if (err.response.status === 401) {
        } else {
        }
      });
  };
  const uploadImg = (e) => {
    uploadImageApi(e.target.files[0], accessToken)
      .then(({ data }) => {
        setIsProfileImg(data.originalFilePath);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const changePwd = () => {
    changePwdApi(accessToken, isCurrentPwd, isNewPwd)
      .then(() => alert("비밀번호가 변경되었습니다."))
      .catch(() => {
        alert("err");
      });
  };
  console.log(infos?.data?.certified);
  function AuthButton() {
    const [loading, error] = useScript("https://cdn.toss.im/cert/v1");

    return (
      <AuthBtn
        onClick={() => {
          // eslint-disable-next-line no-undef
          var tossCert = TossCert(); // 1. TossCert객체를 초기화합니다.
          tossCert.preparePopup(); // 2. 팝업차단 방지를 위해 비동기 호출 전에 빈 팝업을 먼저 띄웁니다.
          // 3. 서버 투 서버의 인증요청 API를 호출합니다. 원하는 방식으로 적절히 수정해주세요.
          postTossTxId(accessToken)
            .then(({ data }) => {
              tossCert.start({
                authUrl: data.success.authUrl,
                txId: data.success.txId,
                onSuccess: function () {
                  alert("성공");
                  successTossCert(data.success.txId, accessToken)
                    .then(() => {
                      window.location.reload();
                    })
                    .catch((err) => {
                      alert(err);
                    });
                },
                onFail: function (error) {
                  alert("에러가 발생했습니다", error); // 인증 실패시 행동을 정의해주세요.
                },
              });
            })
            .catch((err) => {
              alert(err);
            });
        }}
      >
        전화번호 인증하기
      </AuthBtn>
    );
  }

  return (
    <MyPageBox>
      <MyPageTitle>개인정보 수정</MyPageTitle>
      <MyPageContentBox>
        <ReviseForm onSubmit={handleSubmit(submit)}>
          <InputBox>
            <CertBox>
              {infos?.data?.certified ? (
                "본인인증이 완료된 계정이에요."
              ) : (
                <AuthButton />
              )}
            </CertBox>
            <InputLabel>프로필 사진</InputLabel>
            <br />
            <br />
            <br />
            <ImgBox>
              <Button
                sx={{
                  width: 120,
                  height: 100,
                  backgroundColor: "var(--color-background)",
                  border: "none",
                  boxShadow: "none",
                  "&:hover": {
                    backgroundColor: "var(--color-background)",
                  },
                }}
                variant="contained"
                component="label"
              >
                <input
                  hidden
                  accept=".jpg, .jpeg, .png"
                  onChange={uploadImg}
                  multiple
                  type="file"
                />
                <ProfileImg src={STATIC_URL + isProfileImg} />
              </Button>
            </ImgBox>
          </InputBox>
          <br />
          <br />
          <br />
          <NameInputBox>
            <InputLabel>성명</InputLabel>
            <AccountInput
              {...register("name", {
                name: "name",
                required: "성명을 입력하세요!",
                // 유효성 검사 파트
                // pattern: {
                //   value: /^[A-Za-z0-9._%+-]+@knu\.ac.kr$/,
                //   message: "wrong input",
                // },
                onChange: (e) => {
                  setIsName(e.target.value);
                },
                minLength: {
                  value: MIN_NAME_LENGTH,
                  message: "2글자 이상 입력해주세요.",
                },
                maxLength: {
                  value: MAX_NAME_LENGTH,
                  message: "16글자 이하로 입력해주세요.",
                },
              })}
              type="text"
            />
          </NameInputBox>
          <InputBox>
            <InputLabel>이메일 주소</InputLabel>
            <AccountInput
              {...register("email", {
                name: "email",
                required: "이메일을 입력하세요!",
                // 유효성 검사 파트
                // pattern: {
                //   value: /^[A-Za-z0-9._%+-]+@knu\.ac.kr$/,
                //   message: "wrong input",
                // },
                onChange: (e) => {
                  setIsId(e.target.value);
                },
              })}
              type="text"
            />
          </InputBox>

          <AccountBtn
            texthovercolor={"--color-background"}
            bgcolor={"--color-primary"}
            textcolor={"--color-text"}
          >
            기본 정보 수정하기
          </AccountBtn>
        </ReviseForm>
        <br />
        <br />
        <br />
        <br />
        <ReviseForm onSubmit={handleSubmit2(changePwd)}>
          <InputBox>
            <InputLabel>현재 비밀번호</InputLabel>
            <AccountInput
              {...register2("currendPwd", {
                name: "currentPwd",
                // 유효성 검사 파트
                // pattern: {
                //   value: /^[A-Za-z0-9._%+-]+@knu\.ac.kr$/,
                //   message: "wrong input",
                // },
                onChange: (e) => {
                  setIsCurrentPwd(e.target.value);
                },
              })}
              placeholder="현재 비밀번호를 입력해주세요."
            />
          </InputBox>
          <InputBox>
            <InputLabel>새 비밀번호 확인</InputLabel>
            <AccountInput
              type="text"
              {...register2("newPwd", {
                name: "newPwd",
                required: "비밀번호를 입력하세요",
                // 유효성 검사 파트
                // pattern: {
                //   value: /^[A-Za-z0-9._%+-]+@knu\.ac.kr$/,
                //   message: "wrong input",
                // },
                onChange: (e) => {
                  setIsNewPwd(e.target.value);
                },
              })}
              placeholder="새 비밀번호를 입력해주세요."
            />
          </InputBox>
          <AccountBtn
            texthovercolor={"--color-background"}
            bgcolor={"--color-primary"}
            textcolor={"--color-text"}
          >
            비밀번호 수정하기
          </AccountBtn>
        </ReviseForm>
      </MyPageContentBox>
    </MyPageBox>
  );
}
