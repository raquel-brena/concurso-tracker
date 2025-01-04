import React from "react";

interface TextInputProps extends React.InputHTMLAttributes<HTMLInputElement> {

}

export const TextInput: React.FC<TextInputProps> = ({
  className,
  ...props
}) => {
  return (
    <input
      className={`h-10 rounded border border-[#888888] px-4 py-2 focus:border-[#888888a6] focus:outline-none ${className}`}
      {...props}
    />
  );
};

