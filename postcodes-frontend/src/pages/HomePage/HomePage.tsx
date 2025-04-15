// import classes from './HomePage.module.scss';

import { useEffect } from 'react';

const HomePage = () => {
  useEffect(() => {
    fetch('http://127.0.0.1:8080/postcode')
      .then((res) => res.json())
      .then((data) => console.log(data));
  });
  return <p>Test</p>;
};

export default HomePage;
