import React from "react";

interface MenuBarRootProps {
  children?: React.ReactNode;
  className?: string;
}

export const BoxContent = ({
  children,
  className,
  ...props
}: MenuBarRootProps) => {
  return (
    <div
      className={`flex flex-col relative bg-white shadow-md p-8 ${
        className || ""
      }`}
      {...props}
    >
      {children}
    </div>
  );
};
