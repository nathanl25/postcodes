/* eslint-disable react-refresh/only-export-components */
import { createContext, useState } from 'react';

interface LoginContextValues {
  isLoggedIn: boolean;
  jwt: string;
  setJwt: (jwt: string) => void;
  setIsLoggedIn: (isLoggedIn: boolean) => void;
}

export const LoginContext = createContext<LoginContextValues>({
  isLoggedIn: false,
  jwt: '',
  setJwt: () => {},
  setIsLoggedIn: () => {},
});

interface LoginContextProviderProps {
  children?: React.ReactNode;
}

const LoginContextProvider = ({ children }: LoginContextProviderProps) => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [jwt, setJwt] = useState('');
  return (
    <LoginContext.Provider
      value={{
        isLoggedIn: isLoggedIn,
        jwt: jwt,
        setIsLoggedIn,
        setJwt,
      }}
    >
      {children}
    </LoginContext.Provider>
  );
};

export default LoginContextProvider;
