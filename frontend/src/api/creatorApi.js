import axios from "axios";
import { API_URL } from "../static";
const FILE_PATH = "/file";
const SINGLE_IMG_URL = "/image";
const MULTI_IMG_URL = "/image/muli";
const UNIT_URL = "/units";
const VIDEO_URL = "/video";
const CREATE_URL = "/courses";
const CURRICULUM_URL = "/curriculum";
const FILE_URL = API_URL + FILE_PATH;

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
    API_URL + CREATE_URL,
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

export async function createUnitsApi(accessToken, courseId, unitsValue) {
  const params = {
    title: "스프링 빈에 대한 이해",
    description: "스프링 빈에 대해 알아봅시다.",
    videoUrl: "/static/videos/sample-m3u8/sample.m3u8",
  };
  return await axios.post(
    `${API_URL}${CREATE_URL}/${courseId}${UNIT_URL}`,
    {},
    {
      headers: {
        ACCESS: accessToken,
        "Content-Type": "application/json",
      },
    },
    { params }
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
    `${API_URL}${CREATE_URL}/${courseId}${CURRICULUM_URL}`,
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
