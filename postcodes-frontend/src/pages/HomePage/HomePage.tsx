import classes from './HomePage.module.scss';
// import { LoginContext } from '../../context/LoginContextProvider';
import // axiosPostcodes,
// getAllPostcodes,
// getAllSuburbs,
'../../services/public-services';
import { useContext, useEffect } from 'react';
// import { getAllSuburbs } from '../../services/public-services';
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
  // const { postcodes } = useContext(PostcodeContext);
  // console.log(postcodes);
  // const { isLoggedIn } = useContext(LoginContext);
  // useEffect(() => {
  //   // axiosPostcodes();
  //   // getAllSuburbs();
  //   console.log(isLoggedIn);
  // });
  // if (postcodes) {
  //   return (
  //     <>
  //       {postcodes.map((postcode) => (
  //         <div key={postcode.postcodeId}>
  //           <p>Postcode - {postcode.postcode}</p>
  //           <p>Postcode Id - {postcode.postcodeId}</p>
  //           {postcode.suburbs.map((suburb) => (
  //             <p key={suburb.suburbId}>Suburb - {suburb.name}</p>
  //           ))}
  //         </div>
  //       ))}
  //     </>
  //   );
  // }
  // if (displayPostcode)
  // return <p>Loading...</p>;
  return (
    <div className={classes.container}>
      <div className={classes.button_container}>
        <Button onClick={() => setDisplayPostcode(true)}>Show Postcodes</Button>
        <Button onClick={() => setDisplayPostcode(false)}>Show Suburbs</Button>
      </div>
      <div>
        <ListContainer />
        {/* <p>Displaying {displayPostcode ? 'postcode' : 'suburb'}</p> */}
      </div>
    </div>
  );
};

export default HomePage;
