import axios from "axios";
const BASE_URL = "http://218.38.127.26:8080/api/courses";

export async function courseApi(courseId) {
  const data = await axios.get(`${BASE_URL}/${courseId}`);
  return data.data;
}

export async function getcourseReviewsApi(courseId) {
  const data = await axios.get(`${BASE_URL}/${courseId}/reviews`);
  return data.data;
}

export async function getcourseInquiriessApi(courseId) {
  const data = await axios.get(`${BASE_URL}/${courseId}/inquiries`);
  return data.data;
}
