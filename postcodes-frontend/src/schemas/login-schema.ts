import { z } from 'zod';

const namePattern = /[a-zA-Z0-9]{4,12}/;
const nameMsg =
  'Name must be between 4 and 12 characters, and must only contain letters or numbers';
const passwordPattern = /[\w]{6,20}/;
const passwordMsg = 'Password must be between 6 and 20 characters';
// const passwordPattern =

export const loginSchema = z.object({
  username: z.string().regex(namePattern, {
    message: nameMsg,
  }),
  password: z.string().regex(passwordPattern, {
    message: passwordMsg,
  }),
});

export type LoginData = z.infer<typeof loginSchema>;
