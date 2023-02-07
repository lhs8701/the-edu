import axios from "axios";
import { API_URL } from "../static";

const STUDNET_URL = `${API_URL}/students`;
const MEMBER_URL = `${API_URL}/members`;
const WISH_URL = "/courses/wish";
const MYCOURSE_URL = "/courses";
const PROFILE_URL = "/profile";
const ME_URL = "/me";
export async function myCourseApi(memberId, accessToken) {
  const data = await axios.get(`${STUDNET_URL}/${memberId}${MYCOURSE_URL}`, {
    headers: {
      "Content-Type": "application/json",
      "Access-Control-Allow-Credentials": true,
      "Access-Control-Allow-Origin": "*",
      ACCESS: accessToken,
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
      ACCESS: accessToken,
    },
  });
  return data.data;
}

export async function myInfoApi(memberId, accessToken) {
  const data = await axios.get(`${MEMBER_URL}${ME_URL}${PROFILE_URL}`, {
    headers: {
      "Content-Type": "application/json",
      "Access-Control-Allow-Credentials": true,
      "Access-Control-Allow-Origin": "*",
      ACCESS: accessToken,
    },
  });
  return data.data;
}

export async function revisemyInfoApi(memberId, accessToken, info) {
  const data = await axios.patch(
    `${MEMBER_URL}${ME_URL}${PROFILE_URL}`,
    { nickname: info.nickname, email: info.email },
    {
      headers: {
        "Access-Control-Allow-Credentials": true,
        "Access-Control-Allow-Origin": "*",
        "Content-Type": "application/json",
        ACCESS: accessToken,
      },
    }
  );
  return data.data;
}
