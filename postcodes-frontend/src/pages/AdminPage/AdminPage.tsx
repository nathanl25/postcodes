import classes from './AdminPage.module.scss';
import { useContext, useEffect } from 'react';
import Button from '../../components/Button/Button';
import { PostcodeContext } from '../../context/PostcodeContextProvider';
import { ListContainer } from '../../components/ListContainer/ListContainer';
import { HeaderContext } from '../../context/HeaderContextProvider';

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
  );
};

export default AdminPage;
