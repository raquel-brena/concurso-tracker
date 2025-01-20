import { Separator } from "@radix-ui/react-menubar";
import React from "react";
import { BoxContent } from "../../autenticacao/components/BoxContent";
import { CadastrarEditalForm } from "./register/CadastrarEditalForm";
import { useForm } from "react-hook-form";


const CadastrarEdital = ({ headerSubTitle, headerDescription }: any) => {

  return (
    <>
      <div className="flex flex-col w-full h-1/4  pt-2 space-y-4">
        <Separator />
        <p>{headerDescription}</p>
        <p className="font-semibold pt-6 uppercase">{headerSubTitle}</p>
        <Separator />
      </div>

      <div className="flex gap-2 py-8 w-full px-4 ">
        <BoxContent className="flex w-1/2 h-fit p-2 overflow-hidden">
        <CadastrarEditalForm/>
        </BoxContent>
        <BoxContent>

        </BoxContent>
      </div>
    </>
  );
};

export default CadastrarEdital;
