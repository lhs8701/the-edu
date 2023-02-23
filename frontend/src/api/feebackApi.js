import axios from "axios";
import { API_URL } from "../static";

const RECORD_URL = API_URL + "/feedback";
const UNIT_URL = "/unit";

export async function getFeedbackApi(accessToken, unitId) {
  return await axios.get(`${RECORD_URL}${UNIT_URL}/${unitId}`, {
    headers: {
      "Content-Type": "application/json",
      ACCESS: accessToken,
    },
  });
}

export async function postFeedbackApi(
  accessToken,
  unitId,
  thumbsUp,
  thumbsDown
) {
  return await axios.post(
    `${RECORD_URL}${UNIT_URL}/${unitId}`,
    { thumbsDown: thumbsDown, thumbsUp: thumbsUp },
    {
      headers: {
        "Content-Type": "application/json",
        ACCESS: accessToken,
      },
    }
  );
}
