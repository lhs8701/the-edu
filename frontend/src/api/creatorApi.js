import axios from "axios";
import { BASE_URL } from "../static";

const SINGLE_IMG_URL = "/single";
const MuLTI_IMG_URL = "/multiple";
export async function uploadImageApi(file, accessToken) {
  const formData = new FormData();
  formData.append("multipartFile", file);

  return await axios.post(BASE_URL + SINGLE_IMG_URL, formData, {
    headers: {
      "Content-Type": "application/json",
      "X-AUTH-TOKEN": accessToken,
    },
  });
}

export async function uploadImagesApi(formdata, accessToken) {
  return await axios.post(BASE_URL + MuLTI_IMG_URL, formdata, {
    headers: {
      "Content-Type": "application/json",
      "X-AUTH-TOKEN": accessToken,
    },
  });
}
