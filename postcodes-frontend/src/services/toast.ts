import { Bounce, toast } from 'react-toastify';

export const successToast = (item: string) =>
  toast.success(`Successfully deleted ${item}`, {
    position: 'bottom-right',
    autoClose: 4000,
    hideProgressBar: false,
    closeOnClick: false,
    pauseOnHover: true,
    draggable: true,
    progress: undefined,
    theme: 'light',
    transition: Bounce,
  });

export const failureToast = (item: string) =>
  toast.error(item, {
    position: 'bottom-right',
    autoClose: 4000,
    hideProgressBar: false,
    closeOnClick: false,
    pauseOnHover: true,
    draggable: true,
    progress: undefined,
    theme: 'light',
    transition: Bounce,
  });

export const loginToast = () =>
  toast.success(`Login Successful`, {
    position: 'bottom-right',
    autoClose: 4000,
    hideProgressBar: false,
    closeOnClick: false,
    pauseOnHover: true,
    draggable: true,
    progress: undefined,
    theme: 'light',
    transition: Bounce,
  });
