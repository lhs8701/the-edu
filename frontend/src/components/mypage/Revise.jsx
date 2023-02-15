import { Button } from "@mui/material";
import { isAxiosError } from "axios";
import { useEffect, useLayoutEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { useQuery } from "react-query";
import { useRecoilValue } from "recoil";
import styled from "styled-components";
import { queryClient } from "../..";
import { uploadImageApi } from "../../api/creatorApi";
import { changePwdApi, myInfoApi, revisemyInfoApi } from "../../api/myPageApi";
import { getAccessTokenSelector, getMemberIdSelector } from "../../atom";
import { STATIC_URL } from "../../static";
import {
  AccountBtn,
  AccountForm,
  AccountInput,
  AccountSmallBtn,
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

const ImgUploadBtn = styled.div`
  &:hover {
    color: red;
  }
  cursor: pointer;
`;

const TeleInput = styled(AccountInput)`
  width: 83%;
`;
const ImgBox = styled.div`
  display: flex;
  align-items: center;
`;
const AuthBtn = styled(AccountSmallBtn)``;

const ReviseForm = styled(AccountForm)`
  width: var(--size-revise-coupon-window);
  margin: 0 auto;
`;
const ImgNameTab = styled.div`
  display: flex;
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
      onSuccess: ({ email, id, nickname, mobile, profileImage }) => {
        setIsName(nickname);
        setIsTele(mobile);
        setIsId(email);
        setIsProfileImg(profileImage.mediumFilePath);
        setValue("tele", mobile);
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
  const [isTele, setIsTele] = useState(infos?.data?.mobile);
  const [isCurrentPwd, setIsCurrentPwd] = useState("");
  const [isNewPwd, setIsNewPwd] = useState("");
  const [isProfileImg, setIsProfileImg] = useState(
    infos?.data?.profileImage.mediumFilePath
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
      tele: isTele,
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

  console.log(errors2.currentPwd?.message);

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

  return (
    <MyPageBox>
      <MyPageTitle>개인정보 수정</MyPageTitle>
      <MyPageContentBox>
        <ReviseForm onSubmit={handleSubmit(submit)}>
          <InputBox>
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
                    backgroundColor: "#eeeeee",
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
