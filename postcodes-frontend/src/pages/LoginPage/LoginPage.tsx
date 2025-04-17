import classes from './LoginPage.module.scss';
import { useContext, useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { LoginData, loginSchema } from '../../schemas/login-schema';
import { getToken } from '../../services/admin-services';
import { LoginContext } from '../../context/LoginContextProvider';
import { useNavigate } from 'react-router';
import { HeaderContext } from '../../context/HeaderContextProvider';
import Button from '../../components/Button/Button';
import { loginToast } from '../../services/toast';
const LoginPage = () => {
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
        setJwt(res);
        setIsLoggedIn(true);
        loginToast();
        navigate('/admin');
      })
      .catch((e) => {
        setErrorMessage(e);
      });
  };

  return (
    <form className={classes.container} onSubmit={handleSubmit(submitWrapper)}>
      <div className={classes.field}>
        <div className={classes.error_row}>
          {errors?.username && (
            <small className={classes.error_message}>
              {errors?.username?.message}
            </small>
          )}
        </div>
        <div className={classes.input_row}>
          <label htmlFor="usernameInput" className={classes.label}>
            Username
          </label>
          <input type="text" id="usernameInput" {...register('username')} />
        </div>
      </div>
      <div className={classes.field}>
        <div className={classes.error_row}>
          {errors?.password && (
            <small className={classes.error_message}>
              {errors?.password?.message}
            </small>
          )}
        </div>
        <div className={classes.input_row}>
          <label htmlFor="passwordInput" className={classes.label}>
            Password
          </label>
          <input type="password" id="passwordInput" {...register('password')} />
        </div>
      </div>
      <div className={classes.submit}>
        <Button>Login</Button>
        <div className={classes.error_row}>
          {errorMessage && (
            <small className={classes.error_message}>{errorMessage}</small>
          )}
        </div>
      </div>
    </form>
  );
};

export default LoginPage;
