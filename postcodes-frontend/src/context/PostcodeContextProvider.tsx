/* eslint-disable react-refresh/only-export-components */
import { createContext, useEffect, useState } from 'react';
import { Postcode, Suburb } from '../types/postcode';
// import { getAllPostcodes } from '../services/public-services';
import { getAllPostcodes, getAllSuburbs } from '../services/public-services';

interface PostcodeContextValues {
  postcodes: Postcode[];
  setPostcodes: (data: Postcode[]) => void;
  suburbs: Suburb[];
  setSuburbs: (data: Suburb[]) => void;
  displayPostcode: boolean;
  setDisplayPostcode: (val: boolean) => void;
  // addPostcode: (data: unknown) => Promise<unknown>;
  // updatePostcode: (data: unknown) => Promise<unknown>;
}

export const PostcodeContext = createContext<PostcodeContextValues>({
  postcodes: [],
  setPostcodes: () => {},
  suburbs: [],
  setSuburbs: () => {},
  displayPostcode: true,
  setDisplayPostcode: () => {},
});

interface PostcodeContextProviderProps {
  children?: React.ReactNode;
}

const PostcodeContextProvider = ({
  children,
}: PostcodeContextProviderProps) => {
  const [postcodes, setPostcodes] = useState<Postcode[]>([]);
  const [suburbs, setSuburbs] = useState<Suburb[]>([]);
  const [displayPostcode, setDisplayPostcode] = useState(true);
  useEffect(() => {
    getAllPostcodes().then((res) => setPostcodes(res));
    getAllSuburbs().then((res) => setSuburbs(res));
  }, []);

  return (
    <PostcodeContext.Provider
      value={{
        postcodes: postcodes,
        suburbs: suburbs,
        displayPostcode: displayPostcode,
        setPostcodes,
        setSuburbs,
        setDisplayPostcode,
      }}
    >
      {children}
    </PostcodeContext.Provider>
  );
};

export default PostcodeContextProvider;
