import axios from "axios";
import { API_URL } from "../static";

const QUESTION_URL = `${API_URL}/units`;
const QUESTION_TAIL_URL = "/questions";
const ANSWER_URL = `${API_URL}/questions`;
const ANSWER_TAIL_URL = "/answers";
const MINE_URL = "/mine";
export async function getQuestionListApi(pageParam, unitId, accessToken) {
  const params = {
    page: pageParam,
    size: 10,
    sort: "ASC",
  };
  const data = await axios.get(
    `${QUESTION_URL}/${unitId}${QUESTION_TAIL_URL}`,
    {
      headers: {
        "Content-Type": "application/json",
        ACCESS: accessToken,
      },
    },
    { params }
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
        ACCESS: accessToken,
      },
    }
  );
}

export async function getAllMyQuestionsApi(pageParam, unitId, accessToken) {
  const params = {
    page: pageParam,
    size: 10,
    sort: "ASC",
  };
  const data = await axios.get(
    `${QUESTION_URL}/${unitId}${QUESTION_TAIL_URL}${MINE_URL}`,
    {
      headers: {
        "Content-Type": "application/json",
        ACCESS: accessToken,
      },
    },
    { params }
  );
  return data.data;
}

export async function getDetailQuestionApi(questionId, accessToken) {
  return await axios.get(`${QUESTION_URL}${QUESTION_TAIL_URL}/${questionId}`, {
    headers: {
      "Content-Type": "application/json",
      ACCESS: accessToken,
    },
  });
}

export async function getQuestionAnswerApi(pageParam, questionId, accessToken) {
  const params = {
    page: pageParam,
    size: 100,
    sort: "ASC",
  };
  const data = await axios.get(
    `${ANSWER_URL}/${questionId}${ANSWER_TAIL_URL}`,
    {
      headers: {
        "Content-Type": "application/json",
        ACCESS: accessToken,
      },
    },
    { params }
  );
  return data.data;
}

export async function deleteMyQuestionApi(questionId, accessToken) {
  return await axios.delete(
    `${QUESTION_URL}${QUESTION_TAIL_URL}/${questionId}`,
    {
      headers: {
        "Content-Type": "application/json",
        ACCESS: accessToken,
      },
    }
  );
}
