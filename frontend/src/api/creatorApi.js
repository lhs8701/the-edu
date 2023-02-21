import axios from "axios";
import { API_URL } from "../static";
const FILE_PATH = "/file";
const CREATOR_PATH = "/creators/me";
const CREATOR_URL = API_URL + CREATOR_PATH;
const SINGLE_IMG_URL = "/image";
const MULTI_IMG_URL = "/image/muli";
const UNIT_URL = "/units";
const VIDEO_URL = "/video";
const COURSES_URL = "/courses";
const CURRICULUM_URL = "/curriculum";
const FILE_URL = API_URL + FILE_PATH;
const STANBY_URL = "/standby";
const ACTIVE_URL = "/activate/members";

export async function setUpTicketApi(accessToken, courseId, data) {
  return await axios.post(
    `${API_URL}${COURSES_URL}/${courseId}/tickets`,
    {
      costPrice: data.costPrice,
      discountedPrice: data.discountedPrice,
      coursePeriod: data.coursePeriod,
    },
    {
      headers: {
        ACCESS: accessToken,
      },
    }
  );
}

export async function changeChargeTypeApi(accessToken, courseId, chargeType) {
  return await axios.post(
    `${API_URL}${COURSES_URL}/${courseId}`,
    {},
    {
      headers: {
        ACCESS: accessToken,
      },
      params: {
        chargeType: chargeType,
      },
    }
  );
}

export async function requestCreatorApi(accessToken, subject, career) {
  return await axios.post(
    CREATOR_URL + STANBY_URL,
    { subject: subject, career: career },
    {
      headers: {
        ACCESS: accessToken,
      },
    }
  );
}

export async function getUploadedCoursesApi(accessToken) {
  return await axios.get(CREATOR_URL + COURSES_URL, {
    headers: {
      "Content-Type": "application/json",
      ACCESS: accessToken,
    },
  });
}

export async function activeCreatorApi(accessToken, memberId) {
  return await axios.post(
    `${CREATOR_URL}${ACTIVE_URL}/${memberId}`,
    {},
    {
      headers: {
        ACCESS: accessToken,
      },
    }
  );
}

export async function uploadImageApi(file, accessToken) {
  const formData = new FormData();
  formData.append("multipartFile", file);
  return await axios.post(FILE_URL + SINGLE_IMG_URL, formData, {
    headers: {
      ACCESS: accessToken,
    },
  });
}

export async function createCourseApi(accessToken, courseValue) {
  return await axios.post(
    API_URL + COURSES_URL,
    {
      title: courseValue.title,
      description: courseValue.detail,
      category: courseValue.category,
      price: Number(courseValue.price),
      thumbnailImage: courseValue.thumbUrl,
      descriptionImageUrls: courseValue.descriptionImageUrls,
    },
    {
      headers: {
        ACCESS: accessToken,
        "Content-Type": "application/json",
      },
    }
  );
}

export async function createUnitsApi(
  accessToken,
  courseId,
  title,
  description,
  url
) {
  return await axios.post(
    `${API_URL}${COURSES_URL}/${courseId}${UNIT_URL}`,
    { title: title, description: description, videoUrl: url },
    {
      headers: {
        ACCESS: accessToken,
        "Content-Type": "application/json",
      },
    }
  );
}

export async function uploadVideoApi(accessToken, file) {
  const formData = new FormData();
  formData.append("multipartFile", file);
  return await axios.post(FILE_URL + VIDEO_URL, formData, {
    headers: {
      ACCESS: accessToken,
    },
  });
}

export async function createCourseCurriculumApi(
  accessToken,
  courseId,
  curriValue
) {
  return await axios.post(
    `${API_URL}${COURSES_URL}/${courseId}${CURRICULUM_URL}`,
    {
      chapters: curriValue,
    },
    {
      headers: {
        ACCESS: accessToken,
        "Content-Type": "application/json",
      },
    }
  );
}
