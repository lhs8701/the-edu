import axios from "axios";
import { BASE_URL } from "../static";

const COURSE_URL = `${BASE_URL}/courses`;
const INQUIRE_URL = "/inquiries";
const CATEGORY_URL = "/category";
const WISHCHECK_URL = "/wish/check";
const WISH_URL = "/wish";
const ENROLL_URL = "/enroll";
const SEARCH_URL = "/keyword";

export async function searchApi(pageParam, keyword) {
  const params = {
    page: pageParam,
    size: 10,
    sort: "ASC",
  };
  const data = await axios.get(`${COURSE_URL}${SEARCH_URL}/${keyword}`, {
    params,
  });
  return data.data;
}

export async function enrollApi(memberId, courseId, accessToken) {
  return await axios.post(
    COURSE_URL + ENROLL_URL,
    {
      memberId: memberId,
      courseId: Number(courseId),
    },
    {
      headers: {
        "Content-Type": "application/json",
        "X-AUTH-TOKEN": accessToken,
      },
    }
  );
}

export async function courseWishCheckApi(memberId, courseId, accessToken) {
  return await axios.post(
    COURSE_URL + WISHCHECK_URL,
    {
      memberId: memberId,
      courseId: courseId,
    },
    {
      headers: {
        "Content-Type": "application/json",
        "X-AUTH-TOKEN": accessToken,
      },
    }
  );
}

export async function courseWishApi(memberId, courseId, accessToken) {
  return await axios.post(
    COURSE_URL + WISH_URL,
    {
      memberId: memberId,
      courseId: courseId,
    },
    {
      headers: {
        "Content-Type": "application/json",
        "X-AUTH-TOKEN": accessToken,
      },
    }
  );
}

export async function courseApi(courseId) {
  const data = await axios.get(`${COURSE_URL}/${courseId}`);
  return data.data;
}

export async function getcourseReviewsApi(courseId) {
  const data = await axios.get(`${COURSE_URL}/${courseId}/reviews`);
  return data.data;
}

export async function getcourseInquiriessApi(courseId) {
  const data = await axios.get(`${COURSE_URL}/${courseId}${INQUIRE_URL}`);
  return data.data;
}

export async function getCategoryListApi(pageParam, category) {
  const params = {
    page: pageParam,
    size: 10,
    sort: "ASC",
  };
  const data = await axios.get(`${COURSE_URL}${CATEGORY_URL}/${category}`, {
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
    COURSE_URL + INQUIRE_URL,
    {
      memberId: memberId,
      courseId: courseId,
      content: content,
      score: 0,
    },
    {
      headers: {
        "Content-Type": "application/json",
        "X-AUTH-TOKEN": accessToken,
      },
    }
  );
}
