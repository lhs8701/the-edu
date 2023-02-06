import axios from "axios";
import { API_URL } from "../static";

const QUESTION_URL = `${API_URL}/units`;
const QUESTION_TAIL_URL = "/questions";

export async function myCourseApi(unitId, pageParam, accessToken) {
  const params = {
    page: pageParam,
    size: 10,
    sort: "ASC",
  };
  const data = await axios.get(
    `${QUESTION_URL}/${unitId}${QUESTION_TAIL_URL}`,
    { params },
    {
      headers: {
        "Content-Type": "application/json",
        "X-AUTH-TOKEN": accessToken,
      },
    }
  );
  return data.data;
}

export async function postUnitQuestionApi(accessToken, title, content, unitId) {
  return await axios.post(
    `${QUESTION_URL}/${unitId}${QUESTION_TAIL_URL}`,
    {
      title: title,
      content: content,
    },
    {
      headers: {
        "Content-Type": "application/json",
        "X-AUTH-TOKEN": accessToken,
      },
    }
  );
}

export async function getDetailQuestionApi(questionId, accessToken) {
  return await axios.get(
    `${QUESTION_URL}${QUESTION_TAIL_URL}/${questionId}`,
    {},
    {
      headers: {
        "Content-Type": "application/json",
        "X-AUTH-TOKEN": accessToken,
      },
    }
  );
}
