
import { PropsWithChildren } from "react";
import { Separator } from "../../../components/separator/separator";

type ContentProps = PropsWithChildren<{
  title: string;
  className?: string;
}>;

export const Content = ({ children, className, title }: ContentProps) => {
  return (
    <div
      className={`flex flex-col items-center 
    w-screen min-h-fit overflow-x-hidden  ${className}`}
    >
      <Separator title={title} />
      {children}
    </div>
  );
};