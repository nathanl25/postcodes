import axios from 'axios';
import { LoginData } from '../schemas/login-schema';
import { Postcode, PostcodeDto, Suburb, SuburbDto } from '../types/postcode';

const AUTH_URL = 'http://127.0.0.1:8080/auth/';
const ADMIN_URL = 'http://127.0.0.1:8080/admin/';

export const getToken = async ({ username, password }: LoginData) => {
  const res = await axios
    .post<string>(
      AUTH_URL + 'login',
      {},
      {
        auth: {
          username: username,
          password: password,
        },
      }
    )
    .then((res) => {
      return res.data;
    })
    .catch((error) => {
      if (error.response) {
        return Promise.reject('Invalid login credentials');
      }
      if (error.request) {
        return Promise.reject('Could not recieve response to server');
      }
      return Promise.reject(error.message);
    });
  return res;
};

export const createPostcode = async (dto: PostcodeDto, jwt: string) => {
  const res = await axios
    .post<Postcode>(ADMIN_URL + 'postcode', dto, {
      headers: {
        Authorization: `Bearer ${jwt}`,
      },
    })
    .then((res) => {
      return res.data;
    })
    .catch((error) => {
      if (error.response) {
        return Promise.reject(error.response.data as string);
      }
      return Promise.reject(error.message);
    });
  return res;
};

export const updatePostcode = async (
  dto: PostcodeDto,
  jwt: string,
  postcode: string
) => {
  const res = await axios
    .post<Postcode>(ADMIN_URL + 'postcode/' + postcode, dto, {
      headers: {
        Authorization: `Bearer ${jwt}`,
      },
    })
    .then((res) => {
      return res.data;
    })
    .catch((error) => {
      if (error.response) {
        return Promise.reject(error.response.data as string);
      }
      return Promise.reject(error.message);
    });
  return res;
};

export const deletePostcode = async (jwt: string, postcode: string) => {
  const res = await axios
    .delete<string>(ADMIN_URL + 'postcode/' + postcode, {
      headers: {
        Authorization: `Bearer ${jwt}`,
      },
    })
    .then((res) => {
      return res.data;
    })
    .catch((error) => {
      if (error.response) {
        return Promise.reject(error.response.data as string);
      }
      return Promise.reject(error.message);
    });
  return res;
};

export const createSuburb = async (dto: SuburbDto, jwt: string) => {
  const res = await axios
    .post<Suburb>(ADMIN_URL + 'suburb', dto, {
      headers: {
        Authorization: `Bearer ${jwt}`,
      },
    })
    .then((res) => {
      return res.data;
    })
    .catch((error) => {
      if (error.response) {
        if (error.response.data.name) {
          return Promise.reject(error.response.data.name as string);
        }
        return Promise.reject(error.response.data as string);
      }
      return Promise.reject(error.message);
    });
  return res;
};

export const updateSuburb = async (
  dto: SuburbDto,
  jwt: string,
  suburbId: number
) => {
  const res = await axios
    .post<Suburb>(ADMIN_URL + 'suburb/' + suburbId, dto, {
      headers: {
        Authorization: `Bearer ${jwt}`,
      },
    })
    .then((res) => {
      return res.data;
    })
    .catch((error) => {
      if (error.response) {
        if (error.response.data.name) {
          return Promise.reject(error.response.data.name as string);
        }
        return Promise.reject(error.response.data as string);
      }
      return Promise.reject(error.message);
    });
  return res;
};

export const deleteSuburb = async (jwt: string, suburbId: number) => {
  const res = await axios
    .delete<string>(ADMIN_URL + 'suburb/' + suburbId, {
      headers: {
        Authorization: `Bearer ${jwt}`,
      },
    })
    .then((res) => {
      return res.data;
    })
    .catch((error) => {
      if (error.response) {
        return Promise.reject(error.response.data as string);
      }
      return Promise.reject(error.message);
    });
  return res;
};
