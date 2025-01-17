import { TextInputWithIcon } from "../../../../components/inputs/TextInputWithIcon"

export const PerfilForm = () => {

    return (
        <div className="flex flex-col w-full h-full bg-red-200 space-y-4 px-14">
            <p className="font-semibold text-lg">Escolha uma foto</p>
            <p className="font-semibold text-lg">Estado em que reside</p>
            
            <p className="font-semibold text-lg">Escolhas áreas do seu interesse</p>
        
        </div>
                 
    );

}

// <div className="flex flex-col w-full space-y-4 px-14">
// <p className="font-semibold text-lg">{step.stepForm}</p>
// <div className="grid grid-cols-2 grid-rows-5">
// {step.inputs.map((campo) => (
//   <TextInputWithIcon
//     key={campo.var} 
//     icon={campo.icon}
//     title={campo.title}
//     register={register}
//     input={campo.var}
//     type={campo.type}
//     disabled={campo.disabled}
//   />
// ))}
// </div>        
// </div>