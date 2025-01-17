import { MdOutlineMailOutline, MdOutlinePersonOutline, MdOutlinePhoneAndroid } from "react-icons/md";
import { TextInputWithIcon } from "../../../../components/inputs/TextInputWithIcon"
import { FaRegIdCard } from "react-icons/fa";
import { IoIdCard, IoIdCardOutline } from "react-icons/io5";
import { GrDocument } from "react-icons/gr";
import { MdOutlineDocumentScanner } from "react-icons/md";
import { Button } from "../../../../components/buttons/Button";



export const DadosAcademicosForm = ({ register }: any) => {

    return (
        <div className="flex justify-between w-full h-full  space-y-4 px-14">

            <div className="flex w-full flex-col">
                <p className="font-semibold text-lg">Dados Acadêmicos</p>
                <div className="grid grid-cols-2 w-full grid-rows-5">
                    <TextInputWithIcon
                        icon=""
                        title="Nível de formação *"
                        register={register}
                        input="nivelFormacao"
                        type='text'
                        disabled={false}
                    />
                    <TextInputWithIcon
                        icon=""
                        title="Instituição"
                        register={register}
                        input="instituicao"
                        type='text'
                        disabled={false}
                    />
                    <TextInputWithIcon
                        icon=""
                        title="Tipo de curso"
                        register={register}
                        input="instituicao"
                        type='text'
                        disabled={false}
                    />
                    <TextInputWithIcon
                        icon=""
                        title="Ano de conclusão"
                        register={register}
                        input="instituicao"
                        type='number'
                        disabled={false}
                    />


                </div>
                <div className="flex w-full">
                <TextInputWithIcon
                    icon=""
                    title="Anexo do diploma"
                    register={register}
                    input="instituicao"
                    type='text'
                    disabled={false}
                    className="w-full bg-blue-100"
                    classNameInput=" w-full flex"
                />
                <Button 
                    title="Adicionar formação" 
                    type={4}
                    className="w-1/2 h-10 mt-4 rounded-none bg-[#383838] "  
                    onClick={() => {}}
                />
                </div>
              
            </div>


        </div>

    );

}

