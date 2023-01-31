import { useState } from "react";
import { useQuery } from "react-query";
import { useRecoilValue } from "recoil";
import styled from "styled-components";
import { myCourseApi } from "../../api/myPageApi";
import { getAccessTokenSelector, getMemberIdSelector } from "../../atom";
import {
  MyPageBox,
  MyPageContentBox,
  MyPageTitle,
} from "../../style/MypageComponentsCss";

export default function Comment() {
  const memberId = useRecoilValue(getMemberIdSelector);
  const accessToken = useRecoilValue(getAccessTokenSelector);

  const myCourses = useQuery(
    ["myCourseList", memberId],
    () => {
      return myCourseApi(memberId, accessToken);
    },
    {
      enabled: !!memberId,
      onSuccess: (res) => {
        console.log(res);
      },
      onError: (err) => {
        console.error("에러 발생했지롱");
      },
    }
  );

  return (
    <MyPageBox>
      <MyPageTitle>나의 댓글들</MyPageTitle>
      <MyPageContentBox></MyPageContentBox>
    </MyPageBox>
  );
}
