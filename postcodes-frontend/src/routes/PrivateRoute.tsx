import { JSX, useContext } from 'react';
import { LoginContext } from '../context/LoginContextProvider';
import { Navigate } from 'react-router';

interface PrivateRouteProps {
  children: JSX.Element;
}

const PrivateRoute = ({ children }: PrivateRouteProps) => {
  const { isLoggedIn } = useContext(LoginContext);
  return isLoggedIn ? children : <Navigate to="/login" />;
};

export default PrivateRoute;
