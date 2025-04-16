import { useContext, useEffect, useState } from 'react';
import { Suburb } from '../../types/postcode';
import { HeaderContext } from '../../context/HeaderContextProvider';
import { useNavigate } from 'react-router';
import { LoginContext } from '../../context/LoginContextProvider';
import { PostcodeContext } from '../../context/PostcodeContextProvider';
import {
  createPostcodeOptions,
  createSuburbDto,
  getPostcodeOptions,
} from '../../utilities/utilities';
import { Controller, useForm } from 'react-hook-form';
import { SuburbFormData, suburbSchema } from '../../schemas/suburb-schema';
import { zodResolver } from '@hookform/resolvers/zod';
import { getAllPostcodes, getAllSuburbs } from '../../services/public-services';
import Select from 'react-select';
import Button from '../Button/Button';
import { createSuburb, updateSuburb } from '../../services/admin-services';

interface SuburbFormProps {
  currData?: Suburb;
  isEditMode?: boolean;
}

const SuburbForm = ({ currData, isEditMode = false }: SuburbFormProps) => {
  const { setHeading } = useContext(HeaderContext);
  useEffect(() => {
    setHeading(isEditMode ? 'Edit Suburb' : 'Create Suburb');
  }, [setHeading, isEditMode]);
  const navigate = useNavigate();
  const { jwt } = useContext(LoginContext);
  const [errorMessage, setErrorMessage] = useState('');
  const { postcodes, setPostcodes, setSuburbs } = useContext(PostcodeContext);
  const postcodeOptions = createPostcodeOptions(postcodes);
  const currPostcodes = currData
    ? getPostcodeOptions(currData.postcodes)
    : undefined;

  const {
    handleSubmit,
    register,
    control,
    formState: { errors },
  } = useForm<SuburbFormData>({
    resolver: zodResolver(suburbSchema),
  });

  const submitWrapper = async (data: SuburbFormData) => {
    setErrorMessage('');
    const dto = createSuburbDto(data);
    if (isEditMode && currData) {
      console.log(dto);
      updateSuburb(dto, jwt, currData.suburbId)
        .then(() => getAllPostcodes())
        .then((res) => setPostcodes(res))
        .then(() => getAllSuburbs())
        .then((res) => setSuburbs(res))
        .then(() => navigate('/admin'))
        .catch((e: string) => {
          console.log(e);
          setErrorMessage(e);
        });
      return;
    }
    createSuburb(dto, jwt)
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
      <form onSubmit={handleSubmit(submitWrapper)}>
        <div>{errors?.suburb && <p>{errors?.suburb?.message}</p>}</div>
        <div>
          <label htmlFor="suburbInput">Suburb:</label>
          <input
            type="text"
            id="suburbInput"
            defaultValue={currData && currData.suburbName}
            {...register('suburb')}
          />
        </div>
        <div>
          <div>
            {errors?.postcodeList && <p>{errors?.postcodeList?.message}</p>}
          </div>
          <div>
            <label htmlFor="suburbInput">Postcodes:</label>
            <Controller
              control={control}
              name="postcodeList"
              render={({ field }) => (
                <Select
                  {...field}
                  isMulti
                  options={postcodeOptions}
                  //   defaultV
                  defaultValue={currPostcodes}
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

export default SuburbForm;
