import { Separator } from "@radix-ui/react-menubar";
import React, { useEffect, useState } from "react";
import { BoxContent } from "../../autenticacao/components/BoxContent";
import { CadastrarEditalForm } from "./register/CadastrarEditalForm";
import { useForm } from "react-hook-form";
import axios from "axios";
import { useParams } from "react-router-dom";


const CadastrarEdital = ({ headerSubTitle, headerDescription }: any) => {

  const [processoSeletivo, setProcessoSeletivo] = useState<any>({});

   const { id } = useParams();

 useEffect(() => {
   async function fetchProcesso() {
     try {
       const token = localStorage.getItem("token");
       if (id) {
          const response = await axios.get(
            `http://localhost:8080/api/processo/${id}`,
            {
              headers: {
                Authorization: `Bearer ${token}`,
              },
            }
          );
          setProcessoSeletivo(response.data.data);
       }
     
     } catch (error) {
       console.error("Erro ao buscar o processo:", error);
     }
   }
   fetchProcesso();
 }, [id]);

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
          <CadastrarEditalForm
            processoSeletivo={processoSeletivo}
            setProcessoSeletivo={setProcessoSeletivo}
          />
        </BoxContent>
        <BoxContent></BoxContent>
      </div>
    </>
  );
};

export default CadastrarEdital;
