import React from "react";

interface ButtonProps {
  className?: string;
  children: React.ReactNode;
  style: 1 | 2 | 3 | 4;
  type?: "button" | "submit" | "reset";
  onClick?: () => void;
}
export const Button = ({ className, style, children, onClick, ...props }: ButtonProps) => {
  return (
    <button
      {...props}
      onClick={onClick}
      className={`
         px-8 py-2 shadow-sm rounded-full text-sm  font-medium transition-all duration-200
        ${
          style === 1 &&
          `bg-red-600  hover:bg-red-700 
         px-8 py-2 shadow-sm rounded-full text-sm text-white font-medium `
        }
        ${
          style === 2 &&
          `border rounded-full bg-transparent  text-gray-600 border-[#303030] hover:bg-transparent hover:text-gray-700`
        }
           ${
             style === 3 &&
             `border rounded-full bg-transparent text-red-600 border-red-600 hover:bg-transparent hover:text-red-700`
           }
            ${
              style === 4 &&
              `bg-gray-200  
         px-8 py-2 shadow-sm rounded-full text-sm text-gray-500 font-medium hover:bg-gray-300 hover:text-gray-600  `
            }
            ${className}`}
    >
      {children}
    </button>
  );
};
