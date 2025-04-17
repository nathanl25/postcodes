import { z } from 'zod';

const suburbPattern = /[a-zA-Z- ]{4,25}/;
const suburbMsg =
  'Suburb can only contain letters, spaces and hypens and must be between 4 and 25 characters';

export const suburbSchema = z.object({
  suburb: z.string().regex(suburbPattern, {
    message: suburbMsg,
  }),
  postcodeList: z
    .array(
      z
        .object({
          label: z.string(),
          value: z.string(),
          id: z.number(),
        })
        .nullable()
    )
    .optional(),
});

export type SuburbFormData = z.infer<typeof suburbSchema>;
