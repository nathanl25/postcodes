import { useContext } from 'react';
import { PostcodeContext } from '../../context/PostcodeContextProvider';
import PostcodeCard from '../PostcodeCard/PostcodeCard';
import SuburbCard from '../SuburbCard/SuburbCard';
import classes from './ListContainer.module.scss';
import Button from '../Button/Button';
import { useNavigate } from 'react-router';
import { DataVariant } from '../../types/postcode';
interface ListContainerProps
  extends React.DetailedHTMLProps<
    React.HTMLAttributes<HTMLDivElement>,
    HTMLDivElement
  > {
  isEditMode?: boolean;
}

export const ListContainer = ({
  isEditMode = false,
  ...rest
}: ListContainerProps) => {
  const navigate = useNavigate();
  const createPostcodeNav = () => {
    navigate('/create', {
      state: {
        isEditMode: isEditMode,
        variant: DataVariant.Postcode,
      },
    });
  };
  const createSuburbNav = () => {
    navigate('/create', {
      state: {
        isEditMode: isEditMode,
        variant: DataVariant.Suburb,
      },
    });
  };
  const { postcodes, suburbs, displayPostcode } = useContext(PostcodeContext);
  if (displayPostcode) {
    return (
      <section className={classes.list__container}>
        <div className={classes.button_row}>
          {isEditMode && (
            <Button onClick={createPostcodeNav}>Create a New Postcode</Button>
          )}
        </div>
        {postcodes.map((postcode) => (
          <PostcodeCard
            data={postcode}
            isEditMode={isEditMode}
            key={postcode.postcodeId}
          />
        ))}
      </section>
    );
  }
  return (
    <section className={classes.list__container}>
      <div className={classes.button_row}>
        {isEditMode && (
          <Button onClick={createSuburbNav}>Create a New Suburb</Button>
        )}
      </div>
      {suburbs.map((suburb) => (
        <SuburbCard
          data={suburb}
          isEditMode={isEditMode}
          key={suburb.suburbId}
        />
      ))}
    </section>
  );
};
