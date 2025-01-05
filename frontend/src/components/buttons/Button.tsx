import React from "react";

interface ButtonProps {
  title: string;
  className?: string;
  type: 1 | 2 | 3 | 4;
}
export const Button = ({ title, className, type, ...props }: ButtonProps) => {
  return (
    <button
      className={`
         px-8 py-2 shadow-sm rounded-full text-sm  font-medium  transition-all duration-200
        ${
          type === 1 &&
          `bg-red-600  hover:bg-red-700 
         px-8 py-2 shadow-sm rounded-full text-sm text-white font-medium `
        }
        ${
          type === 2 &&
          `border rounded-full bg-transparent  text-gray-600 border-[#303030] hover:bg-transparent hover:text-gray-700`
        }
           ${
             type === 3 &&
             `border rounded-full bg-transparent text-red-600 border-red-600 hover:bg-transparent hover:text-red-700`
           }
            ${
              type === 4 &&
              `bg-gray-200  
         px-8 py-2 shadow-sm rounded-full text-sm text-gray-500 font-medium hover:bg-gray-300 hover:text-gray-600  `
            }
            ${className}`}
    >
      {title}
    </button>
  );
};
