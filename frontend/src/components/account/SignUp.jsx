import { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import Modal from "react-modal";
import {
  AccountBtn,
  AccountForm,
  AccountInput,
  AccountSmallBtn,
  AccountTitle,
  AccountWrapper,
  ErrorMessage,
  InputBox,
  InputLabel,
} from "../../style/AccountComponentCss";
import Term from "./Term";
import { useForm } from "react-hook-form";
import { signUp } from "../../api/authApi";

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
  const [isModalOpen, setModalOpen] = useState(false);
  const [isId, setIsId] = useState("");
  const [isPwd, setIsPwd] = useState("");
  const [isName, setIsName] = useState("");
  const [isTele, setIsTele] = useState("");
  const [isBirth, setIsBirth] = useState("");
  const [isCheck, setIsCheck] = useState(false);
  const navigate = useNavigate();
  const {
    register,
    handleSubmit,
    formState: { errors },
    setError,
    watch,
  } = useForm({
    mode: "onBlur",
    defaultValues: {
      inputValue: "",
    },
    reValidateMode: "onBlur",
  });

  const submit = () => {
    signUp({
      account: isId,
      password: isPwd,
      name: isName,
      nickname: isName,
      mobile: "010-1234-5678",
      birthDate:
        isBirth.substr(0, 4) +
        "." +
        isBirth.substr(5, 2) +
        "." +
        isBirth.substr(8, 2),
    })
      .then(() => {
        alert("회원 가입이 되셨습니다.");
        navigate("");
      })
      .catch((err) => {
        console.log(err.response.status);
        if (err.response.status == 401) {
          alert("이미 가입된 정보입니다.");
        } else {
          alert("잘못된 정보입니다. 정확한 정보를 입력해주세요.");
        }
      });
  };
  return (
    <AccountWrapper>
      <AccountTitle>회원가입</AccountTitle>
      <AccountForm onSubmit={handleSubmit(submit)}>
        <InputBox>
          <InputLabel htmlFor="name">성명</InputLabel>
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
              // 유효성 검사 파트
              // pattern: {
              //   value: /^[A-Za-z0-9._%+-]+@knu\.ac.kr$/,
              //   message: "wrong input",
              // },
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
              required: "비밀번호를 다시 입력해주세요!",
              // 유효성 검사 파트
              // pattern: {
              //   value: /^[A-Za-z0-9._%+-]+@knu\.ac.kr$/,
              //   message: "wrong input",
              // },
              validate: (val) => {
                if (watch("pwd") !== val) {
                  return "비밀번호가 서로 다릅니다!";
                }
              },
              minLength: {
                value: 8,
                message: "8글자 이상 입력해주세요.",
              },
              maxLength: {
                value: 16,
                message: "16글자 이하로 입력해주세요.",
              },
              onChange: (e) => {
                // setIsPwdConfirm(e.target.value);
              },
            })}
            type="password"
            placeholder="비밀번호를 입력해주세요."
          />
          <ErrorMessage>{errors?.pwdConfirm?.message}</ErrorMessage>
        </InputBox>
        <InputBox>
          <InputLabel>생년월일</InputLabel>
          <AccountInput
            {...register("birth", {
              name: "birth",
              required: "생년월일을 입력해주세요!",
              onChange: (e) => {
                setIsBirth(e.target.value);
              },
            })}
            type="date"
          />
          <ErrorMessage>{errors?.birth?.message}</ErrorMessage>
        </InputBox>
        <InputBox>
          <InputLabel>휴대전화 번호</InputLabel>
          <br />
          <TeleInput
            {...register("tele", {
              name: "tele",
              required: "휴대전화번호를 입력해주세요!",
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
            placeholder="휴대전화 번호를 입력해주세요."
          />

          <AuthBtn
            onClick={() => {
              console.log("s");
            }}
            disabled={true}
          >
            인증
          </AuthBtn>
          <ErrorMessage>{errors?.tele?.message}</ErrorMessage>
        </InputBox>

        <TermBox>
          <TermLabel checked={isCheck}>
            <TermCheck
              {...register("service", {
                name: "service",
                required: "약관에 동의해주세요!",
                onChange: (e) => {
                  setIsCheck((prev) => !prev);
                },
              })}
              type="checkbox"
            />
            [필수] 서비스 이용약관 동의
          </TermLabel>
          <TermErrMessage>{errors?.service?.message}</TermErrMessage>
          <TermBtn
            onClick={() => {
              setModalOpen(true);
            }}
          >
            >
          </TermBtn>
        </TermBox>
        <TermBox>
          <TermLabel>
            <TermCheck type="checkbox" />
            [필수] 개인정보 수집 및 이용에 대한 동의
          </TermLabel>
          <ErrorMessage></ErrorMessage>
          <TermBtn
            onClick={() => {
              setModalOpen(true);
            }}
          >
            >
          </TermBtn>
        </TermBox>
        <br />
        <AccountBtn
          texthovercolor={"--color-background"}
          bgcolor={"--color-primary"}
          textcolor={"--color-text"}
        >
          회원가입
        </AccountBtn>
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
            width: "25%",
            height: "75%",
            top: "10%",
            left: "37.5%",
          },
        }}
      >
        {<Term setModalOpen={setModalOpen} />}
      </Modal>
    </AccountWrapper>
  );
}
