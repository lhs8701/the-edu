import axios from "axios";
import { BASE_URL } from "../static";

const STUDNET_URL = `${BASE_URL}/students`;
const MEMBER_URL = `${BASE_URL}/members`;
const WISH_URL = "/courses/wish";
const MYCOURSE_URL = "/courses";
const PROFILE_URL = "/profile";

export async function myCourseApi(memberId, accessToken) {
  const data = await axios.get(`${STUDNET_URL}/${memberId}${MYCOURSE_URL}`, {
    headers: {
      "Content-Type": "application/json",
      "Access-Control-Allow-Credentials": true,
      "Access-Control-Allow-Origin": "*",
      "X-AUTH-TOKEN": accessToken,
    },
  });
  return data.data;
}

export async function wishCourseApi(memberId, accessToken) {
  const data = await axios.get(`${STUDNET_URL}/${memberId}${WISH_URL}`, {
    headers: {
      "Content-Type": "application/json",
      "Access-Control-Allow-Credentials": true,
      "Access-Control-Allow-Origin": "*",
      "X-AUTH-TOKEN": accessToken,
    },
  });
  return data.data;
}

export async function myInfoApi(memberId, accessToken) {
  const data = await axios.get(`${MEMBER_URL}/${memberId}${PROFILE_URL}`, {
    headers: {
      "Content-Type": "application/json",
      "Access-Control-Allow-Credentials": true,
      "Access-Control-Allow-Origin": "*",
      "X-AUTH-TOKEN": accessToken,
    },
  });
  return data.data;
}

export async function revisemyInfoApi(memberId, accessToken, info) {
  const data = await axios.patch(
    `${MEMBER_URL}/${memberId}${PROFILE_URL}`,
    { nickname: info.nickname, email: info.email },
    {
      headers: {
        "Access-Control-Allow-Credentials": true,
      "Access-Control-Allow-Origin": "*",
        "Content-Type": "application/json",
        "X-AUTH-TOKEN": accessToken,
      },
    }
  );
  return data.data;
}
