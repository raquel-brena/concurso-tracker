import { HomeIcon } from "@radix-ui/react-icons";
import { FaRegIdCard } from "react-icons/fa";

type StepProps = { 
  circle: 1 | 2;
  stroke: 1 | 2 | 3;
  title: string;
}
export const Step = ({circle, stroke, title} : StepProps) => {
  return (
    <div className="block">
      <div className="flex items-center">
        <div
          className={`size-7 rounded-full ${
            circle === 1 ? "bg-[#383838]" : " bg-[#C3C3C3]"
          }  duration-300`}
        />
          <div
            className={`w-28 h-1  ${stroke === 1 ? "bg-[#383838]" : stroke === 2 ? "bg-[#C3C3C3]" : "hidden"}`} 
          />
      </div>
      <p className="text-xs font-medium mt-1 max-w-10 text-[#383838]">
        {title}
      </p>
    </div>
  );
};
