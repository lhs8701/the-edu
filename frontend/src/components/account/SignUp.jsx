import { useEffect, useState } from "react";
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
import { postTossTxId, signUp, successTossCert } from "../../api/authApi";
import { useScript } from "../../hook";
import Helmet from "react-helmet";
import { API_URL } from "../../static";

const MIN_PWD_LENGTH = 8;
const MAX_NAME_LENGTH = 16;
const MIN_NAME_LENGTH = 2;
const MAX_PWD_LENGTH = 16;
const MIN_TELE_LENGTH = 10;
const MAX_TELE_LENGTH = 11;

const TeleInput = styled(AccountInput)`
  width: 100%;
`;

const AuthBtn = styled.div`
  border: none;
  background-color: var(--color-primary);
  color: var(--color-black);
  font-size: var(--size-login-btn);
  font-weight: var(--weight-point);
  height: 35px;
  border-radius: 7px;
  width: 75px;
  display: flex;
  align-items: center;
  justify-content: center;

  &:active {
    transform: scale(0.9);
  }
  &:hover {
    color: var(--color-background);
  }
`;

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

const TeleInputBox = styled(InputBox)`
  display: flex;
  justify-content: space-between;

  align-items: baseline;
  height: auto;
`;

export default function SignUp() {
  const [isModalOpen, setModalOpen] = useState(false);
  const [isId, setIsId] = useState("");
  const [isPwd, setIsPwd] = useState("");
  const [isName, setIsName] = useState("");
  const [isCert, setIsCert] = useState(false);
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
    if (!isCert) {
      alert("휴대전화 인증이 되지 않았습니다.");
      return;
    }
    signUp({
      account: isId,
      password: isPwd,
      name: isName,
      nickname: isName,
      mobile: "010-1234-5578",
      birthDate:
        isBirth.substr(0, 4) +
        "." +
        isBirth.substr(5, 2) +
        "." +
        isBirth.substr(8, 2),
    })
      .then(() => {
        alert("회원 가입이 되셨습니다.");
        navigate("/account/login");
      })
      .catch((err) => {
        console.log(err.response.status);
        if (err.response.status == 401) {
          alert("이미 가입된 정보입니다.");
          navigate("/account/login");
        } else {
          alert("잘못된 정보입니다. 정확한 정보를 입력해주세요.");
        }
      });
  };

  function AuthButton() {
    const [loading, error] = useScript("https://cdn.toss.im/cert/v1");
    // if (error) return <p>Error!</p>;
    // if (loading) return <p>Loading...</p>;

    return (
      <AuthBtn
        onClick={() => {
          // eslint-disable-next-line no-undef
          var tossCert = TossCert(); // 1. TossCert객체를 초기화합니다.
          tossCert.preparePopup(); // 2. 팝업차단 방지를 위해 비동기 호출 전에 빈 팝업을 먼저 띄웁니다.
          // 3. 서버 투 서버의 인증요청 API를 호출합니다. 원하는 방식으로 적절히 수정해주세요.
          // eslint-disable-next-line no-undef
          postTossTxId()
            .then(({ data }) => {
              tossCert.start({
                authUrl: data.success.authUrl,
                txId: data.success.txId,
                onSuccess: function () {
                  successTossCert(data.success.txId)
                    .then(() => {
                      setIsCert(true);
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
        인증
      </AuthBtn>
    );
  }

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
                required: "비밀번호를 한번 더 입력해주세요!",
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

          <TeleInputBox>
            <InputLabel>휴대전화 번호</InputLabel>
            <AuthButton />
          </TeleInputBox>
          <br />
          <br />
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
    </>
  );
}
