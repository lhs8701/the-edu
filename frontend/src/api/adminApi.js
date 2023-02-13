import axios from "axios";
import { BASE_URL } from "../static";

const ADMIN_URL = `${BASE_URL}/admin-api`;
const GET_USERS_URL = `${ADMIN_URL}/members`;
const GET_CREATOR_STANBY_URL = `${ADMIN_URL}/creators/standby`;
const CREATOR_URL = `${ADMIN_URL}/creators`;
export async function getAllUsersApi(accessToken) {
  return await axios.get(GET_USERS_URL, {
    headers: {
      "Content-Type": "application/json",
      ACCESS: accessToken,
    },
  });
}

export async function getAllCreatorStanbyApi(accessToken) {
  return await axios.get(GET_CREATOR_STANBY_URL, {
    headers: {
      "Content-Type": "application/json",
      ACCESS: accessToken,
    },
  });
}

export async function getAllCreatorsApi(accessToken) {
  return await axios.get(CREATOR_URL, {
    headers: {
      "Content-Type": "application/json",
      ACCESS: accessToken,
    },
  });
}

export async function activateCreator(accessToken, creatorId) {
  return await axios.post(
    `${CREATOR_URL}/${creatorId}/active`,
    {},
    {
      headers: {
        "Content-Type": "application/json",
        ACCESS: accessToken,
      },
    }
  );
}
