import axios from "axios";
import { BASE_URL } from "../static";
const FILE_PATH = "/file"
const SINGLE_IMG_URL = "/image";
const MULTI_IMG_URL = "/image/muli";
const CREATE_URL="/courses"
const FILE_URL = BASE_URL+FILE_PATH;

export async function uploadImageApi(file, accessToken) {
  const formData = new FormData();
  formData.append("multipartFile", file);
  return await axios.post(FILE_URL+SINGLE_IMG_URL, formData, {
    headers: {
      "X-AUTH-TOKEN": accessToken,
    },
  });
}


export async function createCourseApi(accessToken){
  return await axios.post(BASE_URL + CREATE_URL,{
    "title": "스프링 핵심 원리 - 기본편",
  "description": "스프링 입문자가 예제를 만들어가면서 스프링의 핵심 원리를 이해할 수 있습니다.",
  "category": "백엔드",
  "price": 143000,
  "thumbnailImage": "string",
  "descriptionImageUrls": [
    "string"
  ]
  }, {
    headers: {
      "X-AUTH-TOKEN": accessToken,
      "Content-Type": "application/json",      
    },
  });
}