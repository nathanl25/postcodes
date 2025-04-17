import classes from './HomePage.module.scss';
import { useContext, useEffect } from 'react';
import { PostcodeContext } from '../../context/PostcodeContextProvider';
import { ListContainer } from '../../components/ListContainer/ListContainer';
import Button from '../../components/Button/Button';
import { HeaderContext } from '../../context/HeaderContextProvider';

const HomePage = () => {
  const { setDisplayPostcode, displayPostcode } = useContext(PostcodeContext);
  const { setHeading } = useContext(HeaderContext);
  useEffect(() => {
    setHeading(displayPostcode ? 'View Postcodes' : 'View Suburbs');
  }, [setHeading, displayPostcode]);

  return (
    <div className={classes.container}>
      <div className={classes.button_container}>
        <Button onClick={() => setDisplayPostcode(true)}>Show Postcodes</Button>
        <Button onClick={() => setDisplayPostcode(false)}>Show Suburbs</Button>
      </div>
      <div>
        <ListContainer />
      </div>
    </div>
  );
};

export default HomePage;
