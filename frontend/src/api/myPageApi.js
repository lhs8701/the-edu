import axios from "axios";

const BASE_URL = "http://218.38.127.26:8080/api/students";
const WISH_URL = "/courses/wish";
const MYCOURSE_URL = "/courses";

export async function myCourseApi(memberId, accessToken) {
  const data = await axios.get(`${BASE_URL}/${memberId}${MYCOURSE_URL}`, {
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
  const data = await axios.get(`${BASE_URL}/${memberId}${WISH_URL}`, {
    headers: {
      "Content-Type": "application/json",
      "Access-Control-Allow-Credentials": true,
      "Access-Control-Allow-Origin": "*",
      "X-AUTH-TOKEN": accessToken,
    },
  });
  return data.data;
}
