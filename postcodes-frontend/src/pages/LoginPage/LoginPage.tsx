// import classes from './LoginPage.module.scss';
import { useContext, useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { LoginData, loginSchema } from '../../schemas/login-schema';
import { getToken } from '../../services/admin-services';
import { LoginContext } from '../../context/LoginContextProvider';
import { useNavigate } from 'react-router';
import { HeaderContext } from '../../context/HeaderContextProvider';
// import {}
const LoginPage = () => {
  // const {register} = useForm();
  const { setHeading } = useContext(HeaderContext);
  useEffect(() => {
    setHeading('Login');
  }, [setHeading]);
  const navigate = useNavigate();
  const { setIsLoggedIn, setJwt } = useContext(LoginContext);
  const [errorMessage, setErrorMessage] = useState('');
  const {
    handleSubmit,
    register,
    formState: { errors },
  } = useForm<LoginData>({
    resolver: zodResolver(loginSchema),
  });

  const submitWrapper = async (data: LoginData) => {
    setErrorMessage('');
    getToken(data)
      .then((res) => {
        console.log(res);
        console.log('success');
        setJwt(res);
        setIsLoggedIn(true);
        navigate('/admin');
      })
      .catch((e) => {
        setErrorMessage(e);
      });
  };

  return (
    <form onSubmit={handleSubmit(submitWrapper)}>
      <div>
        {errors?.username && <small>{errors?.username?.message}</small>}
      </div>
      <div>
        <label htmlFor="usernameInput">Username</label>
        <input type="text" id="usernameInput" {...register('username')} />
      </div>
      <div>
        {errors?.password && <small>{errors?.password?.message}</small>}
      </div>
      <div>
        <label htmlFor="passwordInput">Password</label>
        <input type="password" id="passwordInput" {...register('password')} />
      </div>
      <div>{errorMessage && <small>{errorMessage}</small>}</div>
      <button>Submit</button>
    </form>
  );
};

export default LoginPage;
