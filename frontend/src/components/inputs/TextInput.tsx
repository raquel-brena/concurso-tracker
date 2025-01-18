import React, { forwardRef } from "react";

interface TextInputProps extends React.InputHTMLAttributes<HTMLInputElement> {
  className?: string;
  type?: string;
}

export const TextInput = forwardRef<HTMLInputElement, TextInputProps>(
  (props, ref) => {
    return (
    <input
<<<<<<< Updated upstream
      className={`h-10 rounded border border-[#888888] px-4 py-2 focus:border-[#888888a6] focus:outline-none ${className}`}
=======
    ref={ref}
    type={props.type}
      className={`h-10 rounded border border-[#888888] 
        px-4 py-2 focus:border-[#888888a6] 
        focus:outline-none ${props.className}`}
>>>>>>> Stashed changes
      {...props}
    />
  );
});


