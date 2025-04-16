import { z } from 'zod';

const postcodePattern = /\d{4}/;
const postcodeMsg = 'Postcode must be a 4 digit value';

export const postcodeSchema = z.object({
  postcode: z.string().regex(postcodePattern, {
    message: postcodeMsg,
  }),
  //   suburbs:  z.object({
  //             label: z.string(),
  //             value: z.string(),
  //             id: z.number(),
  //           })
  suburbs: z
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

export type PostcodeFormData = z.infer<typeof postcodeSchema>;
