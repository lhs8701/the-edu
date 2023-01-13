import axios from "axios";

const BASE_URL = "https://api.coinpaprika.com/v1";
export async function fetchCoins() {
  return axios.get(`${BASE_URL}/coins`);
}
