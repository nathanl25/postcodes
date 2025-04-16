import { useContext } from 'react';
import classes from './Header.module.scss';
import { HeaderContext } from '../../context/HeaderContextProvider';
import { Link } from 'react-router';

const Header = () => {
  const { heading } = useContext(HeaderContext);
  let link = '';
  let backVisible = true;
  //   let content;
  switch (heading) {
    case 'View Postcodes':
    case 'View Suburbs':
      backVisible = false;
      break;
    case 'Create Postcode':
    case 'Create Suburb':
    case 'Edit Postcode':
    case 'Edit Suburb':
      backVisible = true;
      link = '/admin';
      break;
    case 'Postcode':
    case 'Suburb':
    case 'Login':
      backVisible = true;
      link = '/';
      break;

    default:
      backVisible = true;
      link = '/';
      break;
  }
  return (
    <div className={classes.header}>
      <div className={classes.link_row}>
        {backVisible && (
          <Link className={classes.back_link} to={link}>
            Back
          </Link>
        )}
        {!backVisible && (
          <Link className={classes.forward_link} to={'/admin'}>
            Edit
          </Link>
        )}
      </div>
      <h1 className={classes.heading}>{heading}</h1>
    </div>
  );
};

export default Header;
