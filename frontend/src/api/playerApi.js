import axios from "axios";
import { API_URL } from "../static";

const RECORD_URL = API_URL + "/record";
const UNITS_URL = "/units";
const COMPLETE_URL = "/complete";

export async function getLatestRecordApi(accessToken, unitId) {
  return await axios.get(`${RECORD_URL}${UNITS_URL}/${unitId}`, {
    headers: {
      "Content-Type": "application/json",
      "X-AUTH-TOKEN": accessToken,
    },
  });
}

export async function postMyRecordApi(accessToken, unitId, time) {
  return await fetch(`${RECORD_URL}${UNITS_URL}/${unitId}`, {
    method: "post",
    headers: {
      "X-AUTH-TOKEN": accessToken,
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      time: time,
    }),
    keepalive: true,
  });
}

export async function postWatchAllApi(accessToken, unitId) {
  return await axios.post(
    `${RECORD_URL}${COMPLETE_URL}${UNITS_URL}/${unitId}`,
    {
      headers: {
        "Content-Type": "application/json",
        "X-AUTH-TOKEN": accessToken,
      },
    }
  );
}
