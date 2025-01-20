import React from "react";


type StepProps = { 
  circle: 1 | 2;
  stroke: 1 | 2 | 3;
  title: string;
}
export const Step = ({circle, stroke, title} : StepProps) => {
  return (
    <div className="block">
    <div className="flex items-center">
      {circle === 1 ? ( 
        <div className="size-7 rounded-full bg-[#383838]" />) 
      : ( <div className="size-7 rounded-full bg-[#C3C3C3]" />)
      }
     {stroke === 1 ? ( 
          <div className="w-28 h-1 bg-[#383838]" />) 
      : stroke === 2 ?  
      (<div className="w-28 h-1 bg-[#C3C3C3]" /> ) 
      : (<div></div>)
      }
    
    </div>
    <p className="text-xs font-medium mt-1 max-w-10 text-[#383838]">{title}</p>
  </div>
    
  );
};
