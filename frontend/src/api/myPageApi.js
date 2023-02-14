import axios from "axios";
import { API_URL } from "../static";

const STUDNET_URL = `${API_URL}/students`;
const MEMBER_URL = `${API_URL}/members`;
const WISH_URL = "/wish/courses";
const MYCOURSE_URL = "/courses";
const PROFILE_URL = "/profile";
const ME_URL = "/me";
const FIND_PWD_URL = "/password/reset";
const CHANGE_PWD_URL = "/password/change";

export async function changePwdApi(accessToken, currentPassword, newPassword) {
  return await axios.post(
    MEMBER_URL + ME_URL + CHANGE_PWD_URL,
    {
      currentPassword: currentPassword,
      newPassword: newPassword,
    },
    {
      headers: {
        "Content-Type": "application/json",
        ACCESS: accessToken,
      },
    }
  );
}

export async function resetPwdApi(account) {
  return await axios.post(
    MEMBER_URL + ME_URL + FIND_PWD_URL,
    {
      account: account,
    },
    {
      headers: {
        "Content-Type": "application/json",
      },
    }
  );
}

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

export async function wishCourseApi(accessToken) {
  const data = await axios.get(`${API_URL}${WISH_URL}`, {
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

export async function revisemyInfoApi(accessToken, info) {
  const data = await axios.patch(
    `${MEMBER_URL}${ME_URL}${PROFILE_URL}`,
    {
      nickname: info.nickname,
      email: info.email,
      profileImage: info.profileImage,
    },
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
