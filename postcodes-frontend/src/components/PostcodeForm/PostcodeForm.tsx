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

interface PostcodeFormProps {
  currData?: Postcode;
  isEditMode?: boolean;
}

const PostcodeForm = ({ currData, isEditMode = false }: PostcodeFormProps) => {
  // const {}
  // const { setDisplayPostcode } = useContext(PostcodeContext);
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
    // getValues,
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
    console.log(dto);
  };

  return (
    <>
      <form onSubmit={handleSubmit(submitWrapper)}>
        <div>
          <div>{errors?.postcode && <p>{errors?.postcode?.message}</p>}</div>
          <div>
            <label htmlFor="postcodeInput">Postcode:</label>
            <input
              type="text"
              id="postcodeInput"
              defaultValue={currData && currData.postcode}
              {...register('postcode')}
            />
          </div>
        </div>
        <div>
          <div>{errors?.suburbs && <p>{errors?.suburbs?.message}</p>}</div>
          <div>
            <label htmlFor="suburbInput">Suburbs:</label>
            <Controller
              control={control}
              name="suburbs"
              render={({ field }) => (
                <Select
                  {...field}
                  isMulti
                  options={suburbOptions}
                  //   defaultV
                  defaultValue={currSuburbs}
                  // className={classes.input}
                />
              )}
            />
          </div>
        </div>
        <div>
          <Button>Submit</Button>
          <p>{errorMessage}</p>
          {/* <div></div> */}
        </div>
      </form>
    </>
  );
};

export default PostcodeForm;
