import { isAxiosError } from "axios";
import { useEffect, useLayoutEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { useQuery } from "react-query";
import { useRecoilValue } from "recoil";
import styled from "styled-components";
import { queryClient } from "../..";
import { myInfoApi, revisemyInfoApi } from "../../api/myPageApi";
import { getAccessTokenSelector, getMemberIdSelector } from "../../atom";
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

const TeleInput = styled(AccountInput)`
  width: 83%;
`;

const AuthBtn = styled(AccountSmallBtn)``;

const ReviseForm = styled(AccountForm)`
  width: var(--size-revise-coupon-window);
  margin: 0 auto;
`;

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
      onSuccess: ({ email, id, nickname, mobile }) => {
        setIsName(nickname);
        setIsTele(mobile);
        setIsId(email);
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
  const {
    register,
    handleSubmit,
    formState: { errors },
    setError,
    watch,
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

  const submit = () => {
    console.log("fdd");
    const info = {
      nickname: isName,
      email: isId,
    };
    revisemyInfoApi(memberId, accessToken, info)
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

  return (
    <MyPageBox>
      <MyPageTitle>개인정보 수정</MyPageTitle>
      <MyPageContentBox>
        <ReviseForm onSubmit={handleSubmit(submit)}>
          <InputBox>
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
          </InputBox>
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
          {/* <InputBox>
            <InputLabel>비밀번호</InputLabel>
            <AccountInput
              type="password"
              placeholder="비밀번호를 입력해주세요."
            />
          </InputBox>
          <InputBox>
            <InputLabel>비밀번호 확인</InputLabel>
            <AccountInput
              type="password"
              placeholder="비밀번호를 입력해주세요."
            />
          </InputBox> */}
          {/* <InputBox>
            <InputLabel>휴대전화 번호</InputLabel>
            <TeleInput
              {...register("tele", {
                name: "tele",
                // 유효성 검사 파트
                // pattern: {
                //   value: /^[A-Za-z0-9._%+-]+@knu\.ac.kr$/,
                //   message: "wrong input",
                // },
                minLength: {
                  value: MIN_TELE_LENGTH,
                  message: "10글자 이상 입력해주세요.",
                },
                maxLength: {
                  value: MAX_TELE_LENGTH,
                  message: "11글자 이하로 입력해주세요.",
                },
                onChange: (e) => {
                  setIsTele(e.target.value);
                },
              })}
              type="tel"
            />
            <button>인증</button>
          </InputBox> */}

          <AccountBtn
            texthovercolor={"--color-background"}
            bgcolor={"--color-primary"}
            textcolor={"--color-text"}
          >
            수정하기
          </AccountBtn>
        </ReviseForm>
      </MyPageContentBox>
    </MyPageBox>
  );
}
