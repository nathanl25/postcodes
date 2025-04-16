import classes from './AdminPage.module.scss';
import { useContext, useEffect } from 'react';
import { LoginContext } from '../../context/LoginContextProvider';
import Button from '../../components/Button/Button';
import { useNavigate } from 'react-router';
import { PostcodeContext } from '../../context/PostcodeContextProvider';
import { ListContainer } from '../../components/ListContainer/ListContainer';
import { HeaderContext } from '../../context/HeaderContextProvider';
// import { Navigate, redirect, useNavigate } from 'react-router';

const AdminPage = () => {
  const { setDisplayPostcode, displayPostcode } = useContext(PostcodeContext);
  const { setHeading } = useContext(HeaderContext);
  useEffect(() => {
    setHeading(displayPostcode ? 'Modify Postcodes' : 'Modify Suburbs');
  }, [setHeading, displayPostcode]);
  return (
    <div className={classes.container}>
      <div className={classes.button_container}>
        <Button onClick={() => setDisplayPostcode(true)}>Show Postcodes</Button>
        <Button onClick={() => setDisplayPostcode(false)}>Show Suburbs</Button>
      </div>
      <div>
        <ListContainer isEditMode={true} />
      </div>
    </div>
    // <>
    //   <p>Admin</p>;<Button onClick={() => nav('/edit')}>Edit nothing</Button>;
    // </>
  );
};

export default AdminPage;
