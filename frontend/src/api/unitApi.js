import axios from "axios";
import { API_URL } from "../static";

const UNIT_URL = `${API_URL}/courses`;
const UNIT_TAIL_URL = "/units";

export async function getUnitVideoApi(unitId, accessToken) {
  return await axios.get(`${UNIT_URL}${UNIT_TAIL_URL}/${unitId}`, {
    headers: {
      "Content-Type": "application/json",
      "X-AUTH-TOKEN": accessToken,
    },
  });
}
