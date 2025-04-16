import { useNavigate } from 'react-router';
import { DataVariant, DisplayData, Postcode } from '../../types/postcode';
import Button from '../Button/Button';

import classes from './PostcodeCard.module.scss';
import { useContext } from 'react';
import { LoginContext } from '../../context/LoginContextProvider';
import { deletePostcode } from '../../services/admin-services';
import { getAllPostcodes, getAllSuburbs } from '../../services/public-services';
import { PostcodeContext } from '../../context/PostcodeContextProvider';
interface PostcodeCardProps
  extends React.DetailedHTMLProps<
    React.HTMLAttributes<HTMLDivElement>,
    HTMLDivElement
  > {
  data: Postcode;
  isEditMode?: boolean;
}

const PostcodeCard = ({ data, isEditMode = false }: PostcodeCardProps) => {
  const { jwt } = useContext(LoginContext);
  const { setPostcodes, setSuburbs } = useContext(PostcodeContext);
  const navigate = useNavigate();
  const postcodeState: DisplayData = {
    variant: DataVariant.Postcode,
    data: data,
    isEditMode: isEditMode,
  };
  const expand = () => {
    const navigation = isEditMode ? '/edit' : 'view';
    navigate(navigation, { state: postcodeState });
  };
  const removePostcode = () => {
    const postcode = data.postcode;
    deletePostcode(jwt, postcode)
      .then(() => getAllPostcodes())
      .then((res) => setPostcodes(res))
      .then(() => getAllSuburbs())
      .then((res) => setSuburbs(res))
      .catch((e: string) => {
        console.log(e);
      });
  };
  return (
    <div className={classes.container}>
      <div onClick={expand} className={classes.link_container}>
        <p className={classes.title}>{data.postcode}</p>
      </div>
      {isEditMode && (
        <Button variant="delete" onClick={removePostcode}>
          Delete
        </Button>
      )}
    </div>
  );
};

export default PostcodeCard;
