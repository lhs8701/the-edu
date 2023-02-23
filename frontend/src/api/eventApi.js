import axios from "axios";
import { API_URL } from "../static";

const EVENT_URL = `${API_URL}/events`;
const ONGOING_URL = "/ongoing";
const CLOSED_URL = "/closed";

export async function getOngoingEventApi() {
  return await axios.get(`${EVENT_URL}${ONGOING_URL}`);
}
export async function getClosedEventApi() {
  return await axios.get(`${EVENT_URL}${CLOSED_URL}`);
}
export async function createEventApi(accessToken, data) {
  return await axios.post(`${EVENT_URL}`, data, {
    headers: {
      ACCESS: accessToken,
      "Content-Type": "application/json",
    },
  });
}

export async function getDetailEventApi(eventId) {
  return await axios.get(`${EVENT_URL}/${eventId}`);
}

export async function deleteEventApi(eventId, accessToken) {
  return await axios.delete(`${EVENT_URL}/${eventId}`, {
    headers: {
      ACCESS: accessToken,
      "Content-Type": "application/json",
    },
  });
}
