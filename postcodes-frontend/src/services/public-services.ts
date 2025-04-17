import axios from 'axios';
import { Postcode, Suburb } from '../types/postcode';

const POSTCODE_URL = 'http://127.0.0.1:8080/postcode';
const SUBURB_URL = 'http://127.0.0.1:8080/suburb';

export const getAllPostcodes = async () => {
  const response = await axios.get<Postcode[]>(POSTCODE_URL);
  return response.data;
};
export const getAllSuburbs = async () => {
  const response = await axios.get<Suburb[]>(SUBURB_URL);
  return response.data;
};
