/* eslint-disable react-refresh/only-export-components */
import { createContext, useState } from 'react';

interface HeaderContextValues {
  heading: string;
  setHeading: (heading: string) => void;
}

export const HeaderContext = createContext<HeaderContextValues>({
  heading: '',
  setHeading: () => {},
});

interface HeaderContextProviderProps {
  children?: React.ReactNode;
}

const HeaderContextProvider = ({ children }: HeaderContextProviderProps) => {
  const [heading, setHeading] = useState('Postcodes');
  return (
    <HeaderContext.Provider
      value={{
        heading: heading,
        setHeading,
      }}
    >
      {children}
    </HeaderContext.Provider>
  );
};

export default HeaderContextProvider;
