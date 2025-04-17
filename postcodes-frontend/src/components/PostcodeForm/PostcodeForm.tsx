import { useContext, useEffect, useState } from 'react';
import { PostcodeContext } from '../../context/PostcodeContextProvider';
import { Postcode } from '../../types/postcode';
import {
  createPostcodeDto,
  createSuburbOptions,
  getSuburbOptions,
} from '../../utilities/utilities';
import { Controller, useForm } from 'react-hook-form';
import {
  PostcodeFormData,
  postcodeSchema,
} from '../../schemas/postcode-schema';
import { zodResolver } from '@hookform/resolvers/zod';
import Select from 'react-select';
import Button from '../Button/Button';
import { LoginContext } from '../../context/LoginContextProvider';
import { createPostcode, updatePostcode } from '../../services/admin-services';
import { getAllPostcodes, getAllSuburbs } from '../../services/public-services';
import { useNavigate } from 'react-router';
import { HeaderContext } from '../../context/HeaderContextProvider';
import classes from './PostcodeForm.module.scss';

interface PostcodeFormProps {
  currData?: Postcode;
  isEditMode?: boolean;
}

const PostcodeForm = ({ currData, isEditMode = false }: PostcodeFormProps) => {
  const { setHeading } = useContext(HeaderContext);
  useEffect(() => {
    setHeading(isEditMode ? 'Edit Postcode' : 'Create Postcode');
  }, [setHeading, isEditMode]);
  const navigate = useNavigate();
  const { jwt } = useContext(LoginContext);
  const [errorMessage, setErrorMessage] = useState('');
  const { suburbs, setPostcodes, setSuburbs } = useContext(PostcodeContext);
  const suburbOptions = createSuburbOptions(suburbs);
  const currSuburbs = currData ? getSuburbOptions(currData.suburbs) : undefined;

  const {
    handleSubmit,
    register,
    control,
    formState: { errors },
  } = useForm<PostcodeFormData>({
    resolver: zodResolver(postcodeSchema),
  });

  const submitWrapper = async (data: PostcodeFormData) => {
    setErrorMessage('');
    const dto = createPostcodeDto(data);

    if (isEditMode && currData) {
      updatePostcode(dto, jwt, currData.postcode)
        .then(() => getAllPostcodes())
        .then((res) => setPostcodes(res))
        .then(() => getAllSuburbs())
        .then((res) => setSuburbs(res))
        .then(() => navigate('/admin'))
        .catch((e: string) => {
          setErrorMessage(e);
        });
      return;
    }
    createPostcode(dto, jwt)
      .then(() => getAllPostcodes())
      .then((res) => setPostcodes(res))
      .then(() => getAllSuburbs())
      .then((res) => setSuburbs(res))
      .then(() => navigate('/admin'))
      .catch((e: string) => {
        setErrorMessage(e);
      });
  };

  return (
    <>
      <form
        className={classes.container}
        onSubmit={handleSubmit(submitWrapper)}
      >
        <div className={classes.field}>
          <div className={classes.error_row}>
            {errors?.postcode && <p>{errors?.postcode?.message}</p>}
          </div>
          <div className={classes.input_row}>
            <label htmlFor="postcodeInput" className={classes.label}>
              Postcode:
            </label>
            <input
              className={classes.input}
              type="text"
              id="postcodeInput"
              defaultValue={currData && currData.postcode}
              {...register('postcode')}
            />
          </div>
        </div>

        <div className={classes.field}>
          <div className={classes.error_row}>
            {errors?.suburbs && <p>{errors?.suburbs?.message}</p>}
          </div>
          <div className={classes.input_row}>
            <label htmlFor="suburbInput" className={classes.label}>
              Suburbs:
            </label>
            <Controller
              control={control}
              name="suburbs"
              render={({ field }) => (
                <Select
                  {...field}
                  isMulti
                  options={suburbOptions}
                  defaultValue={currSuburbs}
                  className={classes.input}
                />
              )}
            />
          </div>
        </div>
        <div className={classes.submit}>
          <Button>Submit</Button>
          <p className={classes.error_row}>{errorMessage}</p>
        </div>
      </form>
    </>
  );
};

export default PostcodeForm;
