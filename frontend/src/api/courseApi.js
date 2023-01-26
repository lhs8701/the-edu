import axios from "axios";

const BASE_URL = "http://218.38.127.26:8080/api/courses";
const INQUIRE_URL = "/inquiries";
const CATEGORY_URL = "/category";

export async function courseApi(courseId) {
  const data = await axios.get(`${BASE_URL}/${courseId}`);
  return data.data;
}

export async function getcourseReviewsApi(courseId) {
  const data = await axios.get(`${BASE_URL}/${courseId}/reviews`);
  return data.data;
}

export async function getcourseInquiriessApi(courseId) {
  const data = await axios.get(`${BASE_URL}/${courseId}${INQUIRE_URL}`);
  return data.data;
}

export async function getCategoryListApi(pageParam, category) {
  const params = {
    page: pageParam,
    size: 10,
    sort: "ASC",
  };
  const data = await axios.get(`${BASE_URL}${CATEGORY_URL}/${category}`, {
    params,
  });
  return data.data;
}

export async function postcourseInquiriessApi(
  accessToken,
  courseId,
  content,
  memberId
) {
  return await axios.post(
    BASE_URL + INQUIRE_URL,
    {
      memberId: memberId,
      courseId: courseId,
      content: content,
      score: 0,
    },
    {
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Credentials": true,
        "Access-Control-Allow-Origin": "*",
        "X-AUTH-TOKEN": accessToken,
      },
    }
  );
}
