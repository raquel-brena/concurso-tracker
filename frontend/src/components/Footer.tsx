import { Content } from "../modules/portal_adm/components/Content"

export const Footer = () => { 
    return (
      <div
        className={`flex flex-col items-center 
         w-screen min-h-fit overflow-x-hidden  `}
      >
        <div className="w-full flex flex-col items-center h-[80vh] pt-2">
          <div className="w-full flex items-center bg-[#4F0A05] h-[10%]"></div>
          <div className="h-screen w-full flex justify-center items-center bg-[#652823]"></div>
        </div>
      </div>
    );
}