import { PostcodeFormData } from '../schemas/postcode-schema';
import { SuburbFormData } from '../schemas/suburb-schema';
import {
  Postcode,
  PostcodeDto,
  PostcodeSuburb,
  Suburb,
  SuburbDto,
  SuburbPostcode,
} from '../types/postcode';

export interface Option {
  label: string;
  value: string;
  id: number;
}

export const createPostcodeOptions = (postcodes: Postcode[]) => {
  if (!postcodes.length) {
    return undefined;
  }
  return postcodes.map((postcode) => {
    return {
      label: postcode.postcode,
      value: postcode.postcode,
      id: postcode.postcodeId,
    } as Option;
  });
};

export const getPostcodeOptions = (postcodes: SuburbPostcode[]) => {
  if (!postcodes.length) {
    return undefined;
  }
  return postcodes.map((postcode) => {
    return {
      label: postcode.postcode,
      value: postcode.postcode,
      id: postcode.postcodeId,
    } as Option;
  });
};

export const createSuburbOptions = (suburbs: Suburb[]) => {
  if (!suburbs.length) {
    return undefined;
  }
  return suburbs.map((suburb) => {
    return {
      label: suburb.suburbName,
      value: suburb.suburbName,
      id: suburb.suburbId,
    } as Option;
  });
};

export const getSuburbOptions = (suburbs: PostcodeSuburb[]) => {
  if (!suburbs.length) {
    return undefined;
  }
  return suburbs.map((suburb) => {
    return {
      label: suburb.name,
      value: suburb.name,
      id: suburb.suburbId,
    } as Option;
  });
};

export const createPostcodeDto = (formData: PostcodeFormData) => {
  const { suburbs } = formData;
  let suburbIds;
  if (suburbs == null) {
    suburbIds = null;
  } else if (!suburbs.length) {
    suburbIds = [];
  } else {
    suburbIds = suburbs.map((suburb) => suburb?.id);
  }
  return {
    postcode: formData.postcode,
    suburbIds: suburbIds,
  } as PostcodeDto;
};

export const createSuburbDto = (formData: SuburbFormData) => {
  const { postcodeList } = formData;
  let postcodes;
  if (postcodeList == null) {
    postcodes = null;
  } else if (!postcodeList.length) {
    postcodes = [];
  } else {
    postcodes = postcodeList.map((postcode) => postcode?.value);
  }
  return {
    name: formData.suburb,
    postcodes: postcodes,
  } as SuburbDto;
};
