import { MdOutlineMailOutline, MdOutlinePersonOutline, MdOutlinePhoneAndroid } from "react-icons/md";
import { TextInputWithIcon } from "../../../../components/inputs/TextInputWithIcon"
import { FaRegIdCard } from "react-icons/fa";
import { IoIdCard, IoIdCardOutline } from "react-icons/io5";
import { GrDocument } from "react-icons/gr";
import { MdOutlineDocumentScanner } from "react-icons/md";

const dadosPessoaisInputForm = {
    id: 1,
    stepForm: "Dados pessoais",
    last: false,
    inputs: [
        {
            icon: <MdOutlinePersonOutline size={26} />,
            title: "CPF",
            var: "cpf",
            type: "text",
            disabled: true,
        },
        {
            icon: <MdOutlinePersonOutline size={26} />,
            title: "Nome",
            var: "nome",
            type: "text",
            disabled: false
        },
        {
            icon: <MdOutlineMailOutline size={24} />,
            title: "E-mail",
            var: "email",
            type: "text",
            disabled: false
        },
        {
            icon: <MdOutlinePhoneAndroid size={24} />,
            title: "Celular para receber SMS",
            var: "sms",
            type: "text",
            disabled: false
        },

    ]
}

const dadosPessoaisAnexoForm = {
    title: "Anexos",
    inputs: [
        {
            icon: <MdOutlineDocumentScanner size={28} />,
            title: "RG",
            var: "rg",
            type: "text",
            disabled: false
        },
        {
            icon: <MdOutlineDocumentScanner size={28} />,
            title: "Anexo do CPF",
            var: "cpf",
            type: "text",
            disabled: false
        },
       
    ]
}
export const DadosPessoaisForm = ({ register }: any) => {

    return (
        <div className="flex justify-between w-full h-full  space-y-4 px-14">
            <div>
            <p className="font-semibold text-lg">{dadosPessoaisInputForm.stepForm}</p>
            <div className="grid grid-cols-1 grid-rows-5">
                {dadosPessoaisInputForm.inputs.map((campo) => (
                    <TextInputWithIcon
                        key={campo.var}
                        icon={campo.icon}
                        title={campo.title}
                        register={register}
                        input={campo.var}
                        type={campo.type}
                        disabled={campo.disabled}
                    />
                ))}
            </div>
            </div>
            <div>
            <p className="font-semibold text-lg">Anexos</p>
            <div className="grid grid-cols-1 grid-rows-5">
                {dadosPessoaisAnexoForm.inputs.map((campo) => (
                    <TextInputWithIcon
                        key={campo.var}
                        icon={campo.icon}
                        title={campo.title}
                        register={register}
                        input={campo.var}
                        type={campo.type}
                        disabled={campo.disabled}
                    />
                ))}
            </div>
            </div>
        </div>

    );

}

