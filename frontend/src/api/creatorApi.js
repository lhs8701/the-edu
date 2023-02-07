import axios from "axios";
import { API_URL } from "../static";
const FILE_PATH = "/file";
const SINGLE_IMG_URL = "/image";
const MULTI_IMG_URL = "/image/muli";
const CREATE_URL = "/courses";
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
