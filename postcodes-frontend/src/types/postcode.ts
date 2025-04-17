export interface Postcode {
  postcodeId: number;
  postcode: string;
  suburbs: PostcodeSuburb[];
}

export interface PostcodeSuburb {
  name: string;
  suburbId: number;
}

export interface Suburb {
  suburbId: number;
  suburbName: string;
  postcodes: SuburbPostcode[];
}

export interface SuburbPostcode {
  postcode: string;
  postcodeId: number;
}

export enum DataVariant {
  Suburb,
  Postcode,
}

export interface DisplayData {
  data: Suburb | Postcode;
  variant: DataVariant;
  isEditMode: boolean;
}

export interface PostcodeDto {
  postcode: string;
  suburbIds: number[] | [] | null;
}

export interface SuburbDto {
  name: string;
  postcodes: string[] | [] | null;
}
