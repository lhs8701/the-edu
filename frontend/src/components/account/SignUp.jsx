import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import Modal from "react-modal";
import {
  AccountBtn,
  AccountForm,
  AccountInput,
  AccountTitle,
  AccountWrapper,
  ErrorMessage,
  InputBox,
  InputLabel,
} from "../../style/AccountComponentCss";
import { PrivacyTerm, Term } from "./Term";
import { useForm } from "react-hook-form";
import { signUp } from "../../api/authApi";
import Helmet from "react-helmet";
import { CenterDiv } from "../../style/CommonCss";
import {
  MAX_NAME_LENGTH,
  MAX_PWD_LENGTH,
  MIN_NAME_LENGTH,
  MIN_PWD_LENGTH,
} from "../../static";
import { getLoginState } from "../../atom";
import { useRecoilValue } from "recoil";

const TermBox = styled.div`
  margin-top: 20px;
  margin-bottom: 20px;
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  position: relative;
`;

const TermBtn = styled.div`
  cursor: pointer;
  justify-content: center;
  display: flex;
  align-items: center;
  color: var(--color-black);
  font-size: 1.5rem;
  font-weight: var(--weight-point);
  height: 35px;
  width: 75px;
  &:active {
    font-size: 1rem;
  }
  &:hover {
    color: var(--color-primary);
  }
`;

const TermLabel = styled.label`
  &::before {
    content: "";
    background-color: ${(props) =>
      props.checked ? "#da2127" : "rgba(97, 97, 97, 0.2)"};
    width: 25px;
    height: 25px;
    border-radius: 8px;
    margin-right: 15px;
  }
  display: flex;
  align-items: center;
  font-weight: var(--weight-point);
`;

const TermCheck = styled.input`
  display: none;
  height: 100%;
`;

const TermErrMessage = styled(ErrorMessage)`
  margin-top: 0;
`;

export default function SignUp() {
  const loginState = useRecoilValue(getLoginState);
  const [isModalOpen, setModalOpen] = useState(false);
  const [isId, setIsId] = useState("");
  const [isPwd, setIsPwd] = useState("");
  const [isName, setIsName] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const [isCheck2, setIsCheck2] = useState({
    inputCheck: false,
    lookCheck: false,
  });
  const [isCheck, setIsCheck] = useState({
    inputCheck: false,
    lookCheck: false,
  });
  const navigate = useNavigate();
  const {
    register,
    handleSubmit,
    formState: { errors },
    watch,
  } = useForm({
    mode: "onBlur",
    defaultValues: {
      inputValue: "",
    },
    reValidateMode: "onBlur",
  });

  const submit = () => {
    setIsLoading(true);
    signUp({
      account: isId,
      password: isPwd,
      nickname: isName,
    })
      .then(() => {
        alert("이메일 인증 후 로그인을 할 수 있어요.");
        navigate("/account/login");
      })
      .catch((err) => {
        setIsLoading(false);

        if (err.response.data.code === -6002) {
          alert("이미 가입된 정보입니다.");
          navigate("/account/login");
        } else if (err.response.status === 500) {
          alert("서버 오류입니다.");
        }
      });
  };

  return (
    <>
      <Helmet>
        <script src="https://cdn.toss.im/cert/v1"></script>
      </Helmet>
      <AccountWrapper>
        <AccountTitle>회원가입</AccountTitle>
        <AccountForm onSubmit={handleSubmit(submit)}>
          <InputBox>
            <InputLabel htmlFor="name">성명</InputLabel>
            <AccountInput
              {...register("name", {
                name: "name",
                required: "성명을 입력하세요!",
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
              placeholder="성명을 입력해주세요."
            />
            <ErrorMessage>{errors?.name?.message}</ErrorMessage>
          </InputBox>
          <InputBox>
            <InputLabel>이메일 주소</InputLabel>
            <AccountInput
              {...register("email", {
                name: "email",
                required: "이메일을 입력하세요!",
                pattern: {
                  value:
                    /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/,
                  message: "잘못된 이메일 형식입니다.",
                },
                onChange: (e) => {
                  setIsId(e.target.value);
                },
              })}
              type="text"
              placeholder="이메일 주소를 입력해주세요."
            />
            <ErrorMessage>{errors?.email?.message}</ErrorMessage>
          </InputBox>
          <InputBox>
            <InputLabel>비밀번호</InputLabel>
            <AccountInput
              {...register("pwd", {
                name: "pwd",
                required: "비밀번호를 입력하세요!",
                pattern: {
                  value:
                    /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,16}$/,
                  message:
                    "비밀번호는 8글자 이상, 숫자, 영문, 특수문자를 포함해 8글자 이상 16글자이하입니다.",
                },
                minLength: {
                  value: MIN_PWD_LENGTH,
                  message: "8글자 이상 입력해주세요.",
                },
                maxLength: {
                  value: MAX_PWD_LENGTH,
                  message: "16글자 이하로 입력해주세요.",
                },
                onChange: (e) => {
                  setIsPwd(e.target.value);
                },
              })}
              type="password"
              placeholder="비밀번호를 입력해주세요."
            />
            <ErrorMessage>{errors?.pwd?.message}</ErrorMessage>
          </InputBox>
          <InputBox>
            <InputLabel>비밀번호 확인</InputLabel>
            <AccountInput
              {...register("pwdConfirm", {
                name: "pwdConfirm",
                required: "비밀번호를 한번 더 입력해주세요!",
                validate: (val) => {
                  if (watch("pwd") !== val) {
                    return "비밀번호가 서로 다릅니다!";
                  }
                },
              })}
              type="password"
              placeholder="비밀번호를 입력해주세요."
            />
            <ErrorMessage>{errors?.pwdConfirm?.message}</ErrorMessage>
          </InputBox>

          <TermBox>
            <TermLabel checked={isCheck.inputCheck}>
              <TermCheck
                {...register("service", {
                  name: "service",
                  required: "이용약관에 동의해주세요!",
                  onChange: (e) => {
                    setIsCheck((prev) => ({
                      ...prev,
                      inputCheck: !prev.inputCheck,
                    }));
                  },
                })}
                type="checkbox"
              />
              [필수] 서비스 이용약관 동의
            </TermLabel>
            <TermErrMessage>{errors?.service?.message}</TermErrMessage>
            <TermBtn
              onClick={() => {
                setIsCheck((current) => ({
                  ...current,
                  lookCheck: true,
                }));
                setIsCheck2((current) => ({
                  ...current,
                  lookCheck: false,
                }));

                setModalOpen(true);
              }}
            >
              >
            </TermBtn>
          </TermBox>
          <TermBox>
            <TermLabel checked={isCheck2.inputCheck}>
              <TermCheck
                {...register("service2", {
                  name: "service2",
                  required: "개인정보 약관에 동의해주세요!",
                  onChange: (e) => {
                    setIsCheck2((prev) => ({
                      ...prev,
                      inputCheck: !prev.inputCheck,
                    }));
                  },
                })}
                type="checkbox"
              />
              [필수] 개인정보 수집 및 이용에 대한 동의
            </TermLabel>
            <TermErrMessage>{errors?.service2?.message}</TermErrMessage>
            <TermBtn
              onClick={() => {
                setIsCheck((current) => ({
                  ...current,
                  lookCheck: false,
                }));
                setIsCheck2((current) => ({
                  ...current,
                  lookCheck: true,
                }));
                setModalOpen(true);
              }}
            >
              >
            </TermBtn>
          </TermBox>
          <br />
          {isLoading ? (
            <CenterDiv>진행 중...</CenterDiv>
          ) : (
            <AccountBtn
              texthovercolor={"--color-background"}
              bgcolor={"--color-primary"}
              textcolor={"--color-text"}
            >
              회원가입
            </AccountBtn>
          )}
        </AccountForm>
        <Modal
          isOpen={isModalOpen}
          ariaHideApp={false}
          onRequestClose={() => setModalOpen(false)}
          style={{
            overlay: {
              position: "fixed",
              top: 0,
              left: 0,
              backgroundColor: "rgba(0, 0, 0, 0.2)",
              width: "100%",
              height: "100%",
            },
            content: {
              overflow: "hidden",
              width: "35%",
              height: "75%",
              top: "10%",
              left: "30.5%",
            },
          }}
        >
          {isCheck.lookCheck ? <Term setModalOpen={setModalOpen} /> : null}
          {isCheck2.lookCheck ? (
            <PrivacyTerm setModalOpen={setModalOpen} />
          ) : null}
        </Modal>
      </AccountWrapper>
    </>
  );
}
