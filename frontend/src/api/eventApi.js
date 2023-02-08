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
export async function createEventApi(accessToken) {
  return await axios.post(`${EVENT_URL}`);
}

export async function getDetailEventApi(eventId) {
  return await axios.get(`${EVENT_URL}/${eventId}`);
}
