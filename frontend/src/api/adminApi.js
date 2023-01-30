//
import axios from "axios";
import { FORM_ID, TYPEFORM_TOKEN } from "../AuthKey";
import { BASE_URL } from "../static";

const FORM_URL = `https://api.typeform.com/forms/${FORM_ID}/response`;
export async function getFormResponse() {
  return await axios.get(FORM_URL, {
    headers: {
      authorization: `bearer ${TYPEFORM_TOKEN}`,
    },
  });
}
