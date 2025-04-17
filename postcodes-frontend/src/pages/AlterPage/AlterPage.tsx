import { useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router';
import { DataVariant } from '../../types/postcode';
import PostcodeForm from '../../components/PostcodeForm/PostcodeForm';
import SuburbForm from '../../components/SuburbForm/SuburbForm';

const AlterPage = () => {
  const { state } = useLocation();
  const navigate = useNavigate();
  useEffect(() => {
    if (state == null) {
      navigate('/');
    }
  }, [navigate, state]);
  if (state.variant === DataVariant.Postcode) {
    return (
      <>
        {state.isEditMode && (
          <PostcodeForm isEditMode={state.isEditMode} currData={state.data} />
        )}
        {!state.isEditMode && <PostcodeForm />}
      </>
    );
  }
  return (
    <>
      {state.isEditMode && (
        <SuburbForm isEditMode={state.isEditMode} currData={state.data} />
      )}
      {!state.isEditMode && <SuburbForm />}
    </>
  );
};

export default AlterPage;
