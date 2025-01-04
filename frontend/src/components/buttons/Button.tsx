import React from "react";

interface ButtonProps {
  title: string;
  className?: string;
}
export const Button = ({ title, className, ...props }: ButtonProps) => {
  return (
    <button
      className={`bg-red-600 self-end hover:bg-red-700 
        w-fit px-8 py-2 shadow-sm rounded-full text-sm text-white font-medium ${className}`}
    >
      {title}
    </button>
  );
};
