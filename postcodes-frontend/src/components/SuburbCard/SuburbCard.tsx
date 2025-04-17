import { useNavigate } from 'react-router';
import { DataVariant, DisplayData, Suburb } from '../../types/postcode';
import Button from '../Button/Button';

import classes from './SuburbCard.module.scss';
import { deleteSuburb } from '../../services/admin-services';
import { useContext } from 'react';
import { LoginContext } from '../../context/LoginContextProvider';
import { getAllPostcodes, getAllSuburbs } from '../../services/public-services';
import { PostcodeContext } from '../../context/PostcodeContextProvider';
import { failureToast, successToast } from '../../services/toast';
interface SuburbCardProps
  extends React.DetailedHTMLProps<
    React.HTMLAttributes<HTMLDivElement>,
    HTMLDivElement
  > {
  data: Suburb;
  isEditMode?: boolean;
}

const SuburbCard = ({ data, isEditMode = false }: SuburbCardProps) => {
  const { jwt } = useContext(LoginContext);
  const { setPostcodes, setSuburbs } = useContext(PostcodeContext);
  const navigate = useNavigate();
  const suburbState: DisplayData = {
    variant: DataVariant.Suburb,
    data: data,
    isEditMode: isEditMode,
  };
  const expand = () => {
    const navigation = isEditMode ? '/edit' : 'view';
    navigate(navigation, { state: suburbState });
  };
  const removeSuburb = () => {
    const id = data.suburbId;
    deleteSuburb(jwt, id)
      .then(() => getAllPostcodes())
      .then((res) => setPostcodes(res))
      .then(() => getAllSuburbs())
      .then((res) => setSuburbs(res))
      .then(() => successToast('suburb'))
      .catch((e: string) => {
        failureToast(e);
      });
  };
  return (
    <div className={classes.container}>
      <div onClick={expand} className={classes.link_container}>
        <p className={classes.title}>{data.suburbName}</p>
      </div>
      {isEditMode && (
        <Button variant="delete" onClick={removeSuburb}>
          Delete
        </Button>
      )}
    </div>
  );
};

export default SuburbCard;
