import { useContext, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router';
import {
  DataVariant,
  PostcodeSuburb,
  SuburbPostcode,
} from '../../types/postcode';
import { HeaderContext } from '../../context/HeaderContextProvider';
import classes from './ViewPage.module.scss';
const ViewPage = () => {
  const { state } = useLocation();
  const { setHeading } = useContext(HeaderContext);
  const navigate = useNavigate();

  useEffect(() => {
    if (state == null) {
      navigate('/');
    }
    setHeading(state.variant === DataVariant.Suburb ? 'Suburb' : 'Postcode');
  }, [navigate, state, setHeading]);

  if (state.variant === DataVariant.Postcode) {
    const suburbs = state.data.suburbs as PostcodeSuburb[];
    return (
      <div className={classes.container}>
        <div className={classes.field}>
          <h2 className={classes.key}>Postcode:</h2>
          <p className={classes.value}>{state.data.postcode}</p>
        </div>
        <div className={classes.field}>
          <h2 className={classes.key}>Id:</h2>
          <p className={classes.value}>{state.data.postcodeId}</p>
        </div>
        <div className={classes.field}>
          <h2 className={classes.key}>Suburbs:</h2>
          <ul className={classes.value}>
            {suburbs.map((burb) => (
              <li className={classes.sub_value} key={burb.suburbId}>
                {burb.name}
              </li>
            ))}
          </ul>
        </div>
      </div>
    );
  }
  const postcodes = state.data.postcodes as SuburbPostcode[];
  return (
    <div className={classes.container}>
      <div className={classes.field}>
        <h2 className={classes.key}>Suburb:</h2>
        <p className={classes.value}>{state.data.suburbName}</p>
      </div>
      <div className={classes.field}>
        <h2 className={classes.key}>Suburb Id:</h2>
        <p className={classes.value}>{state.data.suburbId}</p>
      </div>
      <div className={classes.field}>
        <h2 className={classes.key}>Postcodes:</h2>
        <ul>
          {postcodes.map((codes) => (
            <li className={classes.value} key={codes.postcodeId}>
              {codes.postcode}
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
};

export default ViewPage;
