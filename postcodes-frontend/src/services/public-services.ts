import axios from 'axios';
import { Postcode, Suburb } from '../types/postcode';

const POSTCODE_URL = 'http://127.0.0.1:8080/postcode';
const SUBURB_URL = 'http://127.0.0.1:8080/suburb';

// export const getAllPostcodes = () => {
//   fetch('http://127.0.0.1:8080/postcode')
//     .then((res) => res.json())
//     .then((data) => console.log(data));
// };

// export const getAllSuburbs = () => {
//   fetch('http://127.0.0.1:8080/suburb')
//     .then((res) => res.json())
//     .then((data) => console.log(data));
// };

export const getAllPostcodes = async () => {
  const response = await axios.get<Postcode[]>(POSTCODE_URL);
  //   if (response.status )
  return response.data;
  //   console.log(response.data);
};
export const getAllSuburbs = async () => {
  const response = await axios.get<Suburb[]>(SUBURB_URL);
  //   console.log(response.data);
  return response.data;
};
