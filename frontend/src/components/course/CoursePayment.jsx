import styled from "styled-components";

const PaymentBox = styled.div`
  width: 28%;
  height: 400px;
  background-color: var(--color-background);
  border-radius: var(--size-border-radius);
  border: 2px solid #d0cfcf;
  box-sizing: border-box;
  padding: 10px;
  position: sticky;
  top: 200px;
`;

export default function CoursePayment() {
  return <PaymentBox>결제창입니다</PaymentBox>;
}
