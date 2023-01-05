import styled from "styled-components";

export const InputLabel = styled.label`
  font-size: var(--size-login-btn);
  font-weight: var(--weight-normal);
`;

export const AccountTitle = styled.div`
  font-size: 1.9rem;
  font-weight: var(--weight-point);
  text-align: center;
  width: 100%;
`;

export const AccountInput = styled.input`
  width: 100%;
  font-size: var(--size-login-btn);
  height: 40px;
  border: none;
  border-bottom: 1.5px solid var(--color-gray);
  &:focus {
    outline: none;
    /* border-bottom: 1.5px solid var(--color-primary); */
    border-bottom: 1.5px solid var(--color-input-focus);
  }
  &::placeholder {
    color: var(--color-gray);
    padding-left: 1px;
    font-weight: var(--weight-normal);
  }
`;

export const AccountBtn = styled.button`
  border: none;
  background-color: var(${(props) => props.bgcolor});
  color: var(${(props) => props.textcolor});
  font-size: 1.2rem;
  font-weight: var(--weight-point);
  height: 35px;
  border-radius: 7px;
  width: 100%;
  &:active {
    transform: scale(0.9);
  }
  &:hover {
    /* background-color: ${(props) => props.bghovercolor}; */
    color: var(${(props) => props.texthovercolor});
  }
`;

export const AccountForm = styled.form`
  width: 100%;
  height: 100%;
`;

export const AccountWrapper = styled.div`
  width: 100%;
  height: 100%;
`;
